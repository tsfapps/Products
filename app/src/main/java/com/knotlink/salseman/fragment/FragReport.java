package com.knotlink.salseman.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterReportSpinnerActivity;
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

public class FragReport extends Fragment {
    private Context tContext;
    @BindView(R.id.tv_report_top_activity)
    protected TextView tvReportTopActivity;
    @BindView(R.id.tv_report_top_route)
    protected TextView tvReportTopRoute;
    @BindView(R.id.tv_report_top_login)
    protected TextView tvReportTopLogin;
    @BindView(R.id.tv_report_top_map)
    protected TextView tvReportTopMap;
    @BindView(R.id.tv_report_date_from)
    protected TextView tvReportDateFrom;
    @BindView(R.id.tv_report_date_to)
    protected TextView tvReportDateTO;
    @BindView(R.id.tv_report_detail)
    protected TextView tvReportDetail;
    @BindView(R.id.rl_report_spinner_activity)
    protected RelativeLayout rlSpinnerBgActivity;

    @BindView(R.id.spinner_report_activity)
    protected Spinner spnReportActivity;
     @BindView(R.id.spinner_report_salesman)
    protected Spinner spnReportSalesman;
    final Calendar myCalendar = Calendar.getInstance();




    String[] countryNames={"Distance","Meeting","New Order","Receipt","Cold Call","Expenses"};
    int flags[] = {R.drawable.ic_main_route, R.drawable.ic_main_meeting, R.drawable.ic_main_order,
            R.drawable.ic_main_cold_call, R.drawable.ic_main_rupee, R.drawable.ic_main_expenses};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_report, container, false);
        ButterKnife.bind(this, view);
        tContext = getContext();
        initFrag();
        return view;
    }
    private void initFrag(){
        SetTitle.tbTitle("Report", getActivity());
        tvReportTopActivity.setBackgroundResource(R.drawable.bg_simple_aceent);
        AdapterReportSpinnerActivity spinnerAdapter = new AdapterReportSpinnerActivity(tContext, flags, countryNames);
        spnReportActivity.setAdapter(spinnerAdapter);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_report_top_activity)
    public void onClickReportTopActivity(View view){
            tvReportTopActivity.setBackgroundResource(R.drawable.bg_simple_aceent);
            tvReportTopRoute.setBackgroundResource(R.drawable.bg_simple_dark);
            tvReportTopLogin.setBackgroundResource(R.drawable.bg_simple_dark);
            tvReportTopMap.setBackgroundResource(R.drawable.bg_simple_dark);
        rlSpinnerBgActivity.setVisibility(View.VISIBLE);
            tvReportDetail.setText("Activity Report");
    }
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_report_top_route)
    public void onClickReportTopRoute(View view){
        tvReportTopActivity.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopRoute.setBackgroundResource(R.drawable.bg_simple_aceent);
        tvReportTopLogin.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopMap.setBackgroundResource(R.drawable.bg_simple_dark);
        rlSpinnerBgActivity.setVisibility(View.GONE);
        tvReportDetail.setText("Route Report");
    }
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_report_top_login)
    public void onClickReportTopLogin(View view){
        tvReportTopActivity.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopRoute.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopLogin.setBackgroundResource(R.drawable.bg_simple_aceent);
        tvReportTopMap.setBackgroundResource(R.drawable.bg_simple_dark);
        rlSpinnerBgActivity.setVisibility(View.GONE);
        tvReportDetail.setText("Attendance Report");
    }
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tv_report_top_map)
    public void onClickReportTopMap(View view){
        tvReportTopActivity.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopRoute.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopLogin.setBackgroundResource(R.drawable.bg_simple_dark);
        tvReportTopMap.setBackgroundResource(R.drawable.bg_simple_aceent);
        rlSpinnerBgActivity.setVisibility(View.GONE);
        tvReportDetail.setText("Map");
    }
    @OnClick(R.id.tv_report_date_from)
    public void dateFromClicked(View view){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String strCurrentDate = DateUtils.getTodayDate();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
                String strMyDate = sdf.format(myCalendar.getTime());
                if (strMyDate.compareTo(strCurrentDate)<0) {
                    tvReportDateFrom.setText(strMyDate);
                }
                else {
                    CustomToast.toastMid(tContext, Constant.DATE_TOAST);
                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.tv_report_date_to)
    public void dateToClicked(View view){
        if (!tvReportDateFrom.getText().toString().equals("")) {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    String strCurrentDate = DateUtils.getTodayDate();
                    String strDateTo;
                    String strDateFrom = tvReportDateFrom.getText().toString();
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
                    strDateTo = sdf.format(myCalendar.getTime());
                    if (strDateTo.compareTo(strDateFrom) > 0 && strDateTo.compareTo(strCurrentDate)<=0 ) {
                        tvReportDateTO.setText(strDateTo);
                    }
                    else {
                        CustomToast.toastMid(tContext, Constant.DATE_TOAST);
                    }
                }
            };
            new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else {
            CustomToast.toastMid(tContext, "Enter the From Date first...");
        }
    }
}
