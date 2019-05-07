package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportDistance {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("starting_km")
    @Expose
    private String startingKm;
    @SerializedName("starting_image")
    @Expose
    private String startingImage;
    @SerializedName("ending_km")
    @Expose
    private String endingKm;
    @SerializedName("ending_image")
    @Expose
    private String endingImage;
    @SerializedName("distance_traveled")
    @Expose
    private String distanceTraveled;
    @SerializedName("previous_day")
    @Expose
    private String previousDay;
    @SerializedName("previous_month")
    @Expose
    private String previousMonth;
    @SerializedName("distance_status")
    @Expose
    private String distanceStatus;
    @SerializedName("start_lat")
    @Expose
    private String startLat;
    @SerializedName("start_long")
    @Expose
    private String startLong;
    @SerializedName("end_lat")
    @Expose
    private String endLat;
    @SerializedName("end_long")
    @Expose
    private String endLong;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("starting_address")
    @Expose
    private String startingAddress;
    @SerializedName("ending_address")
    @Expose
    private String endingAddress;
    @SerializedName("starting_pincode")
    @Expose
    private String startingPincode;
    @SerializedName("starting_city")
    @Expose
    private String startingCity;
    @SerializedName("ending_pincode")
    @Expose
    private String endingPincode;
    @SerializedName("ending_city")
    @Expose
    private String endingCity;
    @SerializedName("total_km")
    @Expose
    private String totalKm;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartingKm() {
        return startingKm;
    }

    public void setStartingKm(String startingKm) {
        this.startingKm = startingKm;
    }

    public String getStartingImage() {
        return startingImage;
    }

    public void setStartingImage(String startingImage) {
        this.startingImage = startingImage;
    }

    public String getEndingKm() {
        return endingKm;
    }

    public void setEndingKm(String endingKm) {
        this.endingKm = endingKm;
    }

    public String getEndingImage() {
        return endingImage;
    }

    public void setEndingImage(String endingImage) {
        this.endingImage = endingImage;
    }

    public String getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(String distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public String getPreviousDay() {
        return previousDay;
    }

    public void setPreviousDay(String previousDay) {
        this.previousDay = previousDay;
    }

    public String getPreviousMonth() {
        return previousMonth;
    }

    public void setPreviousMonth(String previousMonth) {
        this.previousMonth = previousMonth;
    }

    public String getDistanceStatus() {
        return distanceStatus;
    }

    public void setDistanceStatus(String distanceStatus) {
        this.distanceStatus = distanceStatus;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartingAddress() {
        return startingAddress;
    }

    public void setStartingAddress(String startingAddress) {
        this.startingAddress = startingAddress;
    }

    public String getEndingAddress() {
        return endingAddress;
    }

    public void setEndingAddress(String endingAddress) {
        this.endingAddress = endingAddress;
    }

    public String getStartingPincode() {
        return startingPincode;
    }

    public void setStartingPincode(String startingPincode) {
        this.startingPincode = startingPincode;
    }

    public String getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(String startingCity) {
        this.startingCity = startingCity;
    }

    public String getEndingPincode() {
        return endingPincode;
    }

    public void setEndingPincode(String endingPincode) {
        this.endingPincode = endingPincode;
    }

    public String getEndingCity() {
        return endingCity;
    }

    public void setEndingCity(String endingCity) {
        this.endingCity = endingCity;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

}
