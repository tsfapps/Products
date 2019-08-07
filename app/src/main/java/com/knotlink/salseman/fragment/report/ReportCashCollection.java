package com.knotlink.salseman.fragment.report;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportCashCollection;
import com.knotlink.salseman.adapter.report.AdapterReportMeeting;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportCashCollection;
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

public class ReportCashCollection extends Fragment {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportCashCollection tAdapterReportMeeting;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportCashCollection> tModelReportCash;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;

    private String dateFrom;
    private String dateTo;

    public static ReportCashCollection newInstance(String dateFrom, String dateTo) {

        ReportCashCollection fragment = new ReportCashCollection();
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
        callApiMeeting();
    }
    private  void callApiMeeting(){
        String strUserId = tSharedPrefManager.getUserId();

        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportCashCollection>> call = api.viewReportCash(strUserId,"Cash", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportCashCollection>>() {
            @Override
            public void onResponse(Call<List<ModelReportCashCollection>> call, Response<List<ModelReportCashCollection>> response) {
                tModelReportCash =response.body();
                tAdapterReportMeeting = new AdapterReportCashCollection(tModelReportCash, tContext);
                rvReportAll.setAdapter(tAdapterReportMeeting);
            }
            @Override
            public void onFailure(Call<List<ModelReportCashCollection>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Meeting Not Responding : "+t);

            }

        });
    }
}