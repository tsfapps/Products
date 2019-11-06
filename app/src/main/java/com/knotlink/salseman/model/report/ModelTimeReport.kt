package com.knotlink.salseman.model.report

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ModelTimeReport : Serializable{
    @SerializedName("shop")
    @Expose
    private var shop: String? = null
    @SerializedName("time")
    @Expose
    private var time: String? = null
    @SerializedName("duration")
    @Expose
    private var duration: String? = null
    @SerializedName("activity")
    @Expose
    private var activity: String? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null

    fun getShop(): String? {
        return shop
    }

    fun setShop(shop: String) {
        this.shop = shop
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String) {
        this.time = time
    }

    fun getDuration(): String? {
        return duration+"min"
    }

    fun setDuration(duration: String) {
        this.duration = duration
    }

    fun getActivity(): String? {
        return activity
    }

    fun setActivity(activity: String) {
        this.activity = activity
    }

    fun getLatitude(): String? {
        return latitude
    }

    fun setLatitude(latitude: String) {
        this.latitude = latitude
    }

    fun getLongitude(): String? {
        return longitude
    }

    fun setLongitude(longitude: String) {
        this.longitude = longitude
    }
}