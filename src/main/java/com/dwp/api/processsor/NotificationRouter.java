package com.dwp.api.processsor;

import com.dwp.api.model.Notification;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.Exchange;

import java.io.IOException;

public class NotificationRouter {
    public void route(Exchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        Notification notification = mapper.readValue(exchange.getIn().getBody(String.class), Notification.class);
        exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", notification.getDocumentAuthority() + "." + notification.getDocumentType());
        exchange.getIn().setHeader("DYNAMIC_ENDPOINT", "rabbitmq:localhost:5672/cbr?username=guest&password=guest&autoDelete=false&routingKey=" + notification.getDocumentAuthority() + "." + notification.getDocumentType() + "&queue=NOTIFICATION." + notification.getDocumentAuthority() + "." + notification.getDocumentType());

    }
}
