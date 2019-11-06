package com.knotlink.salseman.fragment.report;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportTime;
import com.knotlink.salseman.databinding.FragTimeReportBinding;
import com.knotlink.salseman.model.report.ModelTimeReport;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelTimeReport;

import java.util.List;

import butterknife.ButterKnife;

public class FragTimeReport extends Fragment {
    private AdapterReportTime tAdapterReportTime;
    private Context tContext;
    private ViewModelTimeReport tViewModels;
    private FragTimeReportBinding tBinding;


    private ProgressBar pbTimeReport;
    private TextView tvTimeReportDate;

    private String strUserId;
    private String strDate;
    private String strUserType;
    private String strSelectedUserId;

    public static FragTimeReport newInstance(String strDate, String strUserType, String strSelectedUserId) {
        FragTimeReport fragment = new FragTimeReport();
        fragment.strDate = strDate;
        fragment.strUserType = strUserType;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_time_report, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initFrag(){
        tContext = getContext();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle("Time Activity Report", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelTimeReport.class);
        RecyclerView rvReportLead = tBinding.rvReportLead;
        pbTimeReport = tBinding.pbTimeReport;
        tvTimeReportDate = tBinding.tvTimeReportDate;
        tvTimeReportDate.setText("Date : "+DateUtils.dateFormatDdMmmYyyy(strDate));
        rvReportLead.setLayoutManager(new LinearLayoutManager(tContext));
        rvReportLead.setNestedScrollingEnabled(false);
        rvReportLead.setItemAnimator(new DefaultItemAnimator());
        tAdapterReportTime = new AdapterReportTime();
        rvReportLead.setAdapter(tAdapterReportTime);
        getTimeReport();
    }

    private void getTimeReport(){
        tViewModels.getTimeReport(strUserId, strDate).observe(this, new Observer<List<ModelTimeReport>>() {
            @Override
            public void onChanged(@Nullable List<ModelTimeReport> tModelTimeReport) {
                pbTimeReport.setVisibility(View.GONE);
                tAdapterReportTime.settModels(tModelTimeReport);
            }
        });
    }



}
