package com.example.NotificationService.dao.api;

import com.example.NotificationService.model.entity.Notifications;
import com.example.NotificationService.model.vo.NotificationVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends MongoRepository<Notifications, String> {
    List<Notifications> findAllByEmpId(int empId);
    List<NotificationVo> findAllByEmpIdAndReadStatus(int empId, String readStatus);
}
