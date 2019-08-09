package com.knotlink.salseman.fragment.report;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportAttendance;
import com.knotlink.salseman.adapter.report.AdapterReportMeeting;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportMeeting extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportMeeting tAdapterReportMeeting;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportMeeting> tModelReportMeeting;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;
    @BindView(R.id.swrReportAll)
    protected SwipeRefreshLayout swrReportAll;
    @BindView(R.id.pbReportAll)
    protected ProgressBar pbReportAll;

    private String dateFrom;
    private String dateTo;

    public static ReportMeeting newInstance(String dateFrom, String dateTo) {

        ReportMeeting fragment = new ReportMeeting();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_attendence, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle(" Meeting Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        pbReportAll.setVisibility(View.VISIBLE);
        swrReportAll.setOnRefreshListener(this);
        callApiMeeting();
    }
    private  void callApiMeeting(){
        String strUserId = tSharedPrefManager.getUserId();

        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportMeeting>> call = api.viewReportMeeting(strUserId,"Meeting", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportMeeting>>() {
            @Override
            public void onResponse(Call<List<ModelReportMeeting>> call, Response<List<ModelReportMeeting>> response) {
                tModelReportMeeting =response.body();
                pbReportAll.setVisibility(View.GONE);
                tAdapterReportMeeting = new AdapterReportMeeting(tModelReportMeeting, tContext);
                rvReportAll.setAdapter(tAdapterReportMeeting);
            }
            @Override
            public void onFailure(Call<List<ModelReportMeeting>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Meeting Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportAll.setRefreshing(false);
                callApiMeeting();
            }
        }, 2000);

    }
}
