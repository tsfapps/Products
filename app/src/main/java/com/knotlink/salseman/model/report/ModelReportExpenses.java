package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportExpenses {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("parking")
    @Expose
    private String parking;
    @SerializedName("petrol")
    @Expose
    private String petrol;
    @SerializedName("road_toll_fee")
    @Expose
    private String roadTollFee;
    @SerializedName("petty_cash")
    @Expose
    private String pettyCash;
    @SerializedName("others")
    @Expose
    private String others;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("remarks")
    @Expose
    private String remarks;

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getPetrol() {
        return petrol;
    }

    public void setPetrol(String petrol) {
        this.petrol = petrol;
    }

    public String getRoadTollFee() {
        return roadTollFee;
    }

    public void setRoadTollFee(String roadTollFee) {
        this.roadTollFee = roadTollFee;
    }

    public String getPettyCash() {
        return pettyCash;
    }

    public void setPettyCash(String pettyCash) {
        this.pettyCash = pettyCash;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
