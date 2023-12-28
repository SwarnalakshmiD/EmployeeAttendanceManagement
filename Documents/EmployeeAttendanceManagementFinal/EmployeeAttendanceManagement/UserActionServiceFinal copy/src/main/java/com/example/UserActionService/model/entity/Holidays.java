package com.example.UserActionService.model.entity;

import com.example.UserActionService.model.constant.FieldName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name=FieldName.HOLIDAY)
@Data
public class Holidays {
    @Id
    @Column(name=FieldName.HOLIDAY_DATE)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String holidayDate;

    @Column(name=FieldName.HOLIDAY_TYPE,nullable=false)
    private String holidayType;
}
