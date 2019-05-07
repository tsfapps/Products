package com.knotlink.salseman.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;


import com.knotlink.salseman.R;
import com.knotlink.salseman.fragment.report.ReportAttendance;
import com.knotlink.salseman.fragment.report.ReportColdCall;
import com.knotlink.salseman.fragment.report.ReportDistance;
import com.knotlink.salseman.fragment.report.ReportExpenses;
import com.knotlink.salseman.fragment.report.ReportLead;
import com.knotlink.salseman.fragment.report.ReportMeeting;
import com.knotlink.salseman.fragment.report.route.ReportReceipt;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragReport extends Fragment{
    private Context tContext;
       final Calendar myCalendar = Calendar.getInstance();
       private FragmentManager tFragmentManager;

       @BindView(R.id.tv_report_date_from)
       protected TextView tvReportDateFrom;
       @BindView(R.id.tv_report_date_to)
       protected TextView tvReportDateTo;





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
        SetTitle.tbTitle("Report", getActivity());
        tFragmentManager = getFragmentManager();
        tvReportDateFrom.setText(DateUtils.getFirstDateOfMonth());
        tvReportDateTo.setText(DateUtils.getTodayDate());

    }

//    private void datePicker(){
//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                String strCurrentDate = DateUtils.getTodayDate();
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
//                String strMyDate = sdf.format(myCalendar.getTime());
//                if (strMyDate.compareTo(strCurrentDate)>1) {
//                    tvReportDateFrom.setText(strMyDate);
//                }
//                else {
//                    CustomToast.toastMid(tContext, Constant.DATE_DELIVERY);
//                }
//            }
//        };
//        new DatePickerDialog(getContext(), date, myCalendar
//                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//    }

    @OnClick(R.id.tv_report_date_from)
    public void reportFromDate(View view){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String strCurrentDate = DateUtils.getTodayDate();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
                String strMyDate = sdf.format(myCalendar.getTime());
                tvReportDateFrom.setText(strMyDate);

//                if (strMyDate.compareTo(strCurrentDate)<0) {
//                }
//                else {
//                    CustomToast.toastMid(tContext, Constant.DATE_DELIVERY);
//                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.tv_report_date_to)
    public void reportFromTo(View view){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String strCurrentDate = tvReportDateFrom.getText().toString().trim();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
                String strMyDate = sdf.format(myCalendar.getTime());
                tvReportDateTo.setText(strMyDate);

//                 if (strMyDate.compareTo(strCurrentDate)<0) {
//                }
//                else {
//                    CustomToast.toastMid(tContext, Constant.DATE_DELIVERY);
//                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

 @OnClick(R.id.iv_report_attendance)
public void reportAttendance(View view){
        String dateFrom = tvReportDateFrom.getText().toString().trim();
        String dateTo = tvReportDateTo.getText().toString().trim();
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportAttendance.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.iv_report_distance)
public void reportDistance(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportDistance.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.iv_report_route)
public void reportRoute(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportReceipt.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
}
@OnClick(R.id.iv_report_leadGeneration)
public void reportLead(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main,  ReportLead.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.iv_report_meeting)
public void reportMeeting(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportMeeting.newInstance(dateFrom,dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.iv_report_coldCall)
public void reportColdCall(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportColdCall.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
@OnClick(R.id.iv_report_cashCollection)
public void reportCashCollection(View view){
//        callApiAttendance();
     }
@OnClick(R.id.iv_report_expenses)
public void reportExpenses(View view){
    String dateFrom = tvReportDateFrom.getText().toString().trim();
    String dateTo = tvReportDateTo.getText().toString().trim();
    tFragmentManager.beginTransaction().replace(R.id.container_main, ReportExpenses.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
}
