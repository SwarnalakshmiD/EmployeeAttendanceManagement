package com.quinbay.UserService.HttpController;


import com.quinbay.UserService.model.entity.User;
import com.quinbay.UserService.model.vo.LoginVo;
import com.quinbay.UserService.model.vo.NotificationVo;
import com.quinbay.UserService.model.vo.UserVo;
import com.quinbay.UserService.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userService")
public class HttpController {

    @Autowired
    Services service;
    @GetMapping("/user")
    public List<UserVo> getAllUserDetails(){
        System.out.println(service.getAllUserDetails());
        return service.getAllUserDetails();
    }
    @PostMapping("/user/login")
    public  UserVo loginCheck(@RequestBody LoginVo loginVo){
        return service.loginCheck(loginVo);
    }
//    @GetMapping("/user/name")
//    public  UserVo getAllUserDetailsByName(@RequestParam String userName){
//        return service.getAllUserDetailsByName(userName);
//    }
    @GetMapping("/user/manager")
    public  List<UserVo> getAllUserDetailsByManagerId(@RequestParam int managerId){
        return service.getAllUserDetailsByManagerId(managerId);
    }

    @PostMapping("/nudgeEmployee/")
    public String nudgeEmployeeByManager(@RequestBody NotificationVo notification)
    {
        return service.nudgeEmployee(notification);
    }

    @GetMapping("/manager/")
    public  UserVo getManagerById(@RequestParam int empId){

        return service.getManager(empId);
    }







}
