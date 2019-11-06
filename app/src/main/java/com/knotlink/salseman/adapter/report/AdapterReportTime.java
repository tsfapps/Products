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
import com.knotlink.salseman.activity.maps.ReportTimeMap;
import com.knotlink.salseman.databinding.FragTimeReportItemBinding;
import com.knotlink.salseman.model.report.ModelTimeReport;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class AdapterReportTime extends RecyclerView.Adapter<AdapterReportTime.TimeReportViewHolder> {

    private List<ModelTimeReport> tModels;
    private Context tContext;

    @NonNull
    @Override
    public TimeReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragTimeReportItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_time_report_item, viewGroup, false);

        tContext=viewGroup.getContext();
        return new TimeReportViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeReportViewHolder timeReportViewHolder, int i) {
        final ModelTimeReport tModel = tModels.get(i);

        timeReportViewHolder.tBinding.setReportTime(tModel);
        timeReportViewHolder.tvMapTimeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportTimeMap.class);
                tIntent.putExtra(Constant.MODEL_INTENT, tModel);
                tContext.startActivity(tIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if (tModels != null) {
            return tModels.size();
        }else {
            return 0;
        }
    }

    public void settModels(List<ModelTimeReport> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class TimeReportViewHolder extends RecyclerView.ViewHolder{

        private FragTimeReportItemBinding tBinding;
        private TextView tvMapTimeReport;
        public TimeReportViewHolder(FragTimeReportItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvMapTimeReport = tBinding.tvMapTimeReport;
        }
    }
}
