package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.R
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.knotlink.salseman.utils.Constant


class ModelOrderList {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: String? = null
    @SerializedName("shop_id")
    @Expose
    private var shopId: String? = null
    @SerializedName("date_of_delivery")
    @Expose
    private var dateOfDelivery: String? = null
    @SerializedName("remarks")
    @Expose
    private var remarks: String? = null
    @SerializedName("signature")
    @Expose
    private var signature: String? = null
    @SerializedName("ordered_image")
    @Expose
    private var orderedImage: String? = null
    @SerializedName("product_id")
    @Expose
    private var productId: String? = null
    @SerializedName("datetime")
    @Expose
    private var datetime: String? = null
    @SerializedName("time")
    @Expose
    private var time: String? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("city")
    @Expose
    private var city: Any? = null
    @SerializedName("pincode")
    @Expose
    private var pincode: Any? = null
    @SerializedName("address")
    @Expose
    private var address: Any? = null
    @SerializedName("area_status")
    @Expose
    private var areaStatus: String? = null
    @SerializedName("activity_address")
    @Expose
    private var activityAddress: String? = null
    @SerializedName("order_status")
    @Expose
    private var orderStatus: String? = null
    @SerializedName("order_remarks")
    @Expose
    private var orderRemarks: String? = null
    @SerializedName("delivery_datetime")
    @Expose
    private var deliveryDatetime: String? = null
    @SerializedName("dispatcher_id")
    @Expose
    private var dispatcherId: String? = null
    @SerializedName("delivery_lat")
    @Expose
    private var deliveryLat: String? = null
    @SerializedName("delivery_long")
    @Expose
    private var deliveryLong: String? = null
    @SerializedName("delivery_address")
    @Expose
    private var deliveryAddress: String? = null
    @SerializedName("shop_name")
    @Expose
    private var shopName: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getShopId(): String? {
        return shopId
    }

    fun setShopId(shopId: String) {
        this.shopId = shopId
    }

    fun getDateOfDelivery(): String? {
        return dateOfDelivery
    }

    fun setDateOfDelivery(dateOfDelivery: String) {
        this.dateOfDelivery = dateOfDelivery
    }

    fun getRemarks(): String? {
        return remarks
    }

    fun setRemarks(remarks: String) {
        this.remarks = remarks
    }

    fun getSignature(): String? {
        return signature
    }

    fun setSignature(signature: String) {
        this.signature = signature
    }

    fun getOrderedImage(): String? {
        val imgUrl = Constant.URL_IMG_COMPLAIN
        return imgUrl+orderedImage
    }

    fun setOrderedImage(orderedImage: String) {
        this.orderedImage = orderedImage
    }

    fun getProductId(): String? {
        return productId
    }

    fun setProductId(productId: String) {
        this.productId = productId
    }

    fun getDatetime(): String? {
        return datetime
    }

    fun setDatetime(datetime: String) {
        this.datetime = datetime
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String) {
        this.time = time
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

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getCity(): Any? {
        return city
    }

    fun setCity(city: Any) {
        this.city = city
    }

    fun getPincode(): Any? {
        return pincode
    }

    fun setPincode(pincode: Any) {
        this.pincode = pincode
    }

    fun getAddress(): Any? {
        return address
    }

    fun setAddress(address: Any) {
        this.address = address
    }

    fun getAreaStatus(): String? {
        return areaStatus
    }

    fun setAreaStatus(areaStatus: String) {
        this.areaStatus = areaStatus
    }

    fun getActivityAddress(): String? {
        return activityAddress
    }

    fun setActivityAddress(activityAddress: String) {
        this.activityAddress = activityAddress
    }

    fun getOrderStatus(): String? {
        return orderStatus
    }

    fun setOrderStatus(orderStatus: String) {
        this.orderStatus = orderStatus
    }

    fun getOrderRemarks(): String? {
        return orderRemarks
    }

    fun setOrderRemarks(orderRemarks: String) {
        this.orderRemarks = orderRemarks
    }

    fun getDeliveryDatetime(): String? {
        return deliveryDatetime
    }

    fun setDeliveryDatetime(deliveryDatetime: String) {
        this.deliveryDatetime = deliveryDatetime
    }

    fun getDispatcherId(): String? {
        return dispatcherId
    }

    fun setDispatcherId(dispatcherId: String) {
        this.dispatcherId = dispatcherId
    }

    fun getDeliveryLat(): String? {
        return deliveryLat
    }

    fun setDeliveryLat(deliveryLat: String) {
        this.deliveryLat = deliveryLat
    }

    fun getDeliveryLong(): String? {
        return deliveryLong
    }

    fun setDeliveryLong(deliveryLong: String) {
        this.deliveryLong = deliveryLong
    }

    fun getDeliveryAddress(): String? {
        return deliveryAddress
    }

    fun setDeliveryAddress(deliveryAddress: String) {
        this.deliveryAddress = deliveryAddress
    }

    fun getShopName(): String? {
        return shopName
    }

    fun setShopName(shopName: String) {
        this.shopName = shopName
    }

    @BindingAdapter("orderedImage")
    fun loadImage(tImageView: ImageView, tImgUrl: String) {
        Glide.with(tImageView.context)
                .setDefaultRequestOptions(RequestOptions().circleCrop())
                .load(tImgUrl)
                .placeholder(com.knotlink.salseman.R.drawable.main_logo)
                .into(tImageView)
    }}