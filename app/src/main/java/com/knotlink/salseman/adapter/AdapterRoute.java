package com.knotlink.salseman.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MapsActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.FragRouteActivity;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.model.ModelVisit;
import com.knotlink.salseman.utils.ButtonBg;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomMethods;
import com.knotlink.salseman.utils.CustomToast;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRoute extends RecyclerView.Adapter<AdapterRoute.RouteViewHolder> {
    private Activity tActivity;
    private Context tContext;
    private FragmentManager tFragmentManager;
    private List<ModelShopList> tModels;
    private String strUSerId;
    private String strUSerType;

    public AdapterRoute(Activity tActivity, Context tContext, FragmentManager tFragmentManager, List<ModelShopList> tModels, String strUSerId, String strUSerType) {
        this.tActivity = tActivity;
        this.tContext = tContext;
        this.tFragmentManager = tFragmentManager;
        this.tModels = tModels;
        this.strUSerId = strUSerId;
        this.strUSerType = strUSerType;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_route_item, viewGroup,false);
        return new RouteViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RouteViewHolder routeViewHolder, final int i) {
        final ModelShopList tModel = tModels.get(i);

        final String strPhoneNo = tModel.getContactNo();
        switch (tModel.getVisitStatus()){
            case "0":
                ButtonBg.setNotVisitButtonBg(routeViewHolder.btnRouteVisit, R.drawable.bg_btn_main,
                        routeViewHolder.btnRouteNotVisit, R.drawable.bg_btn_main, true, true);
                break;
            case "1":
                ButtonBg.setNotVisitButtonBg(routeViewHolder.btnRouteVisit, R.drawable.bg_simple_green,
                        routeViewHolder.btnRouteNotVisit, R.drawable.bg_simple_grey, true, false);
                break;
            case "2":
                ButtonBg.setNotVisitButtonBg(routeViewHolder.btnRouteVisit, R.drawable.bg_simple_grey,
                        routeViewHolder.btnRouteNotVisit, R.drawable.bg_simple_red, false, false);
                break;
                default:
                    ButtonBg.setNotVisitButtonBg(routeViewHolder.btnRouteVisit, R.drawable.bg_btn_main,
                            routeViewHolder.btnRouteNotVisit, R.drawable.bg_btn_main, true, true);
        }
        routeViewHolder.tvRouteShopName.setText(tModel.getShopName());
        routeViewHolder.tvRoutePhoneNo.setText(tModel.getContactNo());
        final String strShopLocation = tModel.getShopAddress();
        routeViewHolder.btnRouteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_green);
                routeViewHolder.btnRouteNotVisit.setBackgroundResource(R.drawable.bg_btn_main);
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragRouteActivity.newInstance(tModels, i, strUSerType)).addToBackStack(null).commit();
            }
        });
        routeViewHolder.btnRouteNotVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(tContext);
                final EditText edittext = new EditText(tContext);
                edittext.setBackgroundResource(R.drawable.bg_et);
                //  edittext.setMinLines(4);
                edittext.setSingleLine();
                FrameLayout container = new FrameLayout(tContext);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
                params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
                params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
                params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
                edittext.setLayoutParams(params);
                container.addView(edittext);
                alert.setTitle("Mention the reason");
                alert.setView(container);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String strRemark = edittext.getText().toString();
                        String strShopId = tModel.getShopId();
                        CustomLog.d(Constant.TAG, "Shop Id : "+strShopId +"\nUser ID : "+strUSerId);

                        Api api = ApiClients.getApiClients().create(Api.class);
                        Call<ModelVisit> call = api.getShopNotVisit(strRemark,strUSerId,strShopId);
                        call.enqueue(new Callback<ModelVisit>() {
                            @Override
                            public void onResponse(Call<ModelVisit> call, Response<ModelVisit> response) {
                                ModelVisit tModel = response.body();
                                if (!tModel.getError()) {
                                    CustomToast.toastTop(tActivity, tModel.getMessage());
                                    routeViewHolder.btnRouteNotVisit.setBackgroundResource(R.drawable.bg_simple_red);
                                    routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_grey);
                                }else {
                                    CustomToast.toastTop(tActivity, tModel.getMessage());

                                }

                            }

                            @Override
                            public void onFailure(Call<ModelVisit> call, Throwable t) {
                                CustomLog.d(Constant.TAG, "Shop not visit failure : "+t);

                            }
                        });



                        //What ever you want to do with the value
                       // Editable YouEditTextValue = edittext.getText();
                        //OR

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        routeViewHolder.ivRouteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tContext, MapsActivity.class);
                intent.putExtra(Constant.SHOP_LOCATION, strShopLocation);
                tContext.startActivity(intent);
            }
        });
        routeViewHolder.ivRouteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(tContext)
                        .setTitle(tModel.getShopName())
                        .setMessage("Contact Name : "+tModel.getContactName()+"\n\n"+
                        "Email Id : "+ tModel.getEmail()+"\n\n"+
                        "Address : "+tModel.getShopAddress())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
            }
        });

        routeViewHolder.tvRoutePhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strPhoneNo.length()<10){
               CustomToast.toastTop(tActivity, "Dialing number is incorrect...");
                }else {

                       CustomMethods.callPhone(strPhoneNo, tActivity, tContext) ;
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return tModels.size();
    }
     class RouteViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvRoutePhoneNo)
        TextView tvRoutePhoneNo;
        @BindView(R.id.tv_route_shop_name)
        TextView tvRouteShopName;
        @BindView(R.id.btn_route_visit)
        Button btnRouteVisit;
        @BindView(R.id.btn_route_notVisit)
        Button btnRouteNotVisit;
        @BindView(R.id.iv_route_location)
        ImageView ivRouteLocation;
        @BindView(R.id.iv_route_details)
        ImageView ivRouteDetails;
        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
