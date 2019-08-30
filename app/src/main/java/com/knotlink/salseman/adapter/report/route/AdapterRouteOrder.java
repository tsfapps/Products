package com.knotlink.salseman.adapter.report.route;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.report.route.ModelRouteOrder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteOrder extends RecyclerView.Adapter<AdapterRouteOrder.RouteOrderViewHolder> {

    private Context tContext;
    private List<ModelRouteOrder> tModels;

    public AdapterRouteOrder(Context tContext, List<ModelRouteOrder> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public RouteOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_new_order_item, viewGroup, false);
        return new RouteOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteOrderViewHolder routeOrderViewHolder, int i) {

        ModelRouteOrder tModel = tModels.get(i);
        routeOrderViewHolder.tvRouteOrderDateOfOrder.setText(tModel.getDatetime());
        routeOrderViewHolder.tvRouteOrderTimeOfOrder.setText(tModel.getTime());
        routeOrderViewHolder.tvRouteOrderDateOfDelivery.setText(tModel.getDateOfDelivery());
        routeOrderViewHolder.tvRouteOrderCity.setText(tModel.getCity());
        routeOrderViewHolder.tvRouteOrderAddress.setText(tModel.getAddress());
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class RouteOrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteOrderDateOfOrder)
        protected TextView tvRouteOrderDateOfOrder;
        @BindView(R.id.tvRouteOrderTimeOfOrder)
        protected TextView tvRouteOrderTimeOfOrder;
        @BindView(R.id.tvRouteOrderDateOfDelivery)
        protected TextView tvRouteOrderDateOfDelivery;
        @BindView(R.id.tvRouteOrderCity)
        protected TextView tvRouteOrderCity;
        @BindView(R.id.tvRouteOrderAddress)
        protected TextView tvRouteOrderAddress;
        @BindView(R.id.ivOrderImage)
        protected ImageView ivOrderImage;
        @BindView(R.id.ivOrderSignature)
        protected ImageView ivOrderSignature;

        public RouteOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
