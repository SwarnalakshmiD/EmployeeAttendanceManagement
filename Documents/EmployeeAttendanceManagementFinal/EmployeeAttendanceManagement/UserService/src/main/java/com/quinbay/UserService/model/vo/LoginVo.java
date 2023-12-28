package com.quinbay.UserService.model.vo;

import lombok.Data;

@Data
public class LoginVo {
    private int loginId;
    private int employeeId;
    public String userName;
    public String password;
}
