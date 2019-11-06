package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelRequestList {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("task_id")
    @Expose
    private var taskId: String? = null
    @SerializedName("shop_id")
    @Expose
    private var shopId: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: String? = null
    @SerializedName("task_assign_date")
    @Expose
    private var taskAssignDate: String? = null
    @SerializedName("task_rescheduled_date")
    @Expose
    private var taskRescheduledDate: Any? = null
    @SerializedName("task_due_date")
    @Expose
    private var taskDueDate: String? = null
    @SerializedName("task_completion_date")
    @Expose
    private var taskCompletionDate: String? = null
    @SerializedName("task_time")
    @Expose
    private var taskTime: Any? = null
    @SerializedName("remarks")
    @Expose
    private var remarks: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("task_type")
    @Expose
    private var taskType: String? = null
    @SerializedName("special_request_type")
    @Expose
    private var specialRequestType: String? = null
    @SerializedName("task_status")
    @Expose
    private var taskStatus: String? = null
    @SerializedName("area_status")
    @Expose
    private var areaStatus: String? = null
    @SerializedName("notification")
    @Expose
    private var notification: String? = null
    @SerializedName("manager")
    @Expose
    private var manager: String? = null
    @SerializedName("sm")
    @Expose
    private var sm: String? = null
    @SerializedName("dm")
    @Expose
    private var dm: String? = null
    @SerializedName("asm")
    @Expose
    private var asm: String? = null
    @SerializedName("activity_address")
    @Expose
    private var activityAddress: String? = null
    @SerializedName("org_id")
    @Expose
    private var orgId: String? = null
    @SerializedName("shop_name")
    @Expose
    private var shopName: String? = null
    @SerializedName("contact_name")
    @Expose
    private var contactName: String? = null
    @SerializedName("contact_no")
    @Expose
    private var contactNo: String? = null
    @SerializedName("shop_address")
    @Expose
    private var shopAddress: String? = null
    @SerializedName("lat_long_address")
    @Expose
    private var latLongAddress: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getTaskId(): String? {
        return taskId
    }

    fun setTaskId(taskId: String) {
        this.taskId = taskId
    }

    fun getShopId(): String? {
        return shopId
    }

    fun setShopId(shopId: String) {
        this.shopId = shopId
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getTaskAssignDate(): String? {
        return taskAssignDate
    }

    fun setTaskAssignDate(taskAssignDate: String) {
        this.taskAssignDate = taskAssignDate
    }

    fun getTaskRescheduledDate(): Any? {
        return taskRescheduledDate
    }

    fun setTaskRescheduledDate(taskRescheduledDate: Any) {
        this.taskRescheduledDate = taskRescheduledDate
    }

    fun getTaskDueDate(): String? {
        return taskDueDate
    }

    fun setTaskDueDate(taskDueDate: String) {
        this.taskDueDate = taskDueDate
    }

    fun getTaskCompletionDate(): String? {
        return taskCompletionDate
    }

    fun setTaskCompletionDate(taskCompletionDate: String) {
        this.taskCompletionDate = taskCompletionDate
    }

    fun getTaskTime(): Any? {
        return taskTime
    }

    fun setTaskTime(taskTime: Any) {
        this.taskTime = taskTime
    }

    fun getRemarks(): String? {
        return remarks
    }

    fun setRemarks(remarks: String) {
        this.remarks = remarks
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
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

    fun getTaskType(): String? {
        return taskType
    }

    fun setTaskType(taskType: String) {
        this.taskType = taskType
    }

    fun getSpecialRequestType(): String? {
        return specialRequestType
    }

    fun setSpecialRequestType(specialRequestType: String) {
        this.specialRequestType = specialRequestType
    }

    fun getTaskStatus(): String? {
        return taskStatus
    }

    fun setTaskStatus(taskStatus: String) {
        this.taskStatus = taskStatus
    }

    fun getAreaStatus(): String? {
        return areaStatus
    }

    fun setAreaStatus(areaStatus: String) {
        this.areaStatus = areaStatus
    }

    fun getNotification(): String? {
        return notification
    }

    fun setNotification(notification: String) {
        this.notification = notification
    }

    fun getManager(): String? {
        return manager
    }

    fun setManager(manager: String) {
        this.manager = manager
    }

    fun getSm(): String? {
        return sm
    }

    fun setSm(sm: String) {
        this.sm = sm
    }

    fun getDm(): String? {
        return dm
    }

    fun setDm(dm: String) {
        this.dm = dm
    }

    fun getAsm(): String? {
        return asm
    }

    fun setAsm(asm: String) {
        this.asm = asm
    }

    fun getActivityAddress(): String? {
        return activityAddress
    }

    fun setActivityAddress(activityAddress: String) {
        this.activityAddress = activityAddress
    }

    fun getOrgId(): String? {
        return orgId
    }

    fun setOrgId(orgId: String) {
        this.orgId = orgId
    }

    fun getShopName(): String? {
        return shopName
    }

    fun setShopName(shopName: String) {
        this.shopName = shopName
    }

    fun getContactName(): String? {
        return contactName
    }

    fun setContactName(contactName: String) {
        this.contactName = contactName
    }

    fun getContactNo(): String? {
        return contactNo
    }

    fun setContactNo(contactNo: String) {
        this.contactNo = contactNo
    }

    fun getShopAddress(): String? {
        return shopAddress
    }

    fun setShopAddress(shopAddress: String) {
        this.shopAddress = shopAddress
    }

    fun getLatLongAddress(): String? {
        return latLongAddress
    }

    fun setLatLongAddress(latLongAddress: String) {
        this.latLongAddress = latLongAddress
    }
}