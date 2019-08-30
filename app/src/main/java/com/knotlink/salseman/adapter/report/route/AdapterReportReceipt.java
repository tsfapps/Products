package com.knotlink.salseman.adapter.report.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportReceipt;
import com.knotlink.salseman.model.report.route.ModelRouteReceipt;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportReceipt extends RecyclerView.Adapter<AdapterReportReceipt.RouteViewHolder> {

    private List<ModelRouteReceipt> tModels;
    private Context tContext;

    public AdapterReportReceipt(List<ModelRouteReceipt> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_receipt_item, viewGroup, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder routeViewHolder, int i) {
        ModelRouteReceipt tModel = tModels.get(i);
//        routeViewHolder.tvReportDistanceDate.setText(tModel.getDate());
//        routeViewHolder.tvReportDistanceStartKm.setText(tModel.getStartingKm());
//        routeViewHolder.tvReportDistanceEndKm.setText(tModel.getEndingKm());
//        routeViewHolder.tvReportDistanceDisTravelled.setText(tModel.getDistanceTraveled());
//        routeViewHolder.tvReportDistanceTotalDisTravelled.setText(tModel.getTotalKm());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

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

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
