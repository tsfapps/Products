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
import com.knotlink.salseman.model.report.route.ModelRouteComplain;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteComplain extends RecyclerView.Adapter<AdapterRouteComplain.RouteComplainViewHolder> {

    private Context tContext;
    private List<ModelRouteComplain> tModels;

    public AdapterRouteComplain(Context tContext, List<ModelRouteComplain> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public RouteComplainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_route_complain_item, viewGroup, false);
        return new RouteComplainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteComplainViewHolder routeComplainViewHolder, int i) {

        ModelRouteComplain tModel = tModels.get(i);
        routeComplainViewHolder.tvRouteComplainShopName.setText(tModel.getShopName());
        routeComplainViewHolder.tvRouteComplainDateOfOrder.setText(tModel.getDatetime());
        routeComplainViewHolder.tvRouteComplainRemarks.setText(tModel.getRemarks());
        final String strLat = tModel.getLatitude();
        final String strLong = tModel.getLongitude();
        final String strShopName = tModel.getShopName();
        final String strAreaStatus = tModel.getAreaStatus();
        final String strDate = tModel.getDatetime();

        routeComplainViewHolder.ivReportComplainLocation.setOnClickListener(new View.OnClickListener() {
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

    public class RouteComplainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteComplainShopName)
        protected TextView tvRouteComplainShopName;
        @BindView(R.id.tvRouteComplainDateOfOrder)
        protected TextView tvRouteComplainDateOfOrder;
        @BindView(R.id.tvRouteComplainRemarks)
        protected TextView tvRouteComplainRemarks;
        @BindView(R.id.ivReportComplainLocation)
        protected ImageView ivReportComplainLocation;

        public RouteComplainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
