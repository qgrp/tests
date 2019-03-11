package com.dwp.api.dao;


import com.dwp.api.model.NotificationAuditLog;
import com.dwp.api.repository.NotificationAuditLogRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationAuditLogDAO extends AbstractDao<Integer, NotificationAuditLog>{

    @Autowired
    NotificationAuditLogRespository notificationAuditLogRespository;


}