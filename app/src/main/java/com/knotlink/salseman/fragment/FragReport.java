package com.knotlink.salseman.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;


import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterSalesMan;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.report.ReportAttendance;
import com.knotlink.salseman.fragment.report.ReportCashCollection;
import com.knotlink.salseman.fragment.report.ReportColdCall;
import com.knotlink.salseman.fragment.report.ReportDistance;
import com.knotlink.salseman.fragment.report.ReportExpenses;
import com.knotlink.salseman.fragment.report.ReportLead;
import com.knotlink.salseman.fragment.report.ReportMeeting;
import com.knotlink.salseman.fragment.report.route.ReportReceipt;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

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
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
       final Calendar myCalendar = Calendar.getInstance();
       private FragmentManager tFragmentManager;
       private String dateFrom;
       private String dateTo;
    private List<ModelSalesMan> tModels;
    private AdapterSalesMan tAdapterSalesMan;

       @BindView(R.id.tv_report_date_from)
       protected TextView tvReportDateFrom;
       @BindView(R.id.tv_report_date_to)
       protected TextView tvReportDateTo;
       @BindView(R.id.spnReportSalesMan)
       protected Spinner spnReportSalesMan;





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
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Report", getActivity());
        tFragmentManager = getFragmentManager();
        callApiSalesMan();
        spnReportSalesMan.setOnItemSelectedListener(this);

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
    private void callApiSalesMan(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesMan>> call = api.salesManList();
        call.enqueue(new Callback<List<ModelSalesMan>>() {
            @Override
            public void onResponse(Call<List<ModelSalesMan>> call, Response<List<ModelSalesMan>> response) {
                tModels = response.body();
                tAdapterSalesMan = new AdapterSalesMan(tContext, tModels);
                spnReportSalesMan.setAdapter(tAdapterSalesMan);
            }

            @Override
            public void onFailure(Call<List<ModelSalesMan>> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.tvReportThreeMonths)
    public void tvReportThreeMonthsClicked(View view){

        String strDate = DateUtils.date3Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
    }
    @OnClick(R.id.tvReportSixMonths)
    public void tvReportSixMonthsClicked(View view){
        String strDate = DateUtils.date6Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());

    }
    @OnClick(R.id.tvReportNineMonths)
    public void tvReportNineMonthsClicked(View view){
        String strDate = DateUtils.date9Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
    }
    @OnClick(R.id.tvReportTwelveMonths)
    public void tvReportTwelveMonthsClicked(View view){
        String strDate = DateUtils.date12Months();
        tSharedPrefManager.clearReportTime();
        tSharedPrefManager.setReportTimeStart(strDate);
        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());
        dateFrom = DateUtils.convertFormat(tvReportDateFrom.getText().toString().trim());
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
                        tSharedPrefManager.setReportTimeStart(strMyDate);
                        tvReportDateFrom.setText(tSharedPrefManager.getReportTimeFrom());

//                        tvReportDateFrom.setText(strMyDate);

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

        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportAttendance.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_distance)
public void reportDistance(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportDistance.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_route)
public void reportRoute(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportReceipt.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.ll_report_leadGeneration)
public void reportLead(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main,  ReportLead.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_meeting)
public void reportMeeting(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportMeeting.newInstance(dateFrom,dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_coldCall)
public void reportColdCall(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportColdCall.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.ll_report_cashCollection)
public void reportCashCollection(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportCashCollection.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
     }
@OnClick(R.id.ll_report_expenses)
public void reportExpenses(View view){
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportExpenses.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
