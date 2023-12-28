package com.quinbay.UserService.model.vo;

import lombok.Data;

@Data
public class UserVo {
    private int id;
    private String employeeName;
    private String email;
    private String phoneNum;
    private int managerId;
    private String role;
    private String message = "Success";

}
