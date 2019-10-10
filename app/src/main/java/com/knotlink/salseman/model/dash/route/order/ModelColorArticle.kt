package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelColorArticle {
    @SerializedName("color")
    @Expose
    private var color: String? = null

    fun getColor(): String? {
        return color
    }

    fun setColor(color: String) {
        this.color = color
    }
}