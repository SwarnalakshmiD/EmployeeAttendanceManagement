package com.example.UserActionService.services;

import com.example.UserActionService.model.vo.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActionServices {
    List<SwipeHistoryVo> getUserSwipehistory(int id);

    String applyUserActions(int id, OperationsVo operations);

    String applyActionStatus(OperationsVo operation);

    String userSwipeDetails(SwipeHistoryVo swipeHistory);

    List<OperationsVo> getUserActionhistory(int id);

    List<OperationsVo> viewEmployeePendingStatus(int id);
    List<ReportVo> report(ReportVo reportVo);
    List<HolidaysVo> getAllHolidays();

    CountOfEmployees countOfEmployees(int id);

    String nudgeEmployeeDetails(int id);
}
