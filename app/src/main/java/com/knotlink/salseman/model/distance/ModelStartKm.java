package com.knotlink.salseman.model.distance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelStartKm {

    @SerializedName("starting_km")
    @Expose
    private String startingKm;
    @SerializedName("starting_image")
    @Expose
    private String startingImage;

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
}
