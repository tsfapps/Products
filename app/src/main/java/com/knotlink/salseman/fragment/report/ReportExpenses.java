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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.AdapterReportExpenses;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportExpenses;
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

public class ReportExpenses extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterReportExpenses tAdapterReportExpenses;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportExpenses> tModelReportExpenses;
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


    public static ReportExpenses newInstance(String dateFrom, String dateTo, String strUserType, String strSelectedUserId) {
        ReportExpenses fragment = new ReportExpenses();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_expense, container, false);
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
        SetTitle.tbTitle(" Expenses Report", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportAll.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        pbReportAll.setVisibility(View.VISIBLE);
        swrReportAll.setOnRefreshListener(this);
        callApiExpenses();
    }
    private  void callApiExpenses(){
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelReportExpenses>> call = api.viewReportExpenses(strUserId,"Expenses", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelReportExpenses>>() {
            @Override
            public void onResponse(Call<List<ModelReportExpenses>> call, Response<List<ModelReportExpenses>> response) {
                tModelReportExpenses =response.body();
                pbReportAll.setVisibility(View.GONE);
                if (tModelReportExpenses.size()>0){
                tAdapterReportExpenses = new AdapterReportExpenses(tModelReportExpenses, tContext);
                rvReportAll.setAdapter(tAdapterReportExpenses);}
                else {
                    CustomDialog.showEmptyDialog(tContext);
                }

            }
            @Override
            public void onFailure(Call<List<ModelReportExpenses>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Expenses Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportAll.setRefreshing(false);
                callApiExpenses();
            }
        }, 2000);

    }
}
