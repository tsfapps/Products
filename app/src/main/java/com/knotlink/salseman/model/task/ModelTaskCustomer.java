package com.knotlink.salseman.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.utils.DateUtils;

public class ModelTaskCustomer {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("task_assign_date")
    @Expose
    private String taskAssignDate;
    @SerializedName("task_rescheduled_date")
    @Expose
    private String taskRescheduledDate;
    @SerializedName("task_due_date")
    @Expose
    private String taskDueDate;
    @SerializedName("task_completion_date")
    @Expose
    private String taskCompletionDate;
    @SerializedName("task_time")
    @Expose
    private String taskTime;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("task_type")
    @Expose
    private String taskType;
    @SerializedName("special_request_type")
    @Expose
    private String specialRequestType;
    @SerializedName("task_status")
    @Expose
    private String taskStatus;
    @SerializedName("area_status")
    @Expose
    private String areaStatus;
    @SerializedName("notification")
    @Expose
    private String notification;
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
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("lat_long_address")
    @Expose
    private String latLongAddress;
    @SerializedName("day_status")
    @Expose
    private String dayStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskAssignDate() {

        return DateUtils.yyyy_MM_dd_HH_mm_ss(taskAssignDate);
    }

    public void setTaskAssignDate(String taskAssignDate) {
        this.taskAssignDate = taskAssignDate;
    }

    public String getTaskRescheduledDate() {
        return taskRescheduledDate;
    }

    public void setTaskRescheduledDate(String taskRescheduledDate) {
        this.taskRescheduledDate = taskRescheduledDate;
    }

    public String getTaskDueDate() {

        return DateUtils.dateFormatDdMmmYyyy(taskDueDate);
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

    public String getRemarks() {
        return remarks;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSpecialRequestType() {
        return specialRequestType;
    }

    public void setSpecialRequestType(String specialRequestType) {
        this.specialRequestType = specialRequestType;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLatLongAddress() {
        return latLongAddress;
    }

    public void setLatLongAddress(String latLongAddress) {
        this.latLongAddress = latLongAddress;
    }

    public String getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(String dayStatus) {
        this.dayStatus = dayStatus;
    }
}
