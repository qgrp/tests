package com.dwp.api.repository;

import com.dwp.api.model.NotificationAuditLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationAuditLogRespository extends PagingAndSortingRepository<NotificationAuditLog, Long> {

//    NotificationAuditLog findTopOrderByid();

}
