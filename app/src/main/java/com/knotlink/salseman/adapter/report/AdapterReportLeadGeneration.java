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
import com.knotlink.salseman.databinding.ReportLeadGenerationItemBinding;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class AdapterReportLeadGeneration extends RecyclerView.Adapter<AdapterReportLeadGeneration.LeadReportViewHolder> {

    private List<ModelReportLeadGeneration> tModels;
    private Context tContext;

    @NonNull
    @Override
    public LeadReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ReportLeadGenerationItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.report_lead_generation_item, viewGroup, false);

        tContext=viewGroup.getContext();
        return new LeadReportViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadReportViewHolder leadReportViewHolder, int i) {
        final ModelReportLeadGeneration tModel = tModels.get(i);

        leadReportViewHolder.tBinding.setLead(tModel);
        leadReportViewHolder.tvReportMeetingMap.setOnClickListener(new View.OnClickListener() {
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
    public int getItemCount()
    {
        if (tModels != null) {
            return tModels.size();
        }else {
            return 0;
        }
    }

    public void settModels(List<ModelReportLeadGeneration> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class LeadReportViewHolder extends RecyclerView.ViewHolder{

        private ReportLeadGenerationItemBinding tBinding;
        private TextView tvReportMeetingMap;
        public LeadReportViewHolder(ReportLeadGenerationItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvReportMeetingMap = tBinding.tvReportLeadMap;
        }
    }
}
