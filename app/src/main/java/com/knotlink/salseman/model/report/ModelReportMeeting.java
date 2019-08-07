package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportMeeting {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("task_id")
    @Expose
    private String taskId;
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
    @SerializedName("org_name")
    @Expose
    private String orgName;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_contact_no")
    @Expose
    private String customerContactNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("user_remarks")
    @Expose
    private String userRemarks;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("route_id")
    @Expose
    private String routeId;
    @SerializedName("whatsapp_no")
    @Expose
    private String whatsappNo;
    @SerializedName("landline_no")
    @Expose
    private String landlineNo;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("task_type")
    @Expose
    private String taskType;
    @SerializedName("vendor_type")
    @Expose
    private String vendorType;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }
}
