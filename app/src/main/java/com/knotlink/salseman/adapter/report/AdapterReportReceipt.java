package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportReceipt;

import java.util.List;

import butterknife.ButterKnife;

public class AdapterReportReceipt extends RecyclerView.Adapter<AdapterReportReceipt.DistanceViewHolder> {

    private List<ModelReportReceipt> tModels;
    private Context tContext;

    public AdapterReportReceipt(List<ModelReportReceipt> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_receipt_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportReceipt tModel = tModels.get(i);
//        distanceViewHolder.tvReportDistanceDate.setText(tModel.getDate());
//        distanceViewHolder.tvReportDistanceStartKm.setText(tModel.getStartingKm());
//        distanceViewHolder.tvReportDistanceEndKm.setText(tModel.getEndingKm());
//        distanceViewHolder.tvReportDistanceDisTravelled.setText(tModel.getDistanceTraveled());
//        distanceViewHolder.tvReportDistanceTotalDisTravelled.setText(tModel.getTotalKm());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.tv_report_distance_date)
//        protected TextView tvReportDistanceDate;
//        @BindView(R.id.tv_report_distance_startKm)
//        protected TextView tvReportDistanceStartKm;
//        @BindView(R.id.tv_report_distance_endKm)
//        protected TextView tvReportDistanceEndKm;
//        @BindView(R.id.tv_report_distance_disTravelled)
//        protected TextView tvReportDistanceDisTravelled;
//        @BindView(R.id.tv_report_distance_totalDisTravelled)
//        protected TextView tvReportDistanceTotalDisTravelled;

        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
