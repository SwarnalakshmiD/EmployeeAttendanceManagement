package com.quinbay.UserService.model.vo;

import lombok.Data;

@Data
public class NotificationVo {
    public int empId;
    public String notificationMessage;

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String readStatus;

}
