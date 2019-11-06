package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.utils.DateUtils;

public class ModelReportAttendance {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("attendance_status")
    @Expose
    private String attendanceStatus;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("login_date")
    @Expose
    private String loginDate;
    @SerializedName("login_time")
    @Expose
    private String loginTime;
    @SerializedName("login_latitude")
    @Expose
    private String loginLatitude;
    @SerializedName("login_longitude")
    @Expose
    private String loginLongitude;
    @SerializedName("login_city")
    @Expose
    private String loginCity;
    @SerializedName("login_pincode")
    @Expose
    private String loginPincode;
    @SerializedName("login_address")
    @Expose
    private String loginAddress;
    @SerializedName("logout_date")
    @Expose
    private String logoutDate;
    @SerializedName("logout_time")
    @Expose
    private String logoutTime;
    @SerializedName("logout_latitude")
    @Expose
    private String logoutLatitude;
    @SerializedName("logout_longitude")
    @Expose
    private String logoutLongitude;
    @SerializedName("logout_city")
    @Expose
    private String logoutCity;
    @SerializedName("logout_pincode")
    @Expose
    private String logoutPincode;
    @SerializedName("logout_address")
    @Expose
    private String logoutAddress;
    @SerializedName("total_time_duration")
    @Expose
    private String totalTimeDuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginDate() {
        return DateUtils.dateFormatDdMmmYyyy(loginDate);
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginLatitude() {
        return loginLatitude;
    }

    public void setLoginLatitude(String loginLatitude) {
        this.loginLatitude = loginLatitude;
    }

    public String getLoginLongitude() {
        return loginLongitude;
    }

    public void setLoginLongitude(String loginLongitude) {
        this.loginLongitude = loginLongitude;
    }

    public String getLoginCity() {
        return loginCity;
    }

    public void setLoginCity(String loginCity) {
        this.loginCity = loginCity;
    }

    public String getLoginPincode() {
        return loginPincode;
    }

    public void setLoginPincode(String loginPincode) {
        this.loginPincode = loginPincode;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getLogoutDate() {
        return DateUtils.dateFormatDdMmmYyyy(logoutDate);
    }

    public void setLogoutDate(String logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLogoutLatitude() {
        return logoutLatitude;
    }

    public void setLogoutLatitude(String logoutLatitude) {
        this.logoutLatitude = logoutLatitude;
    }

    public String getLogoutLongitude() {
        return logoutLongitude;
    }

    public void setLogoutLongitude(String logoutLongitude) {
        this.logoutLongitude = logoutLongitude;
    }

    public String getLogoutCity() {
        return logoutCity;
    }

    public void setLogoutCity(String logoutCity) {
        this.logoutCity = logoutCity;
    }

    public String getLogoutPincode() {
        return logoutPincode;
    }

    public void setLogoutPincode(String logoutPincode) {
        this.logoutPincode = logoutPincode;
    }

    public String getLogoutAddress() {
        return logoutAddress;
    }

    public void setLogoutAddress(String logoutAddress) {
        this.logoutAddress = logoutAddress;
    }

    public String getTotalTimeDuration() {
        return totalTimeDuration;
    }

    public void setTotalTimeDuration(String totalTimeDuration) {
        this.totalTimeDuration = totalTimeDuration;
    }

}
