package com.knotlink.salseman.model.notice;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.knotlink.salseman.utils.DateUtils;

public class ModelNoticeReturn {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("salesman")
    @Expose
    private String salesman;
    @SerializedName("dispatcher")
    @Expose
    private String dispatcher;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("invoice_no")
    @Expose
    private String invoiceNo;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("article_no")
    @Expose
    private String articleNo;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceNo() {
        return "Invoice no : "+invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return DateUtils.convertYyyyToDd(invoiceDate);
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getArticleNo() {
        return "Article no : "+articleNo;
    }

    public void setArticleNo(String articleNo) {
        this.articleNo = articleNo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
