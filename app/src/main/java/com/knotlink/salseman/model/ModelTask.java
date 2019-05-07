package com.knotlink.salseman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTask {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("task_assign_date")
    @Expose
    private String taskAssignDate;
    @SerializedName("task_due_date")
    @Expose
    private String taskDueDate;
    @SerializedName("task_completion_date")
    @Expose
    private String taskCompletionDate;
    @SerializedName("task_time")
    @Expose
    private String taskTime;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_contact_no")
    @Expose
    private String customerContactNo;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("user_remarks")
    @Expose
    private String userRemarks;
    @SerializedName("remarks_date")
    @Expose
    private String remarksDate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTaskCompletionDate() {
        return taskCompletionDate;
    }

    public void setTaskCompletionDate(String taskCompletionDate) {
        this.taskCompletionDate = taskCompletionDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactNo() {
        return customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserRemarks() {
        return userRemarks;
    }

    public void setUserRemarks(String userRemarks) {
        this.userRemarks = userRemarks;
    }

    public String getRemarksDate() {
        return remarksDate;
    }

    public void setRemarksDate(String remarksDate) {
        this.remarksDate = remarksDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
