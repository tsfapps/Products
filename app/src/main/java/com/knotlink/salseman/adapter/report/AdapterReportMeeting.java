package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.ReportMapActivity;
import com.knotlink.salseman.databinding.ReportMeetingItemBinding;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class AdapterReportMeeting extends RecyclerView.Adapter<AdapterReportMeeting.MeetingReportViewHolder> {

    private List<ModelReportMeeting> tModels;
    private Context tContext;

    @NonNull
    @Override
    public MeetingReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ReportMeetingItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.report_meeting_item, viewGroup, false);
       tContext = viewGroup.getContext();
        return new MeetingReportViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingReportViewHolder meetingReportViewHolder, int i) {
       final ModelReportMeeting tModel = tModels.get(i);
        meetingReportViewHolder.tBinding.setMeeting(tModel);
        meetingReportViewHolder.tvReportMeetingMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.FIRST_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.SECOND_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.SECOND_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.START_ADDRESS, tModel.getAddress());
                tIntent.putExtra(Constant.END_ADDRESS, tModel.getAddress());
                tContext.startActivity(tIntent);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (tModels != null) {
            return tModels.size();
        } else {
            return 0;
        }
    }
    public void settModels(List<ModelReportMeeting> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }
    public class MeetingReportViewHolder extends RecyclerView.ViewHolder{

        private ReportMeetingItemBinding tBinding;
        private TextView tvReportMeetingMap;

        public MeetingReportViewHolder(ReportMeetingItemBinding tBinding) {
            super(tBinding.getRoot());
           this.tBinding = tBinding;
           tvReportMeetingMap = tBinding.tvReportMeetingMap;
        }
    }
}
