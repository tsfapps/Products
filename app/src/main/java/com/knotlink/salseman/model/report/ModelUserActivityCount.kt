package com.knotlink.salseman.model.report

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ModelUserActivityCount {
    @SerializedName("new_order_count")
    @Expose
    private var newOrderCount: String? = null
    @SerializedName("attendance_count")
    @Expose
    private var attendanceCount: String? = null
    @SerializedName("distance_upload_count")
    @Expose
    private var distanceUploadCount: String? = null
    @SerializedName("meeting_count")
    @Expose
    private var meetingCount: String? = null
    @SerializedName("lead_generation_count")
    @Expose
    private var leadGenerationCount: String? = null
    @SerializedName("special_request_count")
    @Expose
    private var specialRequestCount: String? = null
    @SerializedName("receipt_count")
    @Expose
    private var receiptCount: String? = null
    @SerializedName("invoice_count")
    @Expose
    private var invoiceCount: String? = null
    @SerializedName("sales_return_count")
    @Expose
    private var salesReturnCount: String? = null
    @SerializedName("complain_count")
    @Expose
    private var complainCount: String? = null
    @SerializedName("no_visit_count")
    @Expose
    private var noVisitCount: String? = null
    @SerializedName("no_activity_count")
    @Expose
    private var noActivityCount: String? = null
    @SerializedName("expense_count")
    @Expose
    private var expenseCount: String? = null
    @SerializedName("cold_call_count")
    @Expose
    private var coldCallCount: String? = null
    @SerializedName("meeting_count_prospect")
    @Expose
    private var meetingCountProspect: String? = null
    @SerializedName("lead_generation_count_prospect")
    @Expose
    private var leadGenerationCountProspect: String? = null
    @SerializedName("expense_sum")
    @Expose
    private var expenseSum: String? = null
    @SerializedName("cash_collection_sum")
    @Expose
    private var cashCollectionSum: String? = null
    @SerializedName("route_count")
    @Expose
    private var routeCount: String? = null

    fun getNewOrderCount(): String? {
        return newOrderCount
    }

    fun setNewOrderCount(newOrderCount: String) {
        this.newOrderCount = newOrderCount
    }

    fun getAttendanceCount(): String? {
        return attendanceCount
    }

    fun setAttendanceCount(attendanceCount: String) {
        this.attendanceCount = attendanceCount
    }

    fun getDistanceUploadCount(): String? {
        return distanceUploadCount
    }

    fun setDistanceUploadCount(distanceUploadCount: String) {
        this.distanceUploadCount = distanceUploadCount
    }

    fun getMeetingCount(): String? {
        return meetingCount
    }

    fun setMeetingCount(meetingCount: String) {
        this.meetingCount = meetingCount
    }

    fun getLeadGenerationCount(): String? {
        return leadGenerationCount
    }

    fun setLeadGenerationCount(leadGenerationCount: String) {
        this.leadGenerationCount = leadGenerationCount
    }

    fun getSpecialRequestCount(): String? {
        return specialRequestCount
    }

    fun setSpecialRequestCount(specialRequestCount: String) {
        this.specialRequestCount = specialRequestCount
    }

    fun getReceiptCount(): String? {
        return receiptCount
    }

    fun setReceiptCount(receiptCount: String) {
        this.receiptCount = receiptCount
    }

    fun getInvoiceCount(): String? {
        return invoiceCount
    }

    fun setInvoiceCount(invoiceCount: String) {
        this.invoiceCount = invoiceCount
    }

    fun getSalesReturnCount(): String? {
        return salesReturnCount
    }

    fun setSalesReturnCount(salesReturnCount: String) {
        this.salesReturnCount = salesReturnCount
    }

    fun getComplainCount(): String? {
        return complainCount
    }

    fun setComplainCount(complainCount: String) {
        this.complainCount = complainCount
    }

    fun getNoVisitCount(): String? {
        return noVisitCount
    }

    fun setNoVisitCount(noVisitCount: String) {
        this.noVisitCount = noVisitCount
    }

    fun getNoActivityCount(): String? {
        return noActivityCount
    }

    fun setNoActivityCount(noActivityCount: String) {
        this.noActivityCount = noActivityCount
    }

    fun getExpenseCount(): String? {
        return expenseCount
    }

    fun setExpenseCount(expenseCount: String) {
        this.expenseCount = expenseCount
    }

    fun getColdCallCount(): String? {
        return coldCallCount
    }

    fun setColdCallCount(coldCallCount: String) {
        this.coldCallCount = coldCallCount
    }

    fun getMeetingCountProspect(): String? {
        return meetingCountProspect
    }

    fun setMeetingCountProspect(meetingCountProspect: String) {
        this.meetingCountProspect = meetingCountProspect
    }

    fun getLeadGenerationCountProspect(): String? {
        return leadGenerationCountProspect
    }

    fun setLeadGenerationCountProspect(leadGenerationCountProspect: String) {
        this.leadGenerationCountProspect = leadGenerationCountProspect
    }

    fun getExpenseSum(): String? {
        return "Expenses : ₹$expenseSum"
    }

    fun setExpenseSum(expenseSum: String) {
        this.expenseSum = expenseSum
    }

    fun getCashCollectionSum(): String? {
        return "Cash Collected : ₹$cashCollectionSum"
    }

    fun setCashCollectionSum(cashCollectionSum: String) {
        this.cashCollectionSum = cashCollectionSum
    }

    fun getRouteCount(): String? {
        return routeCount
    }

    fun setRouteCount(routeCount: String) {
        this.routeCount = routeCount
    }

}