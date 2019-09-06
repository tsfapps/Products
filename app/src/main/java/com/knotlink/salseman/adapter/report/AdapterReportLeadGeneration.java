package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.ReportMapActivity;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportLeadGeneration extends RecyclerView.Adapter<AdapterReportLeadGeneration.DistanceViewHolder> {

    private List<ModelReportLeadGeneration> tModels;
    private Context tContext;

    public AdapterReportLeadGeneration(List<ModelReportLeadGeneration> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_lead_generation_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportLeadGeneration tModel = tModels.get(i);
        String strAssDate = DateUtils.convertYyyyToDd(tModel.getTaskAssignDate());
        String strDueDate = DateUtils.convertYyyyToDd(tModel.getTaskDueDate());

        final String strLoginLat = tModel.getLatitude();
        final String strLoginLong = tModel.getLongitude();
        final String strLogoutLat = tModel.getLatitude();
        final String strLogoutLong = tModel.getLongitude();
        final String strEndAddress = "Customer Address : "+tModel.getCustomerAddress();
        final String strStartAddress = "Customer Address : "+tModel.getCustomerAddress();


        distanceViewHolder.tvReportLeadDate.setText(strAssDate);
        distanceViewHolder.tvReportLeadVendorType.setText(tModel.getVendorType());
        distanceViewHolder.tvReportLeadVendorName.setText(tModel.getOrgName());
        distanceViewHolder.tvReportLeadContactName.setText(tModel.getCustomerName());
        distanceViewHolder.getTvReportLeadContactNumber.setText(tModel.getCustomerContactNo());
        distanceViewHolder.tvReportLeadEmailId.setText(tModel.getEmail());
        distanceViewHolder.tvReportLeadWhatsApp.setText(tModel.getWhatsappNo());
        distanceViewHolder.tvReportLeadAddress.setText(tModel.getCustomerAddress());
//        distanceViewHolder.tvReportLeadCity.setText(tModel.getCity());
        distanceViewHolder.tvReportLeadTelephone.setText(tModel.getLandlineNo());
        distanceViewHolder.tvReportLeadStatus.setText(tModel.getStatus());
        distanceViewHolder.tvReportLeadNextMeetingDate.setText(strDueDate);
        distanceViewHolder.tvReportLeadRemarks.setText(tModel.getRemarks());
        distanceViewHolder.tvReportLeadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, strLoginLat);
                tIntent.putExtra(Constant.FIRST_LONG, strLoginLong);
                tIntent.putExtra(Constant.SECOND_LAT, strLogoutLat);
                tIntent.putExtra(Constant.SECOND_LONG, strLogoutLong);
                tIntent.putExtra(Constant.START_ADDRESS, strStartAddress);
                tIntent.putExtra(Constant.END_ADDRESS, strEndAddress);
                tContext.startActivity(tIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_report_lead_date)
        protected TextView tvReportLeadDate;
        @BindView(R.id.tv_report_lead_vendorType)
        protected TextView tvReportLeadVendorType;
        @BindView(R.id.tv_report_lead_vendorName)
        protected TextView tvReportLeadVendorName;
        @BindView(R.id.tv_report_lead_contactName)
        protected TextView tvReportLeadContactName;
        @BindView(R.id.tv_report_lead_contactNumber)
        protected TextView getTvReportLeadContactNumber;
        @BindView(R.id.tv_report_lead_emailId)
        protected TextView tvReportLeadEmailId;
        @BindView(R.id.tv_report_lead_whatsAppNumber)
        protected TextView tvReportLeadWhatsApp;
        @BindView(R.id.tv_report_lead_telephone)
        protected TextView tvReportLeadTelephone;
        @BindView(R.id.tv_report_lead_address)
        protected TextView tvReportLeadAddress;
//        @BindView(R.id.tv_report_lead_city)
//        protected TextView tvReportLeadCity;
        @BindView(R.id.tv_report_lead_status)
        protected TextView tvReportLeadStatus;
        @BindView(R.id.tv_report_lead_nextMeetingDate)
        protected TextView tvReportLeadNextMeetingDate;
        @BindView(R.id.tv_report_lead_remarks)
        protected TextView tvReportLeadRemarks;
        @BindView(R.id.tvReportLeadMap)
        protected TextView tvReportLeadMap;


        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
