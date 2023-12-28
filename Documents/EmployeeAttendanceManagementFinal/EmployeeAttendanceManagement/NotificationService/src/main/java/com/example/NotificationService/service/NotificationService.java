package com.example.NotificationService.service;

import com.example.NotificationService.model.vo.NotificationVo;

import java.util.List;

public interface NotificationService {

    List<NotificationVo> getNotifications(int id) ;

    String addNotification(NotificationVo notification);

    String updateReadStatus(String notificationId);

    List<NotificationVo> getUnreadNotifications(int id);


}
