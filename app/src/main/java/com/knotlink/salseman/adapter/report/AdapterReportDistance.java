package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportDistance;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportDistance extends RecyclerView.Adapter<AdapterReportDistance.DistanceViewHolder> {

    private List<ModelReportDistance> tModels;
    private Context tContext;

    public AdapterReportDistance(List<ModelReportDistance> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_distance_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportDistance tModel = tModels.get(i);
        String strDate = DateUtils.convertYyyyToDd(tModel.getDate());
        distanceViewHolder.tvReportDistanceDate.setText(strDate);
        distanceViewHolder.tvReportDistanceStartKm.setText(tModel.getStartingKm());
        distanceViewHolder.tvReportDistanceEndKm.setText(tModel.getEndingKm());
        distanceViewHolder.tvReportDistanceDisTravelled.setText(tModel.getDistanceTraveled());
        distanceViewHolder.tvReportDistanceTotalDisTravelled.setText(tModel.getTotalKm());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_report_distance_date)
        protected TextView tvReportDistanceDate;
        @BindView(R.id.tv_report_distance_startKm)
        protected TextView tvReportDistanceStartKm;
        @BindView(R.id.tv_report_distance_endKm)
        protected TextView tvReportDistanceEndKm;
        @BindView(R.id.tv_report_distance_disTravelled)
        protected TextView tvReportDistanceDisTravelled;
        @BindView(R.id.tv_report_distance_totalDisTravelled)
        protected TextView tvReportDistanceTotalDisTravelled;

        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
