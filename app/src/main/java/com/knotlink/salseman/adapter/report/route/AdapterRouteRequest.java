package com.knotlink.salseman.adapter.report.route;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.OrderMapsActivity;
import com.knotlink.salseman.model.report.route.ModelRouteRequest;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteRequest extends RecyclerView.Adapter<AdapterRouteRequest.RouteRequestViewHolder> {

    private Context tContext;
    private List<ModelRouteRequest> tModels;

    public AdapterRouteRequest(Context tContext, List<ModelRouteRequest> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public RouteRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_route_request_item, viewGroup, false);
        return new RouteRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteRequestViewHolder routeRequestViewHolder, int i) {

        ModelRouteRequest tModel = tModels.get(i);
        routeRequestViewHolder.tvRouteRequestShopName.setText(tModel.getOrgName());
        routeRequestViewHolder.tvRouteRequestDateOfOrder.setText(tModel.getTaskAssignDate());
        routeRequestViewHolder.tvReportShopRequestType.setText(tModel.getSpecialRequestType());
        routeRequestViewHolder.tvRouteRequestRemarks.setText(tModel.getRemarks());
        final String strLat = tModel.getLatitude();
        final String strLong = tModel.getLongitude();
        final String strShopName = tModel.getOrgName();
        final String strAreaStatus = tModel.getAreaStatus();
        final String strDate = tModel.getTaskAssignDate();

        routeRequestViewHolder.ivReportRequestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, OrderMapsActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, strLat);
                tIntent.putExtra(Constant.FIRST_LONG, strLong);
                tIntent.putExtra(Constant.SHOP_NAME, strShopName);
                tIntent.putExtra(Constant.START_DATE, strDate);
                tIntent.putExtra(Constant.AREA_STATUS, strAreaStatus);
                tContext.startActivity(tIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class RouteRequestViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteRequestShopName)
        protected TextView tvRouteRequestShopName;
        @BindView(R.id.tvRouteRequestDateOfOrder)
        protected TextView tvRouteRequestDateOfOrder;
        @BindView(R.id.tvReportShopRequestType)
        protected TextView tvReportShopRequestType;
        @BindView(R.id.tvRouteRequestRemarks)
        protected TextView tvRouteRequestRemarks;
        @BindView(R.id.ivReportRequestLocation)
        protected ImageView ivReportRequestLocation;

        public RouteRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
