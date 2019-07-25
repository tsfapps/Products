package com.knotlink.salseman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelShopList {
// {
//        "dispatcher_id": "EMP_1033",
//        "pincode": null,
//        "latitude": "0",
//        "longitude": "0",
//        "route_name": "GANGANAGAR (8123010201)",
//        "gst_no": "29000000000",
//        "landline_no": null,
//        "whatsapp_no": null,
//        "invoice_no": [
//            ""
//        ]
//    },
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
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("visit_status")
    @Expose
    private String visitStatus;
    @SerializedName("not_visit_status")
    @Expose
    private String notVisitStatus;
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
    @SerializedName("total_outstanding_amount")
    @Expose
    private String totalOutstandingAmount;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getNotVisitStatus() {
        return notVisitStatus;
    }

    public void setNotVisitStatus(String notVisitStatus) {
        this.notVisitStatus = notVisitStatus;
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

    public String getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(String totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    public List<String> getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(List<String> invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

}
