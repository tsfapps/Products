package com.knotlink.salseman.fragment.report;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ReportDistance extends Fragment {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportDistance tAdapterReportDistance;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportDistance> tModelReportDistance;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;

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
        callApiDistance();
    }
    private  void callApiDistance(){
        String strUserId = tSharedPrefManager.getUserId();
        Log.d(Constant.TAG, "Date From : "+dateFrom);
        Log.d(Constant.TAG, "Date To : "+dateTo);
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportDistance>> call = api.viewReportDistance(strUserId,"Distance", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportDistance>>() {
            @Override
            public void onResponse(Call<List<ModelReportDistance>> call, Response<List<ModelReportDistance>> response) {
                tModelReportDistance =response.body();
                Log.d(Constant.TAG, "Distance Response : "+response.message());
                Log.d(Constant.TAG, "Distance Response : "+response.errorBody());
                Log.d(Constant.TAG, "Distance Response : "+response.code());

                tAdapterReportDistance = new AdapterReportDistance(tModelReportDistance, tContext);
                rvReportAll.setAdapter(tAdapterReportDistance);
            }
            @Override
            public void onFailure(Call<List<ModelReportDistance>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Distance Not Responding : "+t);

            }

        });
    }

}
