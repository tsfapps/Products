package com.knotlink.salseman.model.dash.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelShopList {



    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("lat_long_address")
    @Expose
    private String latLongAddress;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("visit_status")
    @Expose
    private String visitStatus;
    @SerializedName("new_order_status")
    @Expose
    private String newOrderStatus;
    @SerializedName("receipt_status")
    @Expose
    private String receiptStatus;
    @SerializedName("special_request_status")
    @Expose
    private String specialRequestStatus;
    @SerializedName("complain_status")
    @Expose
    private String complainStatus;
    @SerializedName("no_activity_status")
    @Expose
    private String noActivityStatus;
    @SerializedName("dispatcher_id")
    @Expose
    private String dispatcherId;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("route_name")
    @Expose
    private String routeName;
    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("landline_no")
    @Expose
    private String landlineNo;
    @SerializedName("whatsapp_no")
    @Expose
    private String whatsappNo;
    @SerializedName("counter")
    @Expose
    private String counter;
    @SerializedName("invoice_no")
    @Expose
    private List<String> invoiceNo = null;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLatLongAddress() {
        return latLongAddress;
    }

    public void setLatLongAddress(String latLongAddress) {
        this.latLongAddress = latLongAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(String newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    public String getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getSpecialRequestStatus() {
        return specialRequestStatus;
    }

    public void setSpecialRequestStatus(String specialRequestStatus) {
        this.specialRequestStatus = specialRequestStatus;
    }

    public String getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(String complainStatus) {
        this.complainStatus = complainStatus;
    }

    public String getNoActivityStatus() {
        return noActivityStatus;
    }

    public void setNoActivityStatus(String noActivityStatus) {
        this.noActivityStatus = noActivityStatus;
    }

    public String getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(String dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public List<String> getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(List<String> invoiceNo) {
        this.invoiceNo = invoiceNo;
    }


}
