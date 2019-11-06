package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.knotlink.salseman.utils.DateUtils


class ModelSalesReturnSize {

    @SerializedName("size")
    @Expose
    private var size: String? = null
    @SerializedName("mrp")
    @Expose
    private var mrp: String? = null

    fun getSize(): String? {
        return size
    }

    fun setSize(size: String) {
        this.size = size
    }

    fun getMrp(): String? {
        return mrp
    }

    fun setMrp(mrp: String) {
        this.mrp = mrp
    }

}