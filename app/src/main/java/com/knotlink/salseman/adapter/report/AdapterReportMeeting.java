package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportMeeting;

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
        distanceViewHolder.tvReportMeetingDate.setText(tModel.getDate());
        distanceViewHolder.tvReportMeetingVendorType.setText(tModel.getVendorType());
        distanceViewHolder.tvReportMeetingVendorName.setText(tModel.getOrgName());
        distanceViewHolder.tvReportMeetingContactName.setText(tModel.getContactName());
        distanceViewHolder.tvReportMeetingContactNumber.setText(tModel.getContactNo());
        distanceViewHolder.tvReportMeetingEmailId.setText(tModel.getEmail());
        distanceViewHolder.tvReportMeetingTelephone.setText(tModel.getContactNo());
        distanceViewHolder.tvReportMeetingWhatsApp.setText(tModel.getWhatsappNo());
        distanceViewHolder.tvReportMeetingAddress.setText(tModel.getAddress());
        distanceViewHolder.tvReportMeetingMeetingStatus.setText(tModel.getStatus());
        distanceViewHolder.getTvReportMeetingMeetingTime.setText(tModel.getMeetingTime());
        distanceViewHolder.tvReportMeetingRemarks.setText(tModel.getRemarks());
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
        @BindView(R.id.tv_report_meeting_remarks)
        protected TextView tvReportMeetingRemarks;


        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
