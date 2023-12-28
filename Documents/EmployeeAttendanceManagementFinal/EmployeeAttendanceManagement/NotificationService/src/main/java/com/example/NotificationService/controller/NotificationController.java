package com.example.NotificationService.controller;
import com.example.NotificationService.model.vo.NotificationVo;
import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins="*")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("getAllNotification/{id}")
    public List<NotificationVo> getNotificationsById(@PathVariable int id){

        return notificationService.getNotifications(id);
    }

    @PostMapping("/AddNotification/")
    public String addNotify(@RequestBody NotificationVo notification) {
        return notificationService.addNotification(notification);
    }

    @PutMapping("/updateNotification/{notificationId}")
    public String updateNotificationStatus(@PathVariable String notificationId)
    {
        return notificationService.updateReadStatus(notificationId);

    }

    @GetMapping("getNotification/{id}")
    public List<NotificationVo> getUnreadNotificationsById(@PathVariable int id){

        return notificationService.getUnreadNotifications(id);
    }



}
