package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelPriceArticle {
    @SerializedName("unit_price")
    @Expose
    private var unitPrice: String? = null

    fun getUnitPrice(): String? {
        return unitPrice
    }

    fun setUnitPrice(unitPrice: String) {
        this.unitPrice = unitPrice
    }
}