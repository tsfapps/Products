package com.knotlink.salseman.model.dash.route

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelShopCounter {
    @SerializedName("visit_count")
    @Expose
    private var visitCount: String? = null
    @SerializedName("no_visit_count")
    @Expose
    private var noVisitCount: String? = null
    @SerializedName("remain_count")
    @Expose
    private var remainCount: String? = null
    @SerializedName("total_count")
    @Expose
    private var totalCount: String? = null

    fun getVisitCount(): String? {
        return "Visited : $visitCount"
    }

    fun setVisitCount(visitCount: String) {
        this.visitCount = visitCount
    }

    fun getNoVisitCount(): String? {
        return "Not Visited : $noVisitCount"
    }

    fun setNoVisitCount(noVisitCount: String) {
        this.noVisitCount = noVisitCount
    }

    fun getRemainCount(): String? {
        return "Remain : $remainCount"
    }

    fun setRemainCount(remainCount: String) {
        this.remainCount = remainCount
    }

    fun getTotalCount(): String? {
        return "Total Shop : $totalCount"
    }

    fun setTotalCount(totalCount: String) {
        this.totalCount = totalCount
    }


}