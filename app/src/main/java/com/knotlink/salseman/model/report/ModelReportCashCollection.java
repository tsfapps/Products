package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportCashCollection {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("cash_2000")
    @Expose
    private String cash2000;
    @SerializedName("cash_500")
    @Expose
    private String cash500;
    @SerializedName("cash_200")
    @Expose
    private String cash200;
    @SerializedName("cash_100")
    @Expose
    private String cash100;
    @SerializedName("cash_50")
    @Expose
    private String cash50;
    @SerializedName("cash_20")
    @Expose
    private String cash20;
    @SerializedName("cash_10")
    @Expose
    private String cash10;
    @SerializedName("cash_5")
    @Expose
    private String cash5;
    @SerializedName("cash_2")
    @Expose
    private String cash2;
    @SerializedName("cash_1")
    @Expose
    private String cash1;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("pin_code")
    @Expose
    private Object pinCode;
    @SerializedName("no_cheque")
    @Expose
    private String noCheque;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remarks")
    @Expose
    private Object remarks;

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCash2000() {
        return cash2000;
    }

    public void setCash2000(String cash2000) {
        this.cash2000 = cash2000;
    }

    public String getCash500() {
        return cash500;
    }

    public void setCash500(String cash500) {
        this.cash500 = cash500;
    }

    public String getCash200() {
        return cash200;
    }

    public void setCash200(String cash200) {
        this.cash200 = cash200;
    }

    public String getCash100() {
        return cash100;
    }

    public void setCash100(String cash100) {
        this.cash100 = cash100;
    }

    public String getCash50() {
        return cash50;
    }

    public void setCash50(String cash50) {
        this.cash50 = cash50;
    }

    public String getCash20() {
        return cash20;
    }

    public void setCash20(String cash20) {
        this.cash20 = cash20;
    }

    public String getCash10() {
        return cash10;
    }

    public void setCash10(String cash10) {
        this.cash10 = cash10;
    }

    public String getCash5() {
        return cash5;
    }

    public void setCash5(String cash5) {
        this.cash5 = cash5;
    }

    public String getCash2() {
        return cash2;
    }

    public void setCash2(String cash2) {
        this.cash2 = cash2;
    }

    public String getCash1() {
        return cash1;
    }

    public void setCash1(String cash1) {
        this.cash1 = cash1;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPinCode() {
        return pinCode;
    }

    public void setPinCode(Object pinCode) {
        this.pinCode = pinCode;
    }

    public String getNoCheque() {
        return noCheque;
    }

    public void setNoCheque(String noCheque) {
        this.noCheque = noCheque;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

}
