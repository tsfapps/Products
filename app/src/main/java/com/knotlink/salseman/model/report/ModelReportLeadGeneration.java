package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.utils.DateUtils;

public class ModelReportLeadGeneration {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("meeting_date")
    @Expose
    private String meetingDate;
    @SerializedName("meeting_time")
    @Expose
    private String meetingTime;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("org_name")
    @Expose
    private String orgName;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_contact_no")
    @Expose
    private String customerContactNo;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("area_status")
    @Expose
    private String areaStatus;

    public String getDatetime() {
        return "Date\n"+DateUtils.dateFormatDdMmmYyyy(datetime);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMeetingDate() {
        return "Meeting Date\n"+DateUtils.dateFormatDdMmmYyyy(meetingDate);
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return "Meeting Time\n"+meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getUserName() {
        return "Lead Generator\n"+userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCustomerName() {
        return "Contact Person\n"+customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactNo() {
        return "Contact Number\n"+customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getRemarks() {
        return "Remarks\n"+remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }
}
