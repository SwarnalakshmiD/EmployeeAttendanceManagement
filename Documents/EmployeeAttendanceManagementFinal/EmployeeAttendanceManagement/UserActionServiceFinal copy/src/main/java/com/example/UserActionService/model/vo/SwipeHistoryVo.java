package com.example.UserActionService.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwipeHistoryVo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public Time getSwipeTime() {
        return swipeTime;
    }

    public void setSwipeTime(Time swipeTime) {
        this.swipeTime = swipeTime;
    }



    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    private int employeeId;

    public String getSwipeDate() {
        return swipeDate;
    }

    public void setSwipeDate(String swipeDate) {
        this.swipeDate = swipeDate;
    }

    private String swipeDate;

//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    public Date getSwipeDate() {
//        return swipeDate;
//    }
//
//    public void setSwipeDate(Date swipeDate) {
//        this.swipeDate = swipeDate;
//    }

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date swipeDate;
    private Time swipeTime;


}
