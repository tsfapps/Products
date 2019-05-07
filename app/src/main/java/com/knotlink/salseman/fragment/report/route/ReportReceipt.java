package com.knotlink.salseman.fragment.report.route;

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
import com.knotlink.salseman.adapter.report.AdapterReportColdCall;
import com.knotlink.salseman.adapter.report.AdapterReportReceipt;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.model.report.ModelReportReceipt;
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

public class ReportReceipt extends Fragment {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportReceipt tAdapterReportReceipt;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportReceipt> tModelReportReceipt;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;

    private String dateFrom;
    private String dateTo;

    public static ReportReceipt newInstance(String dateFrom, String dateTo) {

        ReportReceipt fragment = new ReportReceipt();
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
        SetTitle.tbTitle(" Receipt Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        callApiReceipt();
    }
    private  void callApiReceipt(){
        String strUserId = tSharedPrefManager.getUserId();

        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportReceipt>> call = api.viewReportReceipt(strUserId,"Receipt", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportReceipt>>() {
            @Override
            public void onResponse(Call<List<ModelReportReceipt>> call, Response<List<ModelReportReceipt>> response) {
                tModelReportReceipt =response.body();
                tAdapterReportReceipt = new AdapterReportReceipt(tModelReportReceipt, tContext);
                rvReportAll.setAdapter(tAdapterReportReceipt);
            }
            @Override
            public void onFailure(Call<List<ModelReportReceipt>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Receipt Not Responding : "+t);

            }

        });
    }
}
