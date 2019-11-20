package com.knotlink.salseman.fragment.report.route;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.DataUtils;
import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.RouteMapsActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragReportNewBinding;
import com.knotlink.salseman.databinding.FragReportRouteBinding;
import com.knotlink.salseman.databinding.FragRouteBinding;
import com.knotlink.salseman.model.report.ModelReportOrderMap;
import com.knotlink.salseman.model.report.ModelUserActivityCount;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelActivityCount;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRoute extends Fragment {

    private TextView tvMapDate;
    private ViewModelActivityCount tViewModel;
    private FragReportRouteBinding tBinding;

    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private FragmentManager tFragmentManager;
    @BindView(R.id.tvRouteShopName)
    protected TextView tvRouteShopName;

    private  String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    private String dateFrom;
    private String dateTo;
    private String shopId;
    private String strShopName;

    public static ReportRoute newInstance(String dateFrom, String dateTo, String shopId, String strShopName, String strUserType, String strSelectedUserId) {

        ReportRoute fragment = new ReportRoute();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.shopId = shopId;
        fragment.strShopName = strShopName;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_report_route, container, false);
       View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tSharedPrefManager = new SharedPrefManager(tContext);
        tViewModel = ViewModelProviders.of(this).get(ViewModelActivityCount.class);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        tvRouteShopName.setText(strShopName);
        tFragmentManager = getFragmentManager();
        SetTitle.tbTitle(" Route Report", getActivity());
        setActivityCounter(dateFrom, dateTo);
     }
    @OnClick(R.id.ll_ReportNewOrder)
    public void reportNewOrderClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportNewOrder.newInstance(dateFrom, dateTo, shopId, strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }

    @OnClick(R.id.ll_ReportReceipt)
    public void reportReceiptClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportReceipt.newInstance(dateFrom, dateTo, shopId, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportSpecialRequest)
    public void reportSpecialRequestClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportRequest.newInstance(dateFrom, dateTo, shopId,strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportComplain)
    public void reportComplainClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportComplain.newInstance(dateFrom, dateTo, shopId, strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportNoActivity)
    public void reportNoActivityClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportNoActivity.newInstance(dateFrom, dateTo, shopId, strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportNoVisit)
    public void reportNoVisitClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportNoVisit.newInstance(dateFrom, dateTo, shopId, strShopName, strUserType, strSelectedUserId)).addToBackStack(null).commit();
    }
 @OnClick(R.id.tvRouteOrderMap)
    public void tvRouteOrderMapClicked(View view){
     selectDateDialog("New Order");
    }
 @OnClick(R.id.tvRouteReceiptMap)
    public void tvRouteReceiptMapClicked(View view){
     selectDateDialog("Receipt");
    }
 @OnClick(R.id.tvRouteSpecialMap)
    public void tvRouteSpecialMapClicked(View view){
     selectDateDialog("Special Request");
    }
 @OnClick(R.id.tvRouteFeedbackMap)
    public void tvRouteFeedbackMapClicked(View view){
     selectDateDialog("Complaint");
    }
 @OnClick(R.id.tvRouteNoActivityMap)
    public void tvRouteNoActivityMapClicked(View view){
     selectDateDialog("No Activity");
    }

    private void selectDateDialog(final String strTableName){
        final Calendar myCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_map_date);
        dialog.setTitle("Select Date");
        dialog.setCancelable(true);
        tvMapDate =  dialog.findViewById(R.id.tvMapDate);
        final Button btnMapDateSubmit =  dialog.findViewById(R.id.btnMapDateSubmit);
        tvMapDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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
                            if (selDate.compareTo(myDate)<=0) {
                                tvMapDate.setText(strMyDate);
                                btnMapDateSubmit.setEnabled(true);
                            }
                            else {
                                Toast.makeText(tContext, "Select the correct date", Toast.LENGTH_LONG).show();
                                btnMapDateSubmit.setEnabled(false);

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new DatePickerDialog(tContext, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        btnMapDateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String strDate = DateUtils.convertDdToYyyy(tvMapDate.getText().toString().trim());
                Log.d(Constant.TAG, "Date : "+strDate);
                Api api = ApiClients.getApiClients().create(Api.class);
                Call<List<ModelReportOrderMap>> call = api.viewReportOrderMap(strUserId, strTableName, strDate);
                call.enqueue(new Callback<List<ModelReportOrderMap>>() {
                    @Override
                    public void onResponse(Call<List<ModelReportOrderMap>> call, Response<List<ModelReportOrderMap>> response) {
                        List<ModelReportOrderMap> tModels = response.body();
                        if (tModels.size()>0){
                        Intent tIntent = new Intent(tContext, RouteMapsActivity.class);
                        tIntent.putExtra(Constant.MODEL_INTENT, (Serializable) tModels);
                        tContext.startActivity(tIntent);}
                        else {
                            CustomDialog.showEmptyDialog(tContext);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<ModelReportOrderMap>> call, Throwable t) {

                    }
                });
            }
        });
        dialog.show();
    }

    private void setActivityCounter(String strDateFrom, String strDateTo){
        tViewModel.getActivityCount(strUserId, strDateFrom, strDateTo).observe(this, new Observer<ModelUserActivityCount>() {
            @Override
            public void onChanged(@Nullable ModelUserActivityCount tModelUserActivityCount) {
                tBinding.setActivityCounter(tModelUserActivityCount);
            }
        });
    }


}
