package com.knotlink.salseman.model.distance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDistancePrevious {
    @SerializedName("distance_traveled")
    @Expose
    private String distanceTraveled;
    @SerializedName("excess_distance")
    @Expose
    private String excessDistance;
    @SerializedName("starting_km")
    @Expose
    private String startingKm;

    public String getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(String distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public String getExcessDistance() {
        return excessDistance;
    }

    public void setExcessDistance(String excessDistance) {
        this.excessDistance = excessDistance;
    }

    public String getStartingKm() {
        return startingKm;
    }

    public void setStartingKm(String startingKm) {
        this.startingKm = startingKm;
    }
}
