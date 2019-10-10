package com.knotlink.salseman.model.dash.route.order

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelBrand {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("brand_name")
    @Expose
    private var brandName: String? = null
    @SerializedName("datetime")
    @Expose
    private var datetime: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getBranchId(): String? {
        return branchId
    }

    fun setBranchId(branchId: String) {
        this.branchId = branchId
    }

    fun getBrandName(): String? {
        return brandName
    }

    fun setBrandName(brandName: String) {
        this.brandName = brandName
    }

    fun getDatetime(): String? {
        return datetime
    }

    fun setDatetime(datetime: String) {
        this.datetime = datetime
    }
}