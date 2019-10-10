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
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportAttendance extends RecyclerView.Adapter<AdapterReportAttendance.DistanceViewHolder> {

    private List<ModelReportAttendance> tModels;
    private Context tContext;

    public AdapterReportAttendance(List<ModelReportAttendance> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_attendance_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportAttendance tModel = tModels.get(i);
        String strDate = DateUtils.convertYyyyToDd(tModel.getLoginDate());

        final String strLoginLat = tModel.getLoginLatitude();
        final String strLoginLong = tModel.getLoginLongitude();
        final String strLogoutLat = tModel.getLogoutLatitude();
        final String strLogoutLong = tModel.getLogoutLongitude();
        final String strLoginAddress = "Login Address : "+tModel.getLoginAddress();
        final String strLogoutAddress = "Logout Address"+tModel.getLogoutAddress();

        distanceViewHolder.tvReportAttendanceDate.setText(tModel.getLoginDate());
        distanceViewHolder.tvReportAttendanceLoginTime.setText(tModel.getLoginTime());
        distanceViewHolder.tvReportAttendanceLogoutTime.setText(tModel.getLogoutTime());
        distanceViewHolder.tvReportAttendanceWorkingHour.setText(tModel.getTotalTimeDuration());
        distanceViewHolder.tvReportAttendanceMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, strLoginLat);
                tIntent.putExtra(Constant.FIRST_LONG, strLoginLong);
                tIntent.putExtra(Constant.SECOND_LAT, strLogoutLat);
                tIntent.putExtra(Constant.SECOND_LONG, strLogoutLong);
                tIntent.putExtra(Constant.START_ADDRESS, strLoginAddress);
                tIntent.putExtra(Constant.END_ADDRESS, strLogoutAddress);
                tContext.startActivity(tIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_report_attendance_date)
        protected TextView tvReportAttendanceDate;
        @BindView(R.id.tv_report_attendance_loginTime)
        protected TextView tvReportAttendanceLoginTime;
        @BindView(R.id.tv_report_attendance_logoutTime)
        protected TextView tvReportAttendanceLogoutTime;
        @BindView(R.id.tv_report_attendance_workingHour)
        protected TextView tvReportAttendanceWorkingHour;
        @BindView(R.id.tvReportAttendanceMap)
        protected TextView tvReportAttendanceMap;
        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
