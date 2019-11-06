package com.knotlink.salseman.adapter.route;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.databinding.FragSalesReturnFetchItemBinding;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnShopFetch;

import java.util.List;

public class AdapterSalesReturnFetch extends RecyclerView.Adapter<AdapterSalesReturnFetch.SalesReturnFetchViewHolder> {

    private List<ModelSalesReturnShopFetch> tModels;
    private Context tContext;

    @NonNull
    @Override
    public SalesReturnFetchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragSalesReturnFetchItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_sales_return_fetch_item, viewGroup, false);

        tContext=viewGroup.getContext();
        return new SalesReturnFetchViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesReturnFetchViewHolder salesReturnFetchViewHolder, int i) {
        final ModelSalesReturnShopFetch tModel = tModels.get(i);
        salesReturnFetchViewHolder.tBinding.setSalesReturnFetch(tModel);
        if (tModel.getStatus().equalsIgnoreCase("1")){
            salesReturnFetchViewHolder.tvSalesReturnFetchStatus.setText("Accepted");
            salesReturnFetchViewHolder.llSalesReturnFetchStatusColor.setBackgroundResource(R.color.green);
        }else {
            salesReturnFetchViewHolder.tvSalesReturnFetchStatus.setText("Rejected");
            salesReturnFetchViewHolder.llSalesReturnFetchStatusColor.setBackgroundResource(R.color.red);

        }
    }

    @Override
    public int getItemCount()
    {
        if (tModels != null) {
            return tModels.size();
        }else {
            return 0;
        }
    }

    public void settModels(List<ModelSalesReturnShopFetch> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class SalesReturnFetchViewHolder extends RecyclerView.ViewHolder{

        private FragSalesReturnFetchItemBinding tBinding;
        private TextView tvSalesReturnFetchStatus;
        private View llSalesReturnFetchStatusColor;
        public SalesReturnFetchViewHolder(FragSalesReturnFetchItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvSalesReturnFetchStatus = tBinding.tvSalesReturnFetchStatus;
            llSalesReturnFetchStatusColor = tBinding.llSalesReturnFetchStatusColor;
        }
    }
}
