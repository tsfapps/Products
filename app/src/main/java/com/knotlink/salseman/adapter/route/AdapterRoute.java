package com.knotlink.salseman.adapter.route;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MapsActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.dashboard.route.FragOrderList;
import com.knotlink.salseman.fragment.dashboard.route.FragRequestList;
import com.knotlink.salseman.fragment.dashboard.route.FragRouteActivity;
import com.knotlink.salseman.fragment.FragTask;
import com.knotlink.salseman.fragment.dashboard.route.FragSalesReturnFetch;
import com.knotlink.salseman.fragment.report.route.ReportRoute;
import com.knotlink.salseman.model.dash.ModelVisitShop;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.model.dash.route.ModelVisit;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.utils.ButtonBg;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomMethods;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private String strAttDate;
    private String strUSerId;
    private String strUserType;
    private String strSelectedUserId;
    private GPSTracker tGPSTracker;
    private String strLat;
    private String strLong;
    private String strAddress;

    public AdapterRoute(String strAttDate, Activity tActivity, FragmentManager tFragmentManager,
                        List<ModelShopList> tModels, String strUSerId, String strUserType, String strSelectedUserId) {
        this.strAttDate = strAttDate;
        this.tActivity = tActivity;

        this.tFragmentManager = tFragmentManager;
        this.tModels = tModels;
        this.strUSerId = strUSerId;
        this.strUserType = strUserType;
        this.strSelectedUserId = strSelectedUserId; }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_route_item, viewGroup,false);
        tContext = viewGroup.getContext();
        tGPSTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGPSTracker.latitude);
        strLong = String.valueOf(tGPSTracker.longitude);
        strAddress = tGPSTracker.getAddressLine(tContext);
        return new RouteViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RouteViewHolder routeViewHolder, final int i) {
        final ModelShopList tModel = tModels.get(i);
        final String strShopId = tModel.getShopId();
        final String strShopName = tModel.getShopName();
        final String strShopStatus = tModel.getStatus();
        routeViewHolder.tvRouteBadgeOrder.setText(tModel.getNewOrderCounter());

        routeViewHolder.tvRouteBadgeReturn.setText(tModel.getSalesReturnCounter());
        if (tModel.getSalesReturnCounter().equalsIgnoreCase("0") || !strUserType.equalsIgnoreCase("2")){
            routeViewHolder.tvRouteBadgeReturn.setVisibility(View.GONE);
            routeViewHolder.tvRouteBadgeRequest.setVisibility(View.GONE);
        }
        else {
            routeViewHolder.tvRouteBadgeReturn.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteBadgeReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tFragmentManager.beginTransaction().replace(R.id.container_main,
                            FragSalesReturnFetch.newInstance(tModels, i, strUserType, strSelectedUserId)).addToBackStack(null).commit();
                }
            });
        }

        if (tModel.getNewOrderCounter().equalsIgnoreCase("0")|| !strUserType.equalsIgnoreCase("2")){
            routeViewHolder.tvRouteBadgeOrder.setVisibility(View.GONE);
        }else {
            routeViewHolder.tvRouteBadgeOrder.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteBadgeOrder.setText(tModel.getNewOrderCounter());
            routeViewHolder.tvRouteBadgeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tFragmentManager.beginTransaction().replace(R.id.container_main, FragOrderList.newInstance(strShopId,
                            strShopName, strAttDate, strUserType, strSelectedUserId)).addToBackStack(null).commit();
                }
            });
        }

        if (tModel.getCounter().equalsIgnoreCase("0")|| !strUserType.equalsIgnoreCase("2")){
            routeViewHolder.tvRouteBadgeRequest.setVisibility(View.GONE);
        }else {
            routeViewHolder.tvRouteBadgeRequest.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteBadgeRequest.setText(tModel.getCounter());
            routeViewHolder.tvRouteBadgeRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tFragmentManager.beginTransaction().replace(R.id.container_main, FragRequestList.newInstance(tModels.get(i)
                            .getShopId(), strAttDate)).addToBackStack(null).commit();
                }
            });
        }



        if (strShopStatus.equalsIgnoreCase("Closed")){
            routeViewHolder.tvRouteShopStatus.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteShopStatus.setText("C");
            routeViewHolder.tvRouteShopStatus.setBackgroundResource(R.drawable.bg_badge_red);
        }
       else if (strShopStatus.equalsIgnoreCase("Inactive")){
            routeViewHolder.tvRouteShopStatus.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteShopStatus.setText("I");
            routeViewHolder.tvRouteShopStatus.setBackgroundResource(R.drawable.bg_badge_yellow); }
       else {
            routeViewHolder.tvRouteShopStatus.setVisibility(View.GONE);
        }

       if (tModel.getCounter().equalsIgnoreCase("0")|| strUserType.equalsIgnoreCase("2")){
            routeViewHolder.tvRouteTaskBadge.setVisibility(View.GONE);
        }else {
            routeViewHolder.tvRouteTaskBadge.setVisibility(View.VISIBLE);
            routeViewHolder.tvRouteTaskBadge.setText(tModel.getCounter());
            routeViewHolder.tvRouteTaskBadge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tFragmentManager.beginTransaction().replace(R.id.container_main, FragTask.newInstance(strSelectedUserId, strUserType)).addToBackStack(null).commit();
                }
            });
        }

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
        }
        routeViewHolder.tvRouteShopName.setText(tModel.getShopName());
        final String strShopLocation = tModel.getShopAddress();

        routeViewHolder.btnRouteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_green);

                Api api = ApiClients.getApiClients().create(Api.class);
                Call<ModelVisitShop> call = api.visitShop(strShopId,strUSerId,strLat,strLong,strAttDate);
                call.enqueue(new Callback<ModelVisitShop>() {
                    @Override
                    public void onResponse(Call<ModelVisitShop> call, Response<ModelVisitShop> response) {
                        ModelVisitShop tModel = response.body();
                        if (!tModel.getError()) {
                            tFragmentManager.beginTransaction().replace(R.id.container_main, FragRouteActivity.newInstance(strAttDate,
                                    strSelectedUserId, tModels, i, strUserType)).addToBackStack(null).commit();
                            Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ModelVisitShop> call, Throwable t) {

                    }
                });
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
                params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                edittext.setLayoutParams(params);
                container.addView(edittext);
                alert.setTitle("Mention the reason");
                alert.setView(container);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String strRemark = edittext.getText().toString();
                        String strShopId = tModel.getShopId();

                        if (!strRemark.equalsIgnoreCase("")) {
                            Api api = ApiClients.getApiClients().create(Api.class);
                            Call<ModelVisit> call = api.getShopNotVisit(strAttDate, strRemark, strUSerId,strLat, strLong, strAddress, strShopId);
                            call.enqueue(new Callback<ModelVisit>() {
                                @Override
                                public void onResponse(Call<ModelVisit> call, Response<ModelVisit> response) {
                                    ModelVisit tModel = response.body();
                                    if (!tModel.getError()) {
                                        CustomToast.toastTop(tActivity, tModel.getMessage());
                                        routeViewHolder.btnRouteNotVisit.setBackgroundResource(R.drawable.bg_simple_red);
                                        routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_grey);
                                    } else {
                                        CustomToast.toastTop(tActivity, tModel.getMessage());
                                    }
                                }
                                @Override
                                public void onFailure(Call<ModelVisit> call, Throwable t) {
                                    CustomLog.d(Constant.TAG, "Shop not visit failure : " + t);
                                }
                            });
                        }else {
                            CustomDialog.showEmptyDialog(tContext);
                        }
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
                        .setMessage(tModel.getContactName()+"\n\n"+
                        tModel.getEmail()+"\n\n"+
                        tModel.getShopAddress())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton(strPhoneNo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (strPhoneNo.length()<10){
                                    CustomToast.toastTop(tActivity, "Dialing number is incorrect...");
                                }else {
                                    CustomMethods.callPhone(strPhoneNo, tActivity, tContext) ;
                                }
                            }
                        })
                        .create()
                        .show();
            }
        });
        routeViewHolder.tvRouteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_route_report_date);
                dialog.setTitle("Select Date Range");
                dialog.setCancelable(true);

                // set the custom dialog components - text, image and button
                final TextView tvDateFrom = dialog.findViewById(R.id.tvReportOrderDateFrom);
                final TextView tvDateTo = dialog.findViewById(R.id.tvReportOrderDateTo);

                tvDateFrom.setText(DateUtils.date3Months());
                tvDateTo.setText(DateUtils.getTodayDate());
                tvDateFrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {
                            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                      int dayOfMonth) {
                                    String strCurrentDate = DateUtils.getTodayDate();
                                    myCalendar.set(Calendar.YEAR, year);
                                    myCalendar.set(Calendar.MONTH, monthOfYear);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
                                    try {
                                        Date myDate = sdf.parse(strCurrentDate);
                                        String strMyDate = sdf.format(myCalendar.getTime());
                                        Date selDate = sdf.parse(strMyDate);
                                        if (selDate.compareTo(myDate)<0) {
                                            tvDateFrom.setText(strMyDate);

                                        }
                                        else {
                                            Toast.makeText(tContext, Constant.DATE_DELIVERY,Toast.LENGTH_LONG).show();
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            new DatePickerDialog(tContext, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    }
                });
                Button btnSubmit =  dialog.findViewById(R.id.btnReportOrderDate);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strDateFrom = tvDateFrom.getText().toString().trim();
                        String strDateTo = tvDateTo.getText().toString().trim();
                        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportRoute.newInstance(strDateFrom, strDateTo,
                                strShopId, strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }
     class RouteViewHolder extends RecyclerView.ViewHolder{
         @BindView(R.id.btn_route_visit)
         Button btnRouteVisit;

         @BindView(R.id.tv_route_shop_name)
        TextView tvRouteShopName;
        @BindView(R.id.tvRouteBadgeReport)
        TextView tvRouteReport;
        @BindView(R.id.tvRouteBadgeTask)
        TextView tvRouteTaskBadge;
        @BindView(R.id.tvRouteBadgeRequest)
        TextView tvRouteBadgeRequest;
        @BindView(R.id.tvRouteBadgeShopStatus)
        TextView tvRouteShopStatus;
        @BindView(R.id.tvRouteBadgeOrder)
        TextView tvRouteBadgeOrder;
        @BindView(R.id.tvRouteBadgeReturn)
        TextView tvRouteBadgeReturn;
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
