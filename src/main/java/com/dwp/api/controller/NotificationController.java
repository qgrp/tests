package com.dwp.api.controller;

import com.dwp.api.model.Notification;
import com.dwp.api.route.NotificationRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("notify")
public class NotificationController {
    @Autowired
    ProducerTemplate producerTemplate;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public HttpEntity<Object> notifyDocument(@RequestBody Notification notification) {
        producerTemplate.requestBody(NotificationRoute.NOTIFICATION_ROUTE,notification);
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);
        return response;
    }
}
