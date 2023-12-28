package com.example.UserActionService.controller;

import com.example.UserActionService.model.vo.*;
import com.example.UserActionService.services.ActionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actions")

public class UserActionController {

    @Autowired
    ActionServices actionServices;

    @GetMapping("/viewswipehistory")
    public List<SwipeHistoryVo> getSwipeHistory(@RequestParam int id)
    {
        return actionServices.getUserSwipehistory(id);
    }

    @PostMapping("/swipecard")
     public String saveSwipeDetails(@RequestBody SwipeHistoryVo swipeHistoryVo)

    {
            return actionServices.userSwipeDetails(swipeHistoryVo);
    }

    @PostMapping("/applyactions")
    public String applyAction(@RequestParam int id, @RequestBody OperationsVo operation)
    {
        return actionServices.applyUserActions(id,operation);
    }

    @PutMapping("/approval")
    public String actionUpdate(@RequestBody OperationsVo operation)
    {

        return actionServices.applyActionStatus(operation);
    }

    @GetMapping("/viewactionhistory")
    public List<OperationsVo> getActionHistory(@RequestParam int id)
    {
        return actionServices.getUserActionhistory(id);
    }

    @GetMapping("/viewpendingstatus")
    public List<OperationsVo> viewPendingStatus(@RequestParam int id)
    {
        System.out.println(id);
        return actionServices.viewEmployeePendingStatus(id);
    }

    @PostMapping("/report")
    public List<ReportVo> reportGenertion(@RequestBody ReportVo reportVo)
    {
        return actionServices.report(reportVo);
    }

    @GetMapping("/holidays")
    public List<HolidaysVo> getHolidays()
    {
        return actionServices.getAllHolidays();
    }

    @GetMapping("/count")
    public CountOfEmployees count(@RequestParam int id)
    {
        return actionServices.countOfEmployees(id);
    }

        @GetMapping("/notification")
        public String sendNotification(@RequestParam int id)
        {
            return actionServices.nudgeEmployeeDetails(id);
        }



}
