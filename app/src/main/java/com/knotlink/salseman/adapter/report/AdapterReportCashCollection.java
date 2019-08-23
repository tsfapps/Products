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
import com.knotlink.salseman.activity.ReportMapActivity;
import com.knotlink.salseman.model.report.ModelReportCashCollection;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportCashCollection extends RecyclerView.Adapter<AdapterReportCashCollection.DistanceViewHolder> {

    private List<ModelReportCashCollection> tModels;
    private Context tContext;

    public AdapterReportCashCollection(List<ModelReportCashCollection> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_cash_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportCashCollection tModel = tModels.get(i);
        String strAssDate = tModel.getDatetime();

        final String strLoginLat = tModel.getLatitude();
        final String strLoginLong = tModel.getLongitude();
        final String strLogoutLat = tModel.getLatitude();
        final String strLogoutLong = tModel.getLongitude();
        final String strEndAddress = "Address : "+tModel.getAddress();
        final String strStartAddress = "Address : "+tModel.getAddress();

        distanceViewHolder.tvReportCashDate.setText(strAssDate);
        distanceViewHolder.tvReportCash2000.setText(tModel.getCash2000());
        distanceViewHolder.tvReportCash500.setText(tModel.getCash500());
        distanceViewHolder.tvReportCash200.setText(tModel.getCash200());
        distanceViewHolder.tvReportCash100.setText(tModel.getCash100());
        distanceViewHolder.tvReportCash50.setText(tModel.getCash50());
        distanceViewHolder.tvReportCash20.setText(tModel.getCash20());
        distanceViewHolder.tvReportCash10.setText(tModel.getCash10());
        distanceViewHolder.tvReportCash5.setText(tModel.getCash5());
        distanceViewHolder.tvReportCash2.setText(tModel.getCash2());
        distanceViewHolder.tvReportCash1.setText(tModel.getCash1());
        distanceViewHolder.tvReportCashTotalNumber.setText(tModel.getTotal());
        distanceViewHolder.tvReportCashTotalAmount.setText(tModel.getTotalAmount());
        distanceViewHolder.tvReportCashChequeNo.setText(tModel.getNoCheque());
        distanceViewHolder.tvReportCashMap.setOnClickListener(new View.OnClickListener() {
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

        @BindView(R.id.tvReportCashDate)
        protected TextView tvReportCashDate;
        @BindView(R.id.tvReportCash2000)
        protected TextView tvReportCash2000;
        @BindView(R.id.tvReportCash500)
        protected TextView tvReportCash500;
        @BindView(R.id.tvReportCash200)
        protected TextView tvReportCash200;
        @BindView(R.id.tvReportCash100)
        protected TextView tvReportCash100;
        @BindView(R.id.tvReportCash50)
        protected TextView tvReportCash50;
        @BindView(R.id.tvReportCash20)
        protected TextView tvReportCash20;
        @BindView(R.id.tvReportCash10)
        protected TextView tvReportCash10;
        @BindView(R.id.tvReportCash5)
        protected TextView tvReportCash5;
        @BindView(R.id.tvReportCash2)
        protected TextView tvReportCash2;
        @BindView(R.id.tvReportCash1)
        protected TextView tvReportCash1;
        @BindView(R.id.tvReportCashTotalNumber)
        protected TextView tvReportCashTotalNumber;
        @BindView(R.id.tvReportCashTotalAmount)
        protected TextView tvReportCashTotalAmount;
        @BindView(R.id.tvReportCashChequeNo)
        protected TextView tvReportCashChequeNo;
        @BindView(R.id.tvReportCashMap)
        protected TextView tvReportCashMap;



        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
