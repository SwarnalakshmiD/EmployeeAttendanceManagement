package com.example.NotificationService.model.entity;

import com.example.NotificationService.model.constant.FieldNames;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = FieldNames.NOTIFICATION_T)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @Column(name = FieldNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = FieldNames.EMP_ID, nullable = false)
    private int empId;

    @Column(name = FieldNames.NOTIFICATION_MESSAGE, nullable = false)
    private String notificationMessage;

    @Column(name = FieldNames.READ_STATUS, nullable = true)
    private String readStatus;

    @Column(name = FieldNames.ACTION_TYPE, nullable = true)
    private String actionType;

    @Column(name = FieldNames.ACTION_START_DATE, nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actionStartDate;

    @Column(name = FieldNames.UPDATED_DATE, nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;

}
