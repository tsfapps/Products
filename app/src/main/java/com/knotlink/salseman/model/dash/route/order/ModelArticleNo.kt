package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelArticleNo {
    @SerializedName("article_no")
    @Expose
    private var articleNo: String? = null

    fun getArticleNo(): String? {
        return articleNo
    }

    fun setArticleNo(articleNo: String) {
        this.articleNo = articleNo
    }
}