package com.knotlink.salseman.fragment.report.route;

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
import com.knotlink.salseman.adapter.report.route.AdapterReportReceipt;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportReceipt;
import com.knotlink.salseman.model.report.route.ModelRouteReceipt;
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

public class ReportComplain extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportReceipt tAdapterReportReceipt;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportReceipt> tModelReportReceipt;
    @BindView(R.id.rvReportAll)
    protected RecyclerView rvReportAll;
    @BindView(R.id.swrReportAll)
    protected SwipeRefreshLayout swrReportAll;
    @BindView(R.id.pbReportAll)
    protected ProgressBar pbReportAll;

    private String dateFrom;
    private String dateTo;

    public static ReportComplain newInstance(String dateFrom, String dateTo) {

        ReportComplain fragment = new ReportComplain();
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
        SetTitle.tbTitle(" Complain Report", getActivity());
        pbReportAll.setVisibility(View.VISIBLE);
        swrReportAll.setOnRefreshListener(this);
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(tLayoutManager);
        callApiReceipt();
    }
    private  void callApiReceipt(){
        String strUserId = tSharedPrefManager.getUserId();

        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelRouteReceipt>> call = api.viewReportReceipt(strUserId,"Complaint", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelRouteReceipt>>() {
            @Override
            public void onResponse(Call<List<ModelRouteReceipt>> call, Response<List<ModelRouteReceipt>> response) {
               List<ModelRouteReceipt> tModels =response.body();
                pbReportAll.setVisibility(View.GONE);
                if (tModels.size()>0){
                tAdapterReportReceipt = new AdapterReportReceipt(tModels, tContext);
                rvReportAll.setAdapter(tAdapterReportReceipt);}
                else {
                    CustomDialog.showEmptyDialog(tContext);
                }
            }
            @Override
            public void onFailure(Call<List<ModelRouteReceipt>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Receipt Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportAll.setRefreshing(false);
                callApiReceipt();
            }
        }, 2000);

    }
}
