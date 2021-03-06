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
import com.knotlink.salseman.model.report.ModelReportDistance;
import com.knotlink.salseman.utils.Constant;
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

        final String strLoginLat = tModel.getStartLat();
        final String strLoginLong = tModel.getStartLong();
        final String strLogoutLat = tModel.getEndLat();
        final String strLogoutLong = tModel.getEndLong();
        final String strEndAddress = "Ending Address : "+tModel.getEndingAddress();
        final String strStartAddress = "Starting Address : "+tModel.getStartingAddress();

        distanceViewHolder.tvReportDistanceDate.setText(tModel.getDate());
        distanceViewHolder.tvReportDistanceStartKm.setText(tModel.getStartingKm());
        distanceViewHolder.tvReportDistanceEndKm.setText(tModel.getEndingKm());
        distanceViewHolder.tvReportDistanceDisTravelled.setText(tModel.getDistanceTraveled());
        distanceViewHolder.tvReportDistanceTotalDisTravelled.setText(tModel.getTotalKm());
        distanceViewHolder.tvReportDistanceMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, strLoginLat);
                tIntent.putExtra(Constant.FIRST_LONG, strLoginLong);
                tIntent.putExtra(Constant.SECOND_LAT, strLogoutLat);
                tIntent.putExtra(Constant.SECOND_LONG, strLogoutLong);
                tIntent.putExtra(Constant.START_ADDRESS, strStartAddress);
                tIntent.putExtra(Constant.END_ADDRESS, strEndAddress);
                tContext.startActivity(tIntent);
            }
        });
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
        @BindView(R.id.tvReportDistanceMap)
        protected TextView tvReportDistanceMap;

        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
