package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelReportOrderMap implements Serializable {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("lat_long_address")
    @Expose
    private String latLongAddress;
    @SerializedName("area_status")
    @Expose
    private String areaStatus;
    @SerializedName("image")
    @Expose
    private String image;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLatLongAddress() {
        return latLongAddress;
    }

    public void setLatLongAddress(String latLongAddress) {
        this.latLongAddress = latLongAddress;
    }

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
