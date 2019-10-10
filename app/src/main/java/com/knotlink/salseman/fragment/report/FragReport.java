package com.knotlink.salseman.fragment.report;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.maps.MapsAll;
import com.knotlink.salseman.adapter.baseadapter.AdapterSalesMan;
import com.knotlink.salseman.adapter.spinner.AdapterAreaSalesMan;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.report.route.ReportRoute;
import com.knotlink.salseman.model.ModelAsmList;
import com.knotlink.salseman.model.dash.route.ModelMapAll;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

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

public class FragReport extends Fragment implements AdapterView.OnItemSelectedListener{
    private TextView tvMapDate;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
       final Calendar myCalendar = Calendar.getInstance();
       private FragmentManager tFragmentManager;
       private String dateFrom;
       private String dateTo;
    private List<ModelSalesMan> tModelsSalesMan;
    private List<ModelAsmList> tModelAsmList;
    private AdapterSalesMan tAdapterSalesMan;
    private AdapterAreaSalesMan tAdapterAreaSalesMan;

       @BindView(R.id.tv_report_date_from)
       protected TextView tvReportDateFrom;
       @BindView(R.id.tv_report_date_to)
       protected TextView tvReportDateTo;
       @BindView(R.id.rlSpnReportSalesMan)
       protected RelativeLayout rlSpnReportSalesMan;
       @BindView(R.id.spnReportSalesMan)
       protected Spinner spnReportSalesMan;
       @BindView(R.id.pbSpnReportSales)
       protected ProgressBar pbSpnReportSales;
    @BindView(R.id.rlSpnReportAreaSalesMan)
    protected RelativeLayout rlSpnReportAreaSalesMan;

    @BindView(R.id.spnReportAreaSalesMan)
       protected Spinner spnReportAreaSalesMan;
       @BindView(R.id.pbSpnReportAreaSales)
       protected ProgressBar pbSpnReportAreaSales;

       private String strAsmId;
       private String strSelectedUserId;
       private String strUserId;
       private String strUserType;

    public static FragReport newInstance(String strUserType) {

        FragReport fragment = new FragReport();
        fragment.strUserType = strUserType;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_report_new, container, false);
        ButterKnife.bind(this, view);

        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        pbSpnReportSales.setVisibility(View.VISIBLE);
        pbSpnReportAreaSales.setVisibility(View.VISIBLE);
        tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;

        }else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle("Report", getActivity());
        tFragmentManager = getFragmentManager();
        hideShowContent();

        String strReportDateFrom = tSharedPrefManager.getReportTimeFrom();
        String strReportDateTo = tSharedPrefManager.getReportTimeTo();

        if (strReportDateFrom.equalsIgnoreCase("")) {
            tvReportDateFrom.setText(DateUtils.getFirstDateOfMonth());
        }else {
            tvReportDateFrom.setText(strReportDateFrom);
        }
        if (strReportDateTo.equalsIgnoreCase("")) {
            tvReportDateTo.setText(DateUtils.getTodayDate());
        }else {
            tvReportDateTo.setText(strReportDateTo);
        }
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
        dateTo = DateUtils.convertFormat(tvReportDateTo.getText().toString().trim());
    }


    private void hideShowContent(){
        if (strUserType.equalsIgnoreCase("1")){
            rlSpnReportSalesMan.setVisibility(View.GONE);
            rlSpnReportAreaSalesMan.setVisibility(View.GONE);


        }
        else if (strUserType.equalsIgnoreCase("2")){
            rlSpnReportSalesMan.setVisibility(View.GONE);
            rlSpnReportAreaSalesMan.setVisibility(View.GONE);


        }
        else if (strUserType.equalsIgnoreCase("3")){
            rlSpnReportSalesMan.setVisibility(View.VISIBLE);
            rlSpnReportAreaSalesMan.setVisibility(View.GONE );
            spnReportSalesMan.setOnItemSelectedListener(this);
            callApiSalesMan(strUserId);

        }
        else if (strUserType.equalsIgnoreCase("0")){
            rlSpnReportSalesMan.setVisibility(View.VISIBLE);
            rlSpnReportAreaSalesMan.setVisibility(View.VISIBLE);
            spnReportAreaSalesMan.setOnItemSelectedListener(this);
            spnReportSalesMan.setOnItemSelectedListener(this);
            callApiAreaSalesMan();
        }
    }


    private void callApiSalesMan(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesMan>> call = api.salesAsmManList(strUserId);
        call.enqueue(new Callback<List<ModelSalesMan>>() {
            @Override
            public void onResponse(Call<List<ModelSalesMan>> call, Response<List<ModelSalesMan>> response) {
                tModelsSalesMan = response.body();
                pbSpnReportSales.setVisibility(View.GONE);
                tAdapterSalesMan = new AdapterSalesMan(tContext, tModelsSalesMan);
                spnReportSalesMan.setAdapter(tAdapterSalesMan);
            }

            @Override
            public void onFailure(Call<List<ModelSalesMan>> call, Throwable t) {

            }
        });
    }
    private void callApiAreaSalesMan(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelAsmList>> call = api.asmList(strUserId);
        call.enqueue(new Callback<List<ModelAsmList>>() {
            @Override
            public void onResponse(Call<List<ModelAsmList>> call, Response<List<ModelAsmList>> response) {
                tModelAsmList = response.body();
                pbSpnReportAreaSales.setVisibility(View.GONE);
                tAdapterAreaSalesMan = new AdapterAreaSalesMan(tContext, tModelAsmList);
                spnReportAreaSalesMan.setAdapter(tAdapterAreaSalesMan);
            }

            @Override
            public void onFailure(Call<List<ModelAsmList>> call, Throwable t) {
                Log.d(Constant.TAG, "ASM List Failure : "+t);
            }
        });
    }
    private void selectDateDialog(){
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
                Log.d(Constant.TAG, "User final Id : "+strUserId);
                Api api = ApiClients.getApiClients().create(Api.class);

                Call<List<ModelMapAll>> call = api.mapAll(strUserId, strDate);
                call.enqueue(new Callback<List<ModelMapAll>>() {
                    @Override
                    public void onResponse(Call<List<ModelMapAll>> call, Response<List<ModelMapAll>> response) {
                      List<ModelMapAll> tModels = response.body();
                        if (tModels.size()>0){
                            Intent tIntent = new Intent(tContext, MapsAll.class);
                            tIntent.putExtra(Constant.MODEL_INTENT, (Serializable) tModels);
                            tContext.startActivity(tIntent);}
                        else {
                            CustomDialog.showEmptyDialog(tContext);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ModelMapAll>> call, Throwable t) {

                    }
                });
            }
        });
        dialog.show();
    }


    @OnClick(R.id.llMapsAll)
    public void llMapsAllClicked(View view){
       selectDateDialog();
    }
    @OnClick(R.id.tvReportThreeMonths)
    public void tvReportThreeMonthsClicked(View view){
        String strDate = DateUtils.date3Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
        tvReportDateTo.setText(DateUtils.getTodayDate());
    }
    @OnClick(R.id.tvReportSixMonths)
    public void tvReportSixMonthsClicked(View view){
        String strDate = DateUtils.date6Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
        tvReportDateTo.setText(DateUtils.getTodayDate());


    }
    @OnClick(R.id.tvReportNineMonths)
    public void tvReportNineMonthsClicked(View view){
        String strDate = DateUtils.date9Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
        tvReportDateTo.setText(DateUtils.getTodayDate());

    }
    @OnClick(R.id.tvReportTwelveMonths)
    public void tvReportTwelveMonthsClicked(View view){
        String strDate = DateUtils.date12Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
        tvReportDateTo.setText(DateUtils.getTodayDate());
    }
    @OnClick(R.id.tv_report_date_from)
    public void reportFromDate(View view){
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
                        tSharedPrefManager.clearReportTimeStart();
                        tSharedPrefManager.setReportTimeStart(strMyDate);
                        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
                    }
                    else {
                        CustomToast.toastMid(getActivity(), Constant.DATE_DELIVERY);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.tv_report_date_to)
    public void reportToDate(View view){
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
                        tSharedPrefManager.clearReportTimeEnd();
                        tSharedPrefManager.setReportTimeEnd(strMyDate);
                        tvReportDateTo.setText(tSharedPrefManager.getReportTimeTo());
                    }
                    else {
                        CustomToast.toastMid(getActivity(), Constant.DATE_DELIVERY);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

 @OnClick(R.id.ll_report_attendance)
public void reportAttendance(View view){

        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportAttendance.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_distance)
public void reportDistance(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportDistance.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_route)
public void reportRoute(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportRoute. newInstance(dateFrom, dateTo, "", "", strUserType, strSelectedUserId)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_leadGeneration)
public void reportLead(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main,  ReportLead.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_meeting)
public void reportMeeting(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportMeeting.newInstance(dateFrom,dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_coldCall)
public void reportColdCall(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportColdCall.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_cashCollection)
public void reportCashCollection(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportCashCollection.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
     }
@OnClick(R.id.ll_report_expenses)
public void reportExpenses(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportExpenses.newInstance(dateFrom, dateTo,strUserType,strSelectedUserId)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_vehicleReport)
public void reportVehicle(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportVehicle.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spnReportAreaSalesMan:
                strAsmId = tModelAsmList.get(position).getUserId();
                callApiSalesMan(strAsmId);
                break;
                case R.id.spnReportSalesMan:
                strSelectedUserId = tModelsSalesMan.get(position).getUserId();
//                strUserId = strSelectedUserId;
                    break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
