package com.knotlink.salseman.model.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportReceipt {
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
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("total_outstanding_amount")
    @Expose
    private String totalOutstandingAmount;
    @SerializedName("amount_recived")
    @Expose
    private String amountRecived;
    @SerializedName("pending_outstanding_amount")
    @Expose
    private String pendingOutstandingAmount;
    @SerializedName("credit_days")
    @Expose
    private String creditDays;
    @SerializedName("credit_limit")
    @Expose
    private String creditLimit;
    @SerializedName("cheque_no")
    @Expose
    private String chequeNo;
    @SerializedName("cheque_date")
    @Expose
    private String chequeDate;
    @SerializedName("cheque_image")
    @Expose
    private String chequeImage;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("remarks")
    @Expose
    private String remarks;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(String totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    public String getAmountRecived() {
        return amountRecived;
    }

    public void setAmountRecived(String amountRecived) {
        this.amountRecived = amountRecived;
    }

    public String getPendingOutstandingAmount() {
        return pendingOutstandingAmount;
    }

    public void setPendingOutstandingAmount(String pendingOutstandingAmount) {
        this.pendingOutstandingAmount = pendingOutstandingAmount;
    }

    public String getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(String creditDays) {
        this.creditDays = creditDays;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
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
}
