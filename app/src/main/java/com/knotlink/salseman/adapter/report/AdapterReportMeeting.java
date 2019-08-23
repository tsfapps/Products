package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.ReportMapActivity;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportMeeting extends RecyclerView.Adapter<AdapterReportMeeting.DistanceViewHolder> {

    private List<ModelReportMeeting> tModels;
    private Context tContext;

    public AdapterReportMeeting(List<ModelReportMeeting> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_meeting_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportMeeting tModel = tModels.get(i);
        String strAssDate = DateUtils.convertYyyyToDd(tModel.getTaskAssignDate());
        String strDueDate = DateUtils.convertYyyyToDd(tModel.getTaskDueDate());

        final String strLoginLat = tModel.getLatitude();
        final String strLoginLong = tModel.getLongitude();
        final String strLogoutLat = tModel.getLatitude();
        final String strLogoutLong = tModel.getLongitude();
        final String strEndAddress = "Customer Address : "+tModel.getCustomerAddress();
        final String strStartAddress = "Customer Address : "+tModel.getCustomerAddress();

        distanceViewHolder.tvReportMeetingDate.setText(strAssDate);
        distanceViewHolder.tvReportMeetingVendorType.setText(tModel.getVendorType());
        distanceViewHolder.tvReportMeetingVendorName.setText(tModel.getOrgName());
        distanceViewHolder.tvReportMeetingContactName.setText(tModel.getCustomerName());
        distanceViewHolder.tvReportMeetingContactNumber.setText(tModel.getCustomerContactNo());
        distanceViewHolder.tvReportMeetingEmailId.setText(tModel.getEmail());
        distanceViewHolder.tvReportMeetingTelephone.setText(tModel.getLandlineNo());
        distanceViewHolder.tvReportMeetingWhatsApp.setText(tModel.getWhatsappNo());
        distanceViewHolder.tvReportMeetingAddress.setText(tModel.getCustomerAddress());
        distanceViewHolder.tvReportMeetingMeetingStatus.setText(tModel.getStatus());
        distanceViewHolder.getTvReportMeetingMeetingDate.setText(strDueDate);
        distanceViewHolder.getTvReportMeetingMeetingTime.setText(tModel.getTaskTime());
        distanceViewHolder.tvReportMeetingRemarks.setText(tModel.getRemarks());
        distanceViewHolder.tvReportMeetingMap.setOnClickListener(new View.OnClickListener() {
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

        @BindView(R.id.tv_report_meeting_date)
        protected TextView tvReportMeetingDate;
        @BindView(R.id.tv_report_meeting_vendorType)
        protected TextView tvReportMeetingVendorType;
        @BindView(R.id.tv_report_meeting_vendorName)
        protected TextView tvReportMeetingVendorName;
        @BindView(R.id.tv_report_meeting_contactName)
        protected TextView tvReportMeetingContactName;
        @BindView(R.id.tv_report_meeting_contactNumber)
        protected TextView tvReportMeetingContactNumber;
        @BindView(R.id.tv_report_meeting_emailId)
        protected TextView tvReportMeetingEmailId;
        @BindView(R.id.tv_report_meeting_telephone)
        protected TextView tvReportMeetingTelephone;
        @BindView(R.id.tv_report_meeting_whatsAppNumber)
        protected TextView tvReportMeetingWhatsApp;
        @BindView(R.id.tv_report_meeting_address)
        protected TextView tvReportMeetingAddress;
        @BindView(R.id.tv_report_meeting_meetingStatus)
        protected TextView tvReportMeetingMeetingStatus;
        @BindView(R.id.tv_report_meeting_meetingTime)
        protected TextView getTvReportMeetingMeetingTime;
        @BindView(R.id.tv_report_meeting_meetingDate)
        protected TextView getTvReportMeetingMeetingDate;
        @BindView(R.id.tv_report_meeting_remarks)
        protected TextView tvReportMeetingRemarks;
        @BindView(R.id.tvReportMeetingMap)
        protected TextView tvReportMeetingMap;


        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
