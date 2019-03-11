package com.dwp.api.processsor;

import com.dwp.api.model.Notification;
import com.dwp.api.model.NotificationAudit;
import com.dwp.api.model.NotificationAuditLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
@Slf4j
public class NotificationAuditLogMappingProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("preparing a log for notification");
        String notificationAuditObject = exchange.getIn().getBody(String.class);
        NotificationAuditLog notificationAuditLogObject = new NotificationAuditLog();
        notificationAuditLogObject.setNotification(notificationAuditObject);
        exchange.getIn().setBody(notificationAuditLogObject);
        log.info("prepared a log for notification");
    }

}
