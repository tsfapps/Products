package com.knotlink.salseman.model.dash.route;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInvoice {


    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("invoice_amount")
    @Expose
    private String invoiceAmount;
    @SerializedName("remaining_days")
    @Expose
    private String remainingDays;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
