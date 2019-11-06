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
import com.knotlink.salseman.adapter.report.AdapterReportMeeting;
import com.knotlink.salseman.databinding.ReportMeetingBinding;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.report.ViewModelMeetingReport;

import java.util.List;

import butterknife.ButterKnife;

public class ReportMeeting extends Fragment{

    private AdapterReportMeeting tAdapterReportMeeting;
    private ViewModelMeetingReport tViewModels;
    private ReportMeetingBinding tBinding;

    private ProgressBar pbReportMeeting;
    private String strUserId;
    private String dateFrom;
    private String dateTo;
    private String strUserType;
    private String strSelectedUserId;


    public static ReportMeeting newInstance(String dateFrom, String dateTo, String strUserType, String strSelectedUserId) {

        ReportMeeting fragment = new ReportMeeting();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.report_meeting, container, false);
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

        tViewModels = ViewModelProviders.of(this).get(ViewModelMeetingReport.class);
        SetTitle.tbTitle(" Meeting Report", getActivity());
        tAdapterReportMeeting = new AdapterReportMeeting();
        RecyclerView rvReportMeeting = tBinding.rvReportMeeting;
        pbReportMeeting = tBinding.pbReportMeeting;
        rvReportMeeting.setLayoutManager(new LinearLayoutManager(tContext));
        rvReportMeeting.setItemAnimator(new DefaultItemAnimator());
        rvReportMeeting.setNestedScrollingEnabled(false);
        rvReportMeeting.setAdapter(tAdapterReportMeeting);
        getMeetingReport();
    }
    private void getMeetingReport(){
        tViewModels.getMeetingReport(strUserId, dateFrom, dateTo)
                .observe(this, new Observer<List<ModelReportMeeting>>() {
            @Override
            public void onChanged(@Nullable List<ModelReportMeeting> tModelReportMeeting) {
                pbReportMeeting.setVisibility(View.GONE);
                tAdapterReportMeeting.settModels(tModelReportMeeting);


            }
        });
    }

}
