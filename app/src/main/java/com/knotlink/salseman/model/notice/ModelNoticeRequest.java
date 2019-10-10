package com.knotlink.salseman.model.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNoticeRequest {
    @SerializedName("salesman")
    @Expose
    private String salesman;
    @SerializedName("dispatcher")
    @Expose
    private String dispatcher;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("task_assign_date")
    @Expose
    private String taskAssignDate;
    @SerializedName("task_due_date")
    @Expose
    private String taskDueDate;
    @SerializedName("special_request_type")
    @Expose
    private String specialRequestType;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("task_completion_date")
    @Expose
    private String taskCompletionDate;
    @SerializedName("manager")
    @Expose
    private String manager;
    @SerializedName("sm")
    @Expose
    private String sm;
    @SerializedName("dm")
    @Expose
    private String dm;
    @SerializedName("asm")
    @Expose
    private String asm;
    @SerializedName("task_pending_days")
    @Expose
    private String taskPendingDays;
    @SerializedName("task_completion_days")
    @Expose
    private String taskCompletionDays;

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTaskAssignDate() {
        return taskAssignDate;
    }

    public void setTaskAssignDate(String taskAssignDate) {
        this.taskAssignDate = taskAssignDate;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getSpecialRequestType() {
        return specialRequestType;
    }

    public void setSpecialRequestType(String specialRequestType) {
        this.specialRequestType = specialRequestType;
    }

    public String getRemarks() {
        String strRem = "Remarks : ";
        return strRem+remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskCompletionDate() {
        return taskCompletionDate;
    }

    public void setTaskCompletionDate(String taskCompletionDate) {
        this.taskCompletionDate = taskCompletionDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getTaskPendingDays() {
        String strDays = "Pending Days : ";
        return strDays+taskPendingDays;
    }

    public void setTaskPendingDays(String taskPendingDays) {
        this.taskPendingDays = taskPendingDays;
    }

    public String getTaskCompletionDays() {
        return taskCompletionDays;
    }

    public void setTaskCompletionDays(String taskCompletionDays) {
        this.taskCompletionDays = taskCompletionDays;
    }

}
