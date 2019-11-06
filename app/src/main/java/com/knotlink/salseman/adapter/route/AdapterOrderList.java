package com.knotlink.salseman.adapter.route;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.ReportMapActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragOrderListItemBinding;
import com.knotlink.salseman.model.dash.route.order.ModelOrderDeliver;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;
import com.knotlink.salseman.model.dash.route.order.ModelOrderNotDeliver;
import com.knotlink.salseman.model.task.ModelTaskDecline;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.DateUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOrderList extends RecyclerView.Adapter<AdapterOrderList.OrderListFetchViewHolder> {

    private List<ModelOrderList> tModels;
    private Context tContext;

    private String strUserId;
    private String strLat;
    private String strLong;
    private String strAddress;

    public AdapterOrderList(String strUserId, String strLat, String strLong, String strAddress) {
        this.strUserId = strUserId;
        this.strLat = strLat;
        this.strLong = strLong;
        this.strAddress = strAddress;
    }

    @NonNull
    @Override
    public OrderListFetchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragOrderListItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_order_list_item, viewGroup, false);
        tContext=viewGroup.getContext();
        return new OrderListFetchViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListFetchViewHolder orderListFetchViewHolder, int i) {
        final ModelOrderList tModel = tModels.get(i);
        orderListFetchViewHolder.tBinding.setOrderList(tModel);

        orderListFetchViewHolder.tvOrderListMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tIntent = new Intent(tContext, ReportMapActivity.class);
                tIntent.putExtra(Constant.FIRST_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.FIRST_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.SECOND_LAT, tModel.getLatitude());
                tIntent.putExtra(Constant.SECOND_LONG, tModel.getLongitude());
                tIntent.putExtra(Constant.START_ADDRESS, tModel.getActivityAddress());
                tIntent.putExtra(Constant.END_ADDRESS, tModel.getActivityAddress());
                tContext.startActivity(tIntent);
            }
        });

        orderListFetchViewHolder.btnOrderDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_submit);
                dialog.setCancelable(true);
                Button btnSubmit=  dialog.findViewById(R.id.btnConfirmSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListFetchViewHolder.btnOrderNotDeliver.setEnabled(false);
                        orderListFetchViewHolder.btnOrderDeliver.setEnabled(false);
                        String strId = tModel.getId();
                        Api api = ApiClients.getApiClients().create(Api.class);
                        Call<ModelOrderDeliver> call = api.orderDeliver(strId, strUserId,
                                strLat, strLong, strAddress);
                        call.enqueue(new Callback<ModelOrderDeliver>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<ModelOrderDeliver> call, Response<ModelOrderDeliver> response) {
                                ModelOrderDeliver tModel = response.body();
                                if (!tModel.getError()){
                                    dialog.dismiss();
                                    orderListFetchViewHolder.btnOrderNotDeliver.setBackgroundResource(R.drawable.bg_btn_disabled);
                                    orderListFetchViewHolder.btnOrderDeliver.setBackgroundResource(R.drawable.bg_color_green);
                                    orderListFetchViewHolder.btnOrderDeliver.setText("Order Delivered");
                                    Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ModelOrderDeliver> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        orderListFetchViewHolder.btnOrderNotDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_remarks);
                dialog.setTitle("Not Delivering");
                dialog.setCancelable(true);

                final EditText etRemarks = dialog.findViewById(R.id.etRemarks);
                Button btnSubmit=  dialog.findViewById(R.id.btnRemarksSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListFetchViewHolder.btnOrderNotDeliver.setEnabled(false);
                        orderListFetchViewHolder.btnOrderDeliver.setEnabled(false);
                        String strId = tModel.getId();
                        String strRemarks = etRemarks.getText().toString().trim();
                        if (!strRemarks.equalsIgnoreCase("")) {
                            Api api = ApiClients.getApiClients().create(Api.class);
                            Call<ModelOrderNotDeliver> call = api.orderNotDeliver(strId, strUserId,
                                    strRemarks, strLat, strLong, strAddress);
                            call.enqueue(new Callback<ModelOrderNotDeliver>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onResponse(Call<ModelOrderNotDeliver> call, Response<ModelOrderNotDeliver> response) {
                                    ModelOrderNotDeliver tModel = response.body();
                                    if (!tModel.getError()) {
                                        dialog.dismiss();
                                        orderListFetchViewHolder.btnOrderNotDeliver.setBackgroundResource(R.drawable.bg_color_red);
                                        orderListFetchViewHolder.btnOrderNotDeliver.setText("Order Not Delivered");
                                        orderListFetchViewHolder.btnOrderDeliver.setBackgroundResource(R.drawable.bg_btn_disabled);
                                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ModelOrderNotDeliver> call, Throwable t) {

                                }
                            });

                        }
                        else {
                            Toast.makeText(tContext, "Write the reason for not delivering", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                dialog.show();
            }
        });

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

    public void settModels(List<ModelOrderList> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class OrderListFetchViewHolder extends RecyclerView.ViewHolder{

        private FragOrderListItemBinding tBinding;
        private TextView tvOrderListMap;
        private Button btnOrderDeliver;
        private Button btnOrderNotDeliver;

        public OrderListFetchViewHolder(FragOrderListItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvOrderListMap = tBinding.tvOrderListMap;
            btnOrderDeliver = tBinding.btnOrderDeliver;
            btnOrderNotDeliver = tBinding.btnOrderNotDeliver;

        }
    }
}
