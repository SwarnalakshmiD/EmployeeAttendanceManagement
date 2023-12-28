package com.quinbay.EmployeeAttendaceSystem.model.vo;


public class CountOfEmployees {
    int presentCount;
    int leaveCount;
    int wfhCount;
    int totalCount;
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getPresentCount() {
        return presentCount;
    }
    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }
    public int getLeaveCount() {
        return leaveCount;
    }
    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }
    public int getWfhCount() {
        return wfhCount;
    }
    public void setWfhCount(int wfhCount) {
        this.wfhCount = wfhCount;
    }
}