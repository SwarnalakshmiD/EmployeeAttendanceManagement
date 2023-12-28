package com.example.NotificationService.serviceimplementation;

import com.example.NotificationService.dao.api.NotificationRepo;
import com.example.NotificationService.dao.api.NotificationRepository;
import com.example.NotificationService.model.entity.Notifications;
import com.example.NotificationService.model.vo.NotificationVo;
import com.example.NotificationService.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service("notificationService")
public class NotificationServiceImpl  implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationRepo notificationRepo;


    @KafkaListener(topics="com.attendance.actions.create",groupId = "group-id")
    public void listen(String message) throws JsonProcessingException
    {
        ObjectMapper objectMapper=new ObjectMapper();
        NotificationVo result=objectMapper.readValue(message,NotificationVo.class);
        System.out.println("Received message in group - group-id "+result);
        addNotification(result);

    }

    public List<NotificationVo> getNotifications(int id)
    {
       //List<Notification> empNotification =notificationRepository.findAllByEmpId(id);
        List<Notifications> empNotifications =notificationRepo.findAllByEmpId(id);

        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.convertValue(empNotifications,List.class);
    }
    public String addNotification(NotificationVo notification)
    {

        ObjectMapper objectMapper=new ObjectMapper();

        Notifications notifications=objectMapper.convertValue(notification,Notifications.class);
        notificationRepo.save(notifications);
        return"notification added";
    }

    public String updateReadStatus(String notificationId)
    {
        ObjectMapper objectMapper=new ObjectMapper();
        Notifications notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        notification.setReadStatus("read");
        notificationRepo.save(objectMapper.convertValue(notification,Notifications.class));
        return "read status updated";
    }
    public List<NotificationVo> getUnreadNotifications(int id)
    {
        List<NotificationVo> empNotifications =notificationRepo.findAllByEmpIdAndReadStatus(id,"unread");
        for (NotificationVo notification : empNotifications) {
            System.out.println(notification);
        }

        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.convertValue(empNotifications,List.class);
    }



}
