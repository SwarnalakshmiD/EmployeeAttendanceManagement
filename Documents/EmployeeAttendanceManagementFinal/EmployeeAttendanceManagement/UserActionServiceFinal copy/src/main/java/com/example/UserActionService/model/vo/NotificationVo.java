package com.example.UserActionService.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationVo {
    private int empId;
    private String notificationMessage;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionStartDate() {
        return actionStartDate;
    }

    public void setActionStartDate(String actionStartDate) {
        this.actionStartDate = actionStartDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    private String readStatus;
   private String actionType;
    private String actionStartDate;
    private String updatedDate;
}
