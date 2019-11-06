package com.knotlink.salseman.model.notice;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.R;

public class ModelNoticeCheque {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("invoice_amount")
    @Expose
    private String invoiceAmount;
    @SerializedName("received_amount")
    @Expose
    private String receivedAmount;
    @SerializedName("total_outstanding_amount")
    @Expose
    private String totalOutstandingAmount;
    @SerializedName("total_balance")
    @Expose
    private String totalBalance;
    @SerializedName("invoice_outstanding_days")
    @Expose
    private String invoiceOutstandingDays;
    @SerializedName("pending_invoice_amount")
    @Expose
    private String pendingInvoiceAmount;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("neft_cheque_no")
    @Expose
    private String neftChequeNo;
    @SerializedName("neft_cheque_maturity_date")
    @Expose
    private String neftChequeMaturityDate;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("cheque_image")
    @Expose
    private String chequeImage;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("area_status")
    @Expose
    private String areaStatus;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("notification_status")
    @Expose
    private String notificationStatus;
    @SerializedName("pending_days")
    @Expose
    private String pendingDays;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(String totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getInvoiceOutstandingDays() {
        return invoiceOutstandingDays;
    }

    public void setInvoiceOutstandingDays(String invoiceOutstandingDays) {
        this.invoiceOutstandingDays = invoiceOutstandingDays;
    }

    public String getPendingInvoiceAmount() {
        return pendingInvoiceAmount;
    }

    public void setPendingInvoiceAmount(String pendingInvoiceAmount) {
        this.pendingInvoiceAmount = pendingInvoiceAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getNeftChequeNo() {
        return neftChequeNo;
    }

    public void setNeftChequeNo(String neftChequeNo) {
        this.neftChequeNo = neftChequeNo;
    }

    public String getNeftChequeMaturityDate() {
        return neftChequeMaturityDate;
    }

    public void setNeftChequeMaturityDate(String neftChequeMaturityDate) {
        this.neftChequeMaturityDate = neftChequeMaturityDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChequeImage() {
        return chequeImage;
    }

    public void setChequeImage(String chequeImage) {
        this.chequeImage = chequeImage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getPendingDays() {
        return pendingDays;
    }

    public void setPendingDays(String pendingDays) {
        this.pendingDays = pendingDays;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView tImageView, String tImgUrl){
        Glide.with(tImageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(tImgUrl)
                .placeholder(R.drawable.main_logo)
                .into(tImageView);
    }

}
