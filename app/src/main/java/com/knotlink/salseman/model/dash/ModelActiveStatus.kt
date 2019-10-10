package com.knotlink.salseman.model.dash

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.knotlink.salseman.utils.DateUtils


class ModelActiveStatus {
    @SerializedName("active_status")
    @Expose
    private var activeStatus: String? = null
    @SerializedName("attendance_status")
    @Expose
    private var attendanceStatus: String? = null
    @SerializedName("date")
    @Expose
    private var date: String? = null
    @SerializedName("day")
    @Expose
    private var day: String? = null
    @SerializedName("time")
    @Expose
    private var time: String? = null

    fun getActiveStatus(): String? {
        return activeStatus
    }

    fun setActiveStatus(activeStatus: String) {
        this.activeStatus = activeStatus
    }

    fun getAttendanceStatus(): String? {
        return attendanceStatus
    }

    fun setAttendanceStatus(attendanceStatus: String) {
        this.attendanceStatus = attendanceStatus
    }

    fun getDate(): String? {
        return DateUtils.convertFormatOpposite(date)
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getDay(): String? {
        return day
    }

    fun setDay(day: String) {
        this.day = day
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String) {
        this.time = time
    }
}