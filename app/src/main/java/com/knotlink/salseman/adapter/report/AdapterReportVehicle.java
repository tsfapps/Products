package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportVehicle;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportVehicle extends RecyclerView.Adapter<AdapterReportVehicle.DistanceViewHolder> {

    private List<ModelReportVehicle> tModels;
    private Context tContext;

    public AdapterReportVehicle(List<ModelReportVehicle> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_vehicle_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportVehicle tModel = tModels.get(i);
        String strDate = DateUtils.convertYyyyToDd(tModel.getDate());
        distanceViewHolder.tv_reportVehicleDate.setText(strDate);
        distanceViewHolder.tv_reportVehicleNumber.setText(tModel.getVehicleNo());
        distanceViewHolder.tv_reportVehicleExpense.setText(tModel.getVehicleExpenseType());
        distanceViewHolder.tv_reportVehicleAmount.setText(tModel.getAmount());
        distanceViewHolder.tv_reportVehicleRemarks.setText(tModel.getRemarks());
    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_reportVehicleDate)
        protected TextView tv_reportVehicleDate;
        @BindView(R.id.tv_reportVehicleNumber)
        protected TextView tv_reportVehicleNumber;
        @BindView(R.id.tv_reportVehicleExpense)
        protected TextView tv_reportVehicleExpense;
        @BindView(R.id.tv_reportVehicleAmount)
        protected TextView tv_reportVehicleAmount;
        @BindView(R.id.tv_reportVehicleRemarks)
        protected TextView tv_reportVehicleRemarks;
        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
