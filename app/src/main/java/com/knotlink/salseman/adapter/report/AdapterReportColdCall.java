package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportColdCall extends RecyclerView.Adapter<AdapterReportColdCall.DistanceViewHolder> {

    private List<ModelReportColdCall> tModels;
    private Context tContext;

    public AdapterReportColdCall(List<ModelReportColdCall> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_cold_call_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportColdCall tModel = tModels.get(i);
        String strAssDate = DateUtils.convertYyyyToDd(tModel.getTaskAssignDate());
        String strDueDate = DateUtils.convertYyyyToDd(tModel.getTaskDueDate());
        distanceViewHolder.tvReportColdCallDate.setText(strAssDate);
        distanceViewHolder.tvReportColdCallVendorName.setText(tModel.getOrgName());
        distanceViewHolder.tvReportColdCallContactName.setText(tModel.getCustomerName());
        distanceViewHolder.tvReportColdCallContactNumber.setText(tModel.getCustomerContactNo());
        distanceViewHolder.tvReportColdCallEmailId.setText(tModel.getEmail());
        distanceViewHolder.tvReportColdCallTelephone.setText(tModel.getLandlineNo());
//        distanceViewHolder.tvReportColdCallWhatsApp.setText(tModel.getWhatsappNo());
        distanceViewHolder.tvReportColdCallAddress.setText(tModel.getCustomerAddress());
        distanceViewHolder.tvReportColdCallStatus.setText(tModel.getStatus());
        distanceViewHolder.tvReportColdCallRemarks.setText(tModel.getRemarks());

    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }
    public class DistanceViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_report_coldCall_date)
        protected TextView tvReportColdCallDate;
        @BindView(R.id.tv_report_coldCall_vendorName)
        protected TextView tvReportColdCallVendorName;
        @BindView(R.id.tv_report_coldCall_contactName)
        protected TextView tvReportColdCallContactName;
        @BindView(R.id.tv_report_coldCall_contactNumber)
        protected TextView tvReportColdCallContactNumber;
        @BindView(R.id.tv_report_coldCall_emailId)
        protected TextView tvReportColdCallEmailId;
        @BindView(R.id.tv_report_coldCall_telephone)
        protected TextView tvReportColdCallTelephone;
//        @BindView(R.id.tv_report_coldCall_whatsAppNumber)
//        protected TextView tvReportColdCallWhatsApp;
        @BindView(R.id.tv_report_coldCall_address)
        protected TextView tvReportColdCallAddress;
        @BindView(R.id.tv_report_coldCall_status)
        protected TextView tvReportColdCallStatus;
        @BindView(R.id.tv_report_coldCall_remarks)
        protected TextView tvReportColdCallRemarks;
        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
