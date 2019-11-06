package com.knotlink.salseman.fragment.report;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportAtt;
import com.knotlink.salseman.adapter.report.AdapterReportLeadGeneration;
import com.knotlink.salseman.databinding.ReportAttBinding;
import com.knotlink.salseman.databinding.ReportLeadBinding;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelLeadReport;
import com.knotlink.salseman.viewModel.report.ViewModelReportAtt;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportAttendance extends Fragment {

    private AdapterReportAtt tAdapterReportAtt;
    private ViewModelReportAtt tViewModels;
    private ReportAttBinding tBinding;


    private ProgressBar pbReportAtt;
    private String strUserId;
    private String dateFrom;
    private String dateTo;
    private String strUserType;
    private String strSelectedUserId;


    public static ReportAttendance newInstance(String dateFrom, String dateTo, String strUserType, String strSelectedUserId) {

        ReportAttendance fragment = new ReportAttendance();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         tBinding = DataBindingUtil.inflate(inflater, R.layout.report_att, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        Context tContext = getContext();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle("Attendance Report", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelReportAtt.class);
        RecyclerView rvReportAtt = tBinding.rvReportAtt;
        pbReportAtt = tBinding.pbReportAtt;
        rvReportAtt.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvReportAtt.setNestedScrollingEnabled(false);
        rvReportAtt.setItemAnimator(new DefaultItemAnimator());
        tAdapterReportAtt = new AdapterReportAtt();
        rvReportAtt.setAdapter(tAdapterReportAtt);
        getAttReport();
    }

    private void getAttReport(){
        tViewModels.getAttReport(strUserId, dateFrom, dateTo).observe(this, new Observer<List<ModelReportAttendance>>() {
            @Override
            public void onChanged(@Nullable List<ModelReportAttendance> tModelReportAttendance) {
                pbReportAtt.setVisibility(View.GONE);
                tAdapterReportAtt.settModels(tModelReportAttendance);
            }
        });
    }



}
