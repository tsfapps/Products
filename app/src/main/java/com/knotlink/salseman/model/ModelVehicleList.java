package com.knotlink.salseman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVehicleList {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vehicle_no")
    @Expose
    private String vehicleNo;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("vehicle_image")
    @Expose
    private String vehicleImage;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

}
