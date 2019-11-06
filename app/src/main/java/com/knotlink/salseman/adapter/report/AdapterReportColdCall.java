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
import com.knotlink.salseman.databinding.ReportColdCallItemBinding;
import com.knotlink.salseman.databinding.ReportLeadGenerationItemBinding;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class AdapterReportColdCall extends RecyclerView.Adapter<AdapterReportColdCall.ColdReportViewHolder> {

    private List<ModelReportColdCall> tModels;
    private Context tContext;

    @NonNull
    @Override
    public ColdReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ReportColdCallItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.report_cold_call_item, viewGroup, false);
        tContext=viewGroup.getContext();
        return new ColdReportViewHolder(tBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ColdReportViewHolder coldReportViewHolder, int i) {
       final ModelReportColdCall tModel = tModels.get(i);
        coldReportViewHolder.tBinding.setColdCall(tModel);


        coldReportViewHolder.tvReportColdMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.FIRST_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.SECOND_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.SECOND_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.START_ADDRESS, tModel.getCustomerAddress());
                tIntent.putExtra(Constant.END_ADDRESS, tModel.getCustomerAddress());
                tContext.startActivity(tIntent);
            }
        });

    }
    @Override
    public int getItemCount() {
        if (tModels != null) {
            return tModels.size();
        }else {
            return 0;
        }
    }

    public void settModels(List<ModelReportColdCall> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }
    public class ColdReportViewHolder extends RecyclerView.ViewHolder{

        private ReportColdCallItemBinding tBinding;
        private TextView tvReportColdMap;
        public ColdReportViewHolder(ReportColdCallItemBinding tBinding) {
            super(tBinding.getRoot());
           this.tBinding = tBinding;
           tvReportColdMap = tBinding.tvReportColdMap;
        }
    }
}
