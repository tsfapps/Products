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
import com.knotlink.salseman.adapter.report.AdapterReportAttendance;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportAttendance extends Fragment {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportAttendance tAdapterReportAttendance;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportAttendance> tModelReportAttendance;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;

    private String dateFrom;
    private String dateTo;


    public static ReportAttendance newInstance(String dateFom, String dateTo) {

        ReportAttendance fragment = new ReportAttendance();
        fragment.dateFrom = dateFom;
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
        SetTitle.tbTitle(" Attendance Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        callApiAttendance();
    }
    private void callApiAttendance(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportAttendance>> call = api.viewReportAttendance(strUserId,"Attendance", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportAttendance>>() {
            @Override
            public void onResponse(Call<List<ModelReportAttendance>> call, Response<List<ModelReportAttendance>> response) {
                tModelReportAttendance =response.body();
                tAdapterReportAttendance = new AdapterReportAttendance(tModelReportAttendance, tContext);
                rvReportAll.setAdapter(tAdapterReportAttendance);
            }

            @Override
            public void onFailure(Call<List<ModelReportAttendance>> call, Throwable t) {

            }


        });

    }

}
