package com.quinbay.UserService.model.entity;

import com.quinbay.UserService.model.constant.FieldNames;
//import com.quinbay.UserService.model.entity.SwipeHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = FieldNames.USER_T)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name  = FieldNames.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name  = FieldNames.EMPLOYEE_NAME)
    public String employeeName;

    @Column(name  = FieldNames.EMAIL)
    private String email;
    @Column(name  = FieldNames.PHONENUM)
    private String phoneNum;
    @Column(name  = FieldNames.MANAGERID)
    private int managerId;
    @Column(name  = FieldNames.ROLE)
    private String role;




}
