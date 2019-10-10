package com.knotlink.salseman.adapter.report.route;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.OrderMapsActivity;
import com.knotlink.salseman.model.report.route.ModelRouteOrder;
import com.knotlink.salseman.utils.Constant;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_route_order_item, viewGroup, false);
        return new RouteOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteOrderViewHolder routeOrderViewHolder, int i) {

        ModelRouteOrder tModel = tModels.get(i);
        routeOrderViewHolder.tvRouteOrderShopName.setText(tModel.getShopName());
        routeOrderViewHolder.tvRouteOrderDateOfOrder.setText(tModel.getDatetime());
        routeOrderViewHolder.tvRouteOrderRemarks.setText(tModel.getRemarks());
        final String strLat = tModel.getLatitude();
        final String strLong = tModel.getLongitude();
        final String strShopName = tModel.getShopName();
        final String strAddress = tModel.getAddress();
        final String strAreaStatus = tModel.getAreaStatus();
        final String strDate = tModel.getDate();

        routeOrderViewHolder.tvReportOrderLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, OrderMapsActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, strLat);
                tIntent.putExtra(Constant.FIRST_LONG, strLong);
                tIntent.putExtra(Constant.SHOP_NAME, strShopName);
                tIntent.putExtra(Constant.START_DATE, strDate);
                tIntent.putExtra(Constant.START_ADDRESS, strAddress);
                tIntent.putExtra(Constant.AREA_STATUS, strAreaStatus);
                tContext.startActivity(tIntent);
            }
        });

        final String imgUrl = Constant.URL_IMG_ORDER +tModel.getOrderedImage();
        Glide.with(tContext)
                .load(imgUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.main_logo).error(R.drawable.bg_camera))
                .into(routeOrderViewHolder.ivRouteOrderImage);
        routeOrderViewHolder.ivRouteOrderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_zoom_image);
                dialog.setTitle("Special Request");
                dialog.setCancelable(true);
                ImageView ivZoomImage =  dialog.findViewById(R.id.ivZoomImage);
                Glide.with(tContext)
                        .load(imgUrl)
                        .apply(RequestOptions.placeholderOf(R.drawable.main_logo).error(R.drawable.bg_camera))
                        .into(ivZoomImage);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class RouteOrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRouteOrderShopName)
        protected TextView tvRouteOrderShopName;
        @BindView(R.id.tvRouteOrderDateOfOrder)
        protected TextView tvRouteOrderDateOfOrder;
        @BindView(R.id.tvRouteOrderRemarks)
        protected TextView tvRouteOrderRemarks;
        @BindView(R.id.tvReportOrderLocation)
        protected TextView tvReportOrderLocation;
        @BindView(R.id.ivRouteOrderImage)
        protected ImageView ivRouteOrderImage;

        public RouteOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
