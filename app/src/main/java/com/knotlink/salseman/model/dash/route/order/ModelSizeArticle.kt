package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelSizeArticle {
    @SerializedName("size")
    @Expose
    private var size: String? = null

    fun getSize(): String? {
        return size
    }

    fun setSize(size: String) {
        this.size = size
    }
}