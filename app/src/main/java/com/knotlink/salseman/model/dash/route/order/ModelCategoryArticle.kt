package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelCategoryArticle {
    @SerializedName("category")
    @Expose
    private var category: String? = null

    fun getCategory(): String? {
        return category
    }

    fun setCategory(category: String) {
        this.category = category
    }
}