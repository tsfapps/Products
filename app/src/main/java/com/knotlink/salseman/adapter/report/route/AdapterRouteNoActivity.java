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
import com.knotlink.salseman.model.report.route.ModelRouteNoActivity;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteNoActivity extends RecyclerView.Adapter<AdapterRouteNoActivity.RouteNoActivityViewHolder> {

    private Context tContext;
    private List<ModelRouteNoActivity> tModels;

    public AdapterRouteNoActivity(Context tContext, List<ModelRouteNoActivity> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public RouteNoActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_route_complain_item, viewGroup, false);
        return new RouteNoActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteNoActivityViewHolder routeNoActivityViewHolder, int i) {

        ModelRouteNoActivity tModel = tModels.get(i);
        routeNoActivityViewHolder.tvRouteComplainShopName.setText(tModel.getShopName());
        routeNoActivityViewHolder.tvRouteComplainDateOfOrder.setText(tModel.getDateTime());
        routeNoActivityViewHolder.tvRouteComplainRemarks.setText(tModel.getRemarks());
        final String strLat = tModel.getLatitude();
        final String strLong = tModel.getLongitude();
        final String strShopName = tModel.getShopName();
        final String strAreaStatus = tModel.getAreaStatus();
        final String strDate = tModel.getDateTime();

        routeNoActivityViewHolder.tvReportComplainLocation.setOnClickListener(new View.OnClickListener() {
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

    public class RouteNoActivityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteComplainShopName)
        protected TextView tvRouteComplainShopName;
        @BindView(R.id.tvRouteComplainDateOfOrder)
        protected TextView tvRouteComplainDateOfOrder;
        @BindView(R.id.tvRouteComplainRemarks)
        protected TextView tvRouteComplainRemarks;
        @BindView(R.id.tvReportComplainLocation)
        protected TextView tvReportComplainLocation;

        public RouteNoActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
