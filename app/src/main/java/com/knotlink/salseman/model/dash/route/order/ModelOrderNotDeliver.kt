package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelOrderNotDeliver {
    @SerializedName("error")
    @Expose
    private var error: Boolean? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null

    fun getError(): Boolean? {
        return error
    }

    fun setError(error: Boolean?) {
        this.error = error
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }
}