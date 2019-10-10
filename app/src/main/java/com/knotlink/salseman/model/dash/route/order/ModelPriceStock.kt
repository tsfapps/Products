package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelPriceStock {
    @SerializedName("unit_price")
    @Expose
    private var unitPrice: String? = null
    @SerializedName("in_stock")
    @Expose
    private var inStock: String? = null

    fun getUnitPrice(): String? {
        return unitPrice
    }

    fun setUnitPrice(unitPrice: String) {
        this.unitPrice = unitPrice
    }

    fun getInStock(): String? {
        return inStock
    }

    fun setInStock(inStock: String) {
        this.inStock = inStock
    }
}