package com.quinbay.UserService.model.entity;

import com.quinbay.UserService.model.constant.FieldNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = FieldNames.LOGIN_T)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    @Column(name  = FieldNames.LOGIN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int loginId;
    @Column(name  = FieldNames.EMPLOYEE_ID)
    public int employeeId;
    @Column(name  = FieldNames.USER_NAME)
    private String userName;
    @Column(name  = FieldNames.PASSWORD)
    public String password;

}
