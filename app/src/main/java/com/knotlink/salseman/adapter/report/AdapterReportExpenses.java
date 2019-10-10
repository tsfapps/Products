package com.knotlink.salseman.adapter.report;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.ModelReportExpenses;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterReportExpenses extends RecyclerView.Adapter<AdapterReportExpenses.DistanceViewHolder> {

    private List<ModelReportExpenses> tModels;
    private Context tContext;

    public AdapterReportExpenses(List<ModelReportExpenses> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public DistanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_expenses_item, viewGroup, false);
        return new DistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistanceViewHolder distanceViewHolder, int i) {
        ModelReportExpenses tModel = tModels.get(i);

//        final String strLoginLat = tModel.getLatitude();
//        final String strLoginLong = tModel.getLongitude();
//        final String strLogoutLat = tModel.getLatitude();
//        final String strLogoutLong = tModel.getLongitude();
//        final String strEndAddress = "Address : "+tModel.getAddress();
//        final String strStartAddress = "Address : "+tModel.getAddress();
//

        distanceViewHolder.tvReportDate.setText(tModel.getDatetime());
       distanceViewHolder.tvReportTypeExpenses.setText(tModel.getExpenseType());
       distanceViewHolder.tvReportAmount.setText(tModel.getAmount());
       distanceViewHolder.tvReportRemarks.setText(tModel.getRemarks());
       distanceViewHolder.tvReportExpensesMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
        }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

       @BindView(R.id.tvReportDate)
       protected TextView tvReportDate;
       @BindView(R.id.tvReportRemarks)
       protected TextView tvReportRemarks;
       @BindView(R.id.tvReportTypeExpenses)
       protected TextView tvReportTypeExpenses;
       @BindView(R.id.tvReportAmount)
       protected TextView tvReportAmount;
       @BindView(R.id.tvReportExpensesMap)
       protected TextView tvReportExpensesMap;

        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
