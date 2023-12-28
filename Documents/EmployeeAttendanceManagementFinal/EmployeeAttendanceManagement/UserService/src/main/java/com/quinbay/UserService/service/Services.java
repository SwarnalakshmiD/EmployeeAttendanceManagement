package com.quinbay.UserService.service;
import com.quinbay.UserService.model.entity.User;
import com.quinbay.UserService.model.vo.LoginVo;
import com.quinbay.UserService.model.vo.NotificationVo;
import com.quinbay.UserService.model.vo.UserVo;

import java.util.List;

public interface Services {
    List<UserVo> getAllUserDetails();


    UserVo loginCheck(LoginVo loginVo);
//    UserVo getAllUserDetailsByName(String userName);

    List<UserVo> getAllUserDetailsByManagerId(int managerId);


    String nudgeEmployee(NotificationVo notification);

    UserVo getManager(int empId);
}
