package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.knotlink.salseman.utils.DateUtils


class ModelSalesReturnShopFetch {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("shop_id")
    @Expose
    private var shopId: String? = null
    @SerializedName("invoice_no")
    @Expose
    private var invoiceNo: String? = null
    @SerializedName("invoice_date")
    @Expose
    private var invoiceDate: String? = null
    @SerializedName("article_no")
    @Expose
    private var articleNo: String? = null
    @SerializedName("size")
    @Expose
    private var size: String? = null
    @SerializedName("mrp")
    @Expose
    private var mrp: String? = null
    @SerializedName("quantity")
    @Expose
    private var quantity: String? = null
    @SerializedName("remarks")
    @Expose
    private var remarks: String? = null
    @SerializedName("image")
    @Expose
    private var image: String? = null
    @SerializedName("datetime")
    @Expose
    private var datetime: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: String? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("activity_address")
    @Expose
    private var activityAddress: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("shop_name")
    @Expose
    private var shopName: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getShopId(): String? {
        return shopId
    }

    fun setShopId(shopId: String) {
        this.shopId = shopId
    }

    fun getInvoiceNo(): String? {
        return "Invoice No : $invoiceNo"
    }

    fun setInvoiceNo(invoiceNo: String) {
        this.invoiceNo = invoiceNo
    }

    fun getInvoiceDate(): String? {
        return DateUtils.convertYyyyToDd(invoiceDate)
    }

    fun setInvoiceDate(invoiceDate: String) {
        this.invoiceDate = invoiceDate
    }

    fun getArticleNo(): String? {
        return "Article No : $articleNo"
    }

    fun setArticleNo(articleNo: String) {
        this.articleNo = articleNo
    }

    fun getSize(): String? {
        return "Size : $size"
    }

    fun setSize(size: String) {
        this.size = size
    }

    fun getMrp(): String? {
        return "Mrp : $mrp"
    }

    fun setMrp(mrp: String) {
        this.mrp = mrp
    }

    fun getQuantity(): String? {
        return "Quantity : $quantity"
    }

    fun setQuantity(quantity: String) {
        this.quantity = quantity
    }

    fun getRemarks(): String? {
        return "Remarks\n$remarks"
    }

    fun setRemarks(remarks: String) {
        this.remarks = remarks
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun getDatetime(): String? {
        return datetime
    }

    fun setDatetime(datetime: String) {
        this.datetime = datetime
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
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

    fun getActivityAddress(): String? {
        return activityAddress
    }

    fun setActivityAddress(activityAddress: String) {
        this.activityAddress = activityAddress
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getShopName(): String? {
        return shopName
    }

    fun setShopName(shopName: String) {
        this.shopName = shopName
    }
}