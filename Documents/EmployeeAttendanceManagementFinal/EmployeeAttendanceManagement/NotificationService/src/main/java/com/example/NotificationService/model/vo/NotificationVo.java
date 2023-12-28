package com.example.NotificationService.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationVo {
   // public int id;
    public String id;
    private int empId;
    private String notificationMessage;
    private String readStatus;
    private String actionType;
    private String actionStartDate;
 private String updatedDate;
}
