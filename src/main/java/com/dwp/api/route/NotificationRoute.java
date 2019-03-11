package com.dwp.api.route;

import com.dwp.api.dao.NotificationAuditLogDAO;
import com.dwp.api.model.Notification;
import com.dwp.api.model.NotificationAudit;
import com.dwp.api.processsor.ErrorProcessor;
import com.dwp.api.processsor.NotificationAuditLogMappingProcessor;
import com.dwp.api.processsor.NotificationAuditMappingProcessor;
import com.dwp.api.processsor.NotificationRouter;
import com.dwp.api.repository.NotificationAuditLogRespository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangePattern;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

@Component
@Slf4j

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotificationRoute extends SpringRouteBuilder {

    public static final String NOTIFICATION_ROUTE = "direct:notification-route";
    public static final String NOTIFICATION_SEGREGATION_ROUTE = "rabbitmq:localhost:5672/cbr?username=guest&password=guest&autoDelete=false&queue=NOTIFICATION";
//    public static final String NOTIFICATION_SEGREGATION_ROUTE = "rabbitmq:localhost:5672/cbr?username=guest&password=guest&autoDelete=false&routingKey=NOTIFICATION";

    @Autowired
    private NotificationAuditMappingProcessor notificationAuditMappingProcessor;

    @Autowired
    private NotificationAuditLogMappingProcessor notificationAuditLogMappingProcessor;

    @Autowired
    private ErrorProcessor errorProcessor;

    @Autowired
    NotificationAuditLogDAO notificationAuditLogDAO;
    @Override
    public void configure() throws Exception {

        from(NOTIFICATION_ROUTE)
                .routeId("NOTIFICATION-ROUTE")
                .onException(Exception.class).continued(true).process(errorProcessor).end()
                .to("log:NOTIFICATION-ROUTE-START?showAll=true&level=info")
                .marshal().json(JsonLibrary.Jackson, Notification.class)
                .inOnly(NOTIFICATION_SEGREGATION_ROUTE)
               .to("log:NOTIFICATION-ROUTE-END?showAll=true&level=info")
        .end();

        from(NOTIFICATION_SEGREGATION_ROUTE)
                .routeId("NOTIFICATION_SEGREGATION_ROUTE")
                .onException(ConnectException.class).continued(true).process(errorProcessor).end()
                .to("log:NNOTIFICATION_SEGREGATION_ROUTE-START?showAll=true&level=info")
                .setExchangePattern(ExchangePattern.InOnly)
                .bean(NotificationRouter.class, "route")
                .transacted()
                .recipientList().simple("${header.DYNAMIC_ENDPOINT} ")
                .unmarshal().json(JsonLibrary.Jackson, Notification.class)
                .process(notificationAuditMappingProcessor)
                .marshal().json(JsonLibrary.Jackson,true)
                .process(notificationAuditLogMappingProcessor)
                .bean(notificationAuditLogDAO, "persist")
                .to("log:NOTIFICATION_SEGREGATION_ROUTE-END?showAll=true&level=info")
        .end();
    }
}