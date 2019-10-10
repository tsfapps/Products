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
import com.knotlink.salseman.adapter.report.AdapterReportColdCall;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportColdCall extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportColdCall tAdapterReportColdCall;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportColdCall> tModelReportColdCall;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;
    @BindView(R.id.swrReportAll)
    protected SwipeRefreshLayout swrReportAll;
    @BindView(R.id.pbReportAll)
    protected ProgressBar pbReportAll;

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
        View view = inflater.inflate(R.layout.report_attendence, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle(" Cold Call Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        pbReportAll.setVisibility(View.VISIBLE);
        swrReportAll.setOnRefreshListener(this);
        callApiColdCall();
    }
    private  void callApiColdCall(){
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportColdCall>> call = api.viewReportColdCall(strUserId,"Cold Call", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportColdCall>>() {
            @Override
            public void onResponse(Call<List<ModelReportColdCall>> call, Response<List<ModelReportColdCall>> response) {
                tModelReportColdCall =response.body();
                pbReportAll.setVisibility(View.GONE);
                if (tModelReportColdCall.size()>0){
                tAdapterReportColdCall = new AdapterReportColdCall(tModelReportColdCall, tContext);
                rvReportAll.setAdapter(tAdapterReportColdCall);}
                else {
                    CustomDialog.showEmptyDialog(tContext);
                }

            }
            @Override
            public void onFailure(Call<List<ModelReportColdCall>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " ColdCall Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportAll.setRefreshing(false);
                callApiColdCall();
            }
        }, 2000);

    }
}
