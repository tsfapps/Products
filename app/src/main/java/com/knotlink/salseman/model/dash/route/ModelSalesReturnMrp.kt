package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.knotlink.salseman.utils.DateUtils


class ModelSalesReturnMrp {

    @SerializedName("mrp")
    @Expose
    private var mrp: String? = null

    fun getMrp(): String? {
        return mrp
    }

    fun setMrp(mrp: String) {
        this.mrp = mrp
    }

}