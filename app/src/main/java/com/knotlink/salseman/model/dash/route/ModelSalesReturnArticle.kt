package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.knotlink.salseman.utils.DateUtils


class ModelSalesReturnArticle {

    @SerializedName("invoice_date")
    @Expose
    private var invoiceDate: String? = null
    @SerializedName("article_no")
    @Expose
    private var articleNo: List<String>? = null

    fun getInvoiceDate(): String? {
        return invoiceDate
    }

    fun setInvoiceDate(invoiceDate: String) {
        this.invoiceDate = invoiceDate
    }

    fun getArticleNo(): List<String>? {
        return articleNo
    }

    fun setArticleNo(articleNo: List<String>) {
        this.articleNo = articleNo
    }

}