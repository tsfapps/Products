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
import com.knotlink.salseman.databinding.ReportAttendanceItemBinding;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class AdapterReportAtt extends RecyclerView.Adapter<AdapterReportAtt.AttReportViewHolder> {

    private List<ModelReportAttendance> tModels;
    private Context tContext;
    @NonNull
    @Override
    public AttReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ReportAttendanceItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.report_attendance_item, viewGroup, false);

        tContext = viewGroup.getContext();
        return new AttReportViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttReportViewHolder attReportViewHolder, int i) {

       final ModelReportAttendance tModel = tModels.get(i);
        attReportViewHolder.tBinding.setAttendance(tModel);

        attReportViewHolder.tvReportAttMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, tModel.getLoginLatitude());
                tIntent.putExtra(Constant.FIRST_LONG, tModel.getLoginLongitude());
                tIntent.putExtra(Constant.SECOND_LAT, tModel.getLogoutLatitude());
                tIntent.putExtra(Constant.SECOND_LONG, tModel.getLogoutLongitude());
                tIntent.putExtra(Constant.START_ADDRESS, tModel.getLoginAddress());
                tIntent.putExtra(Constant.END_ADDRESS, tModel.getLogoutAddress());
                tContext.startActivity(tIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tModels != null){
            return tModels.size();
        }else {
            return 0;
        }
    }
    public void settModels(List<ModelReportAttendance> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }


    public class AttReportViewHolder extends RecyclerView.ViewHolder {

        private ReportAttendanceItemBinding tBinding;
        private TextView tvReportAttMap;
        public AttReportViewHolder(ReportAttendanceItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvReportAttMap = tBinding.tvReportAttMap;

        }
    }
}
