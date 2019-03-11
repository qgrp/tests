package com.dwp.api.processsor;

import com.dwp.api.model.Notification;
import com.dwp.api.model.NotificationAudit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NotificationAuditMappingProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("processing NotificationAudit Object");
        Notification notificationObject = (Notification) exchange.getIn().getBody();
        NotificationAudit notificationAuditObject = new NotificationAudit();
        notificationAuditObject.setAuthority(notificationObject.getDocumentAuthority());
        notificationAuditObject.setContent(notificationObject.getDocumentContent());
        notificationAuditObject.setReference(notificationObject.getDocumentId());
        notificationAuditObject.setType(notificationObject.getDocumentType());
        exchange.getIn().setBody(notificationAuditObject);
        log.info("processed NotificationAudit Object");
    }

}
