package com.knotlink.salseman.fragment.report;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportColdCall;
import com.knotlink.salseman.databinding.ReportColdCallBinding;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelReportCold;

import java.util.List;

import butterknife.ButterKnife;

public class ReportColdCall extends Fragment{

    private AdapterReportColdCall tAdapterReportColdCall;
    private ViewModelReportCold tViewModels;
    private ReportColdCallBinding tBinding;
    private ProgressBar pbReportCold;

    private String strUserId;
    private String dateFrom;
    private String dateTo;
    private String strUserType;
    private String strSelectedUserId;


    public static ReportColdCall newInstance(String dateFrom, String dateTo, String strUserType, String strSelectedUserId) {

        ReportColdCall fragment = new ReportColdCall();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.report_cold_call, container, false);
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
        SetTitle.tbTitle("Cold Call Report", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelReportCold.class);
        RecyclerView rvReportCold = tBinding.rvReportCold;
        pbReportCold = tBinding.pbReportCold;
        rvReportCold.setLayoutManager(new LinearLayoutManager(tContext));
        rvReportCold.setNestedScrollingEnabled(false);
        rvReportCold.setItemAnimator(new DefaultItemAnimator());
        tAdapterReportColdCall = new AdapterReportColdCall();
        rvReportCold.setAdapter(tAdapterReportColdCall);
        getColdReport();
    }

    private void getColdReport(){
        tViewModels.getColdReport(strUserId, dateFrom, dateTo).observe(this, new Observer<List<ModelReportColdCall>>() {
            @Override
            public void onChanged(@Nullable List<ModelReportColdCall> modelReportLeadGenerations) {
                pbReportCold.setVisibility(View.GONE);
                tAdapterReportColdCall.settModels(modelReportLeadGenerations);
            }
        });
    }


}
