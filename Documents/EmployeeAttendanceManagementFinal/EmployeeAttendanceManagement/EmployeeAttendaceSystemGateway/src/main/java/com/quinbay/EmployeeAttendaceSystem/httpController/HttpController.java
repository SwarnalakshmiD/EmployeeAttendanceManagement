package com.quinbay.EmployeeAttendaceSystem.httpController;

import com.quinbay.EmployeeAttendaceSystem.model.vo.*;
import com.quinbay.EmployeeAttendaceSystem.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class HttpController {
    @Autowired
    Services service;

    String jsonStringSuccess = "{\"message\":\"Success!\"}";
    String jsonStringFailure = "{\"message\":\"Failure!\"}";
    String jsonStringMessage = "{\"message\":\"This date is already an Holiday,Cant make entry!\"}";
    String jsonStringAction = "{\"message\":\"Already applied for leave or wfh in these dates!\"}";


    @GetMapping("/users")
    public List<UserVo> getAllUserDetails() {
        System.out.println(service.getAllUserDetails());
        return service.getAllUserDetails();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody LoginVo loginVo) {

            UserVo userVo =  service.loginCheck(loginVo);
        if(userVo.getId() > 1){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(userVo);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(jsonStringFailure);
    }




//    @GetMapping("/user/{userName}")
//    public UserVo getAllUserDetailsByName(@PathVariable String userName) {
//
//        return service.getAllUserDetailsByName(userName);
//    }

    @GetMapping("/manager/{managerId}")
    public List<UserVo> getAllUserDetailsByManagerId(@PathVariable int managerId) {
        return service.getAllUserDetailsByManagerId(managerId);

    }


    @GetMapping("/viewswipehistory/{id}")
    public List<SwipeHistoryVo> getSwipeHistory(@PathVariable int id)
    {

        return service.getUserSwipehistory(id);
    }

    @PostMapping("/swipecard")
    public ResponseEntity<String> saveSwipeDetails(@RequestBody SwipeHistoryVo swipeHistoryVo)

    {
        String result = service.userSwipeDetails(swipeHistoryVo);

        if(result.equals("Success")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(jsonStringMessage);
    }

    @PostMapping("/applyactions/{id}")
    public ResponseEntity<String> applyAction(@PathVariable int id, @RequestBody OperationsVo operationVo)
    {
        String res = service.applyUserActions(id,operationVo);
        if(res.equals("Success")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(jsonStringAction);
    }

    @PutMapping("/approval")
    public ResponseEntity<String> actionUpdate( @RequestBody OperationsVo operationVo)
    {

        String res = service.applyActionStatus(operationVo);
        if(res.contains("updated")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(jsonStringFailure);

    }

    @GetMapping("/viewactionhistory/{id}")
    public List<OperationsVo> getActionHistory(@PathVariable int id)
    {
        return service.getUserActionhistory(id);
    }

    @GetMapping("/viewpendingstatus/{managerId}")
    public List<OperationsVo> viewPendingStatus(@PathVariable int managerId)
    {

        return service.viewEmployeePendingStatus(managerId);
    }


    @GetMapping("/getUnreadNotification/{id}")
    public List<NotificationVo> getUnreadNotification(@PathVariable int id)
    {
        return service.getUserUnreadNotification(id);
    }

    @PutMapping("/updateNotification/{notificationId}")
    public ResponseEntity<String>  updateNotificationStatus(@PathVariable String notificationId)
    {
        String res=service.updateNotificationReadStatus(notificationId);
        if(res.contains("updated")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(jsonStringFailure);


    }

    @PostMapping("/nudge")
    public ResponseEntity<String>  nudgeEmployeeByManager(@RequestBody NotificationVo notification)
    {
        String res= service.nudgeEmployee(notification);
        if(res.contains("updated")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(jsonStringFailure);
    }

    @PostMapping("/report")
    public List<ReportVo> ReportGenertion(@RequestBody ReportVo reportVo)
    {
        return service.reportGeneration(reportVo);
    }


    @GetMapping("/holidays")
    public List<HolidaysVo> getHolidays()
    {
        return service.getAllHolidays();
    }

    @GetMapping("/count/{id}")
    public CountOfEmployees count(@PathVariable int id)
    {
        return service.countOfEmployees(id);
    }
    @GetMapping("/notification")
    public ResponseEntity<String> sendNotification(@RequestParam int id)
    {
        String res= service.nudgeEmployeeDetails(id);
        if(res.equals("Success")){

            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonStringSuccess);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(jsonStringFailure);
    }



}
