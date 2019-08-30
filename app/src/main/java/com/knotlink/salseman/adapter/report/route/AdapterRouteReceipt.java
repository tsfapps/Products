package com.knotlink.salseman.adapter.report.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.route.ModelRouteOrder;
import com.knotlink.salseman.model.report.route.ModelRouteReceipt;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteReceipt extends RecyclerView.Adapter<AdapterRouteReceipt.RouteReceiptViewHolder> {
    
    private Context tContext;
    private List<ModelRouteReceipt> tModels;

    public AdapterRouteReceipt(Context tContext, List<ModelRouteReceipt> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public RouteReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_receipt_item, viewGroup, false);
        return new RouteReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteReceiptViewHolder routeReceiptViewHolder, int i) {

        ModelRouteReceipt tModel = tModels.get(i);
        routeReceiptViewHolder.tvRouteReceiptDateOfReceipt.setText(tModel.getDatetime());
        routeReceiptViewHolder.tvRouteReceiptInvoiceNumber.setText(tModel.getInvoiceNo());
        routeReceiptViewHolder.tvRouteReceiptToa.setText(tModel.getTotalOutstandingAmount());
        routeReceiptViewHolder.tvRouteReceiptAmountReceived.setText(tModel.getAmountRecived());
        routeReceiptViewHolder.tvRouteReceiptPending.setText(tModel.getPendingOutstandingAmount());
        routeReceiptViewHolder.tvRouteReceiptCreditDays.setText(tModel.getCreditDays());
        routeReceiptViewHolder.tvRouteReceiptCreditLimit.setText(tModel.getCreditLimit());
        routeReceiptViewHolder.tvRouteReceiptChequeDate.setText(tModel.getChequeDate());
        routeReceiptViewHolder.tvRouteReceiptChequeNumber.setText(tModel.getChequeNo());
        routeReceiptViewHolder.tvRouteReceiptBankName.setText(tModel.getBankName());
        routeReceiptViewHolder.tvRouteReceiptRemarks.setText(tModel.getRemarks());

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class RouteReceiptViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteReceiptDateOfReceipt)
        protected TextView tvRouteReceiptDateOfReceipt;
        @BindView(R.id.tvRouteReceiptInvoiceNumber)
        protected TextView tvRouteReceiptInvoiceNumber;
        @BindView(R.id.tvRouteReceiptToa)
        protected TextView tvRouteReceiptToa;
        @BindView(R.id.tvRouteReceiptAmountReceived)
        protected TextView tvRouteReceiptAmountReceived;
        @BindView(R.id.tvRouteReceiptPending)
        protected TextView tvRouteReceiptPending;
        @BindView(R.id.tvRouteReceiptCreditDays)
        protected TextView tvRouteReceiptCreditDays;
        @BindView(R.id.tvRouteReceiptCreditLimit)
        protected TextView tvRouteReceiptCreditLimit;
        @BindView(R.id.tvRouteReceiptChequeDate)
        protected TextView tvRouteReceiptChequeDate;
        @BindView(R.id.tvRouteReceiptChequeNumber)
        protected TextView tvRouteReceiptChequeNumber;
        @BindView(R.id.tvRouteReceiptBankName)
        protected TextView tvRouteReceiptBankName;
        @BindView(R.id.tvRouteReceiptRemarks)
        protected TextView tvRouteReceiptRemarks;

        public RouteReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
