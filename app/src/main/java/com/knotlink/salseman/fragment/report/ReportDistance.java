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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportAttendance;
import com.knotlink.salseman.adapter.report.AdapterReportDistance;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportDistance;
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

public class ReportDistance extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportDistance tAdapterReportDistance;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportDistance> tModelReportDistance;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;
    @BindView(R.id.swrReportAll)
    protected SwipeRefreshLayout swrReportAll;
    @BindView(R.id.pbReportAll)
    protected ProgressBar pbReportAll;

    private String dateFrom;
    private String dateTo;

    public static ReportDistance newInstance(String dateFrom, String dateTo) {

        ReportDistance fragment = new ReportDistance();
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
        SetTitle.tbTitle(" Distance Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        pbReportAll.setVisibility(View.VISIBLE);
        swrReportAll.setOnRefreshListener(this);
        callApiDistance();
    }
    private  void callApiDistance(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportDistance>> call = api.viewReportDistance(strUserId,"Distance", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportDistance>>() {
            @Override
            public void onResponse(Call<List<ModelReportDistance>> call, Response<List<ModelReportDistance>> response) {
                tModelReportDistance =response.body();
                pbReportAll.setVisibility(View.GONE);
                tAdapterReportDistance = new AdapterReportDistance(tModelReportDistance, tContext);
                rvReportAll.setAdapter(tAdapterReportDistance);
            }
            @Override
            public void onFailure(Call<List<ModelReportDistance>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Distance Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportAll.setRefreshing(false);
                callApiDistance();
            }
        }, 2000);

    }
}
