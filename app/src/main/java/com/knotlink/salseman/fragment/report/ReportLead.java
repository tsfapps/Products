package com.knotlink.salseman.fragment.report;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportLeadGeneration;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.ReportLeadBinding;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelLeadReport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportLead extends Fragment{

    private AdapterReportLeadGeneration tAdapterReportLeadGeneration;
    private Context tContext;
    private ViewModelLeadReport tViewModels;
    private ReportLeadBinding tBinding;


    private ProgressBar pbReportLead;

    private String strUserId;
    private String dateFrom;
    private String dateTo;
    private String strUserType;
    private String strSelectedUserId;


    public static ReportLead newInstance(String dateFrom, String dateTo, String strUserType, String strSelectedUserId) {

        ReportLead fragment = new ReportLead();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.report_lead, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle(" Lead Generation Report", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelLeadReport.class);
        RecyclerView rvReportLead = tBinding.rvReportLead;
        pbReportLead = tBinding.pbReportLead;
        rvReportLead.setLayoutManager(new LinearLayoutManager(tContext));
        rvReportLead.setNestedScrollingEnabled(false);
        rvReportLead.setItemAnimator(new DefaultItemAnimator());
        tAdapterReportLeadGeneration = new AdapterReportLeadGeneration();
        rvReportLead.setAdapter(tAdapterReportLeadGeneration);
        getLeadReport();
    }

    private void getLeadReport(){
        tViewModels.getLeadReport(strUserId, dateFrom, dateTo).observe(this, new Observer<List<ModelReportLeadGeneration>>() {
            @Override
            public void onChanged(@Nullable List<ModelReportLeadGeneration> modelReportLeadGenerations) {
                pbReportLead.setVisibility(View.GONE);
                tAdapterReportLeadGeneration.settModels(modelReportLeadGenerations);
            }
        });
    }

}
