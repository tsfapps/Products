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
       distanceViewHolder.tvReportExpensesDate.setText(tModel.getDatetime());
       distanceViewHolder.tvReportExpensesFood.setText(tModel.getFood());
       distanceViewHolder.tvReportExpensesPettyCash.setText(tModel.getPettyCash());
       distanceViewHolder.tvReportExpensesPetrol.setText(tModel.getPetrol());
       distanceViewHolder.tvReportExpensesParking.setText(tModel.getParking());
       distanceViewHolder.tvReportExpensesRoad.setText(tModel.getRoadTollFee());
       distanceViewHolder.tvReportExpensesOthers.setText(tModel.getOthers());
       distanceViewHolder.tvReportExpensesTotal.setText(tModel.getTotal());
       distanceViewHolder.tvReportExpensesRemarks.setText(tModel.getRemarks());
        }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class DistanceViewHolder extends RecyclerView.ViewHolder{

       @BindView(R.id.tv_report_expenses_date)
       protected TextView tvReportExpensesDate;
       @BindView(R.id.tv_report_expenses_pettyCash)
       protected TextView tvReportExpensesPettyCash;
       @BindView(R.id.tv_report_expenses_food)
       protected TextView tvReportExpensesFood;
       @BindView(R.id.tv_report_expenses_parking)
       protected TextView tvReportExpensesParking;
       @BindView(R.id.tv_report_expenses_roadToll)
       protected TextView tvReportExpensesRoad;
       @BindView(R.id.tv_report_expenses_petrol)
       protected TextView tvReportExpensesPetrol;
       @BindView(R.id.tv_report_expenses_others)
       protected TextView tvReportExpensesOthers;
       @BindView(R.id.tv_report_expenses_total)
       protected TextView tvReportExpensesTotal;
       @BindView(R.id.tv_report_expenses_remarks)
       protected TextView tvReportExpensesRemarks;

        public DistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
