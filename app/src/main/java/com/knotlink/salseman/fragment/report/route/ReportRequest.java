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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.route.AdapterRouteRequest;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportReceipt;
import com.knotlink.salseman.model.report.route.ModelRouteRequest;
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

public class ReportRequest extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager tLayoutManager;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    private List<ModelReportReceipt> tModelReportReceipt;
    @BindView(R.id.tvReportOrderFromDate)
    protected TextView tvReportOrderFromDate;
    @BindView(R.id.tvReportOrderToDate)
    protected TextView tvReportOrderToDate;
    @BindView(R.id.tvReportOrderShopName)
    protected TextView tvReportOrderShopName;
    @BindView(R.id.svReportOrder)
    protected SearchView svReportOrder;
    @BindView(R.id.rvReportOrder)
    protected RecyclerView rvReportOrder;
    @BindView(R.id.swrReportOrder)
    protected SwipeRefreshLayout swrReportOrder;
    @BindView(R.id.pbReportOrder)
    protected ProgressBar pbReportOrder;


    private String dateFrom;
    private String dateTo;
    private String shopId;
    private String strShopName;

    public static ReportRequest newInstance(String dateFrom, String dateTo, String shopId, String strShopName) {

        ReportRequest fragment = new ReportRequest();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.shopId = shopId;
        fragment.strShopName = strShopName;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_route_all, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Special Request Report", getActivity());
        pbReportOrder.setVisibility(View.VISIBLE);
        swrReportOrder.setOnRefreshListener(this);
        tvReportOrderFromDate.setText(dateFrom);
        tvReportOrderToDate.setText(dateTo);
        tLayoutManager = new LinearLayoutManager(tContext);
        rvReportOrder.setLayoutManager(tLayoutManager);
        if (shopId.equalsIgnoreCase("")) {
            callApiRequest();
            svReportOrder.setVisibility(View.VISIBLE);
            tvReportOrderShopName.setVisibility(View.GONE);

        }else {
            callApiShopRequest();
            tvReportOrderShopName.setVisibility(View.VISIBLE);
            tvReportOrderShopName.setText(strShopName);
            svReportOrder.setVisibility(View.GONE);
        }
    }
    private  void callApiRequest(){
        String strUserId = tSharedPrefManager.getUserId();

        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelRouteRequest>> call = api.viewReportRequest(strUserId,"Special Request", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelRouteRequest>>() {
            @Override
            public void onResponse(Call<List<ModelRouteRequest>> call, Response<List<ModelRouteRequest>> response) {
                List<ModelRouteRequest> tModels =response.body();
                pbReportOrder.setVisibility(View.GONE);
                if (tModels.size()>0){
                    AdapterRouteRequest tAdapter = new AdapterRouteRequest(tContext, tModels);
                    rvReportOrder.setAdapter(tAdapter);}
                else {
                    CustomDialog.showEmptyDialog(tContext);
                }
            }
            @Override
            public void onFailure(Call<List<ModelRouteRequest>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Receipt Not Responding : "+t);
            }
        });
    }
    private  void callApiShopRequest(){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRouteRequest>> call = api.viewReportShopRequest(shopId,"Special Request", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelRouteRequest>>() {
            @Override
            public void onResponse(Call<List<ModelRouteRequest>> call, Response<List<ModelRouteRequest>> response) {
                List<ModelRouteRequest> tModels =response.body();
                pbReportOrder.setVisibility(View.GONE);
                if (tModels.size()>0){
                    AdapterRouteRequest tAdapter = new AdapterRouteRequest(tContext, tModels);
                    rvReportOrder.setAdapter(tAdapter);}
                else {
                    CustomDialog.showEmptyDialog(tContext);
                }

            }
            @Override
            public void onFailure(Call<List<ModelRouteRequest>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Receipt Not Responding : "+t);

            }

        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrReportOrder.setRefreshing(false);
                if (shopId.equalsIgnoreCase("")) {
                    callApiRequest();
                    svReportOrder.setVisibility(View.VISIBLE);
                    tvReportOrderShopName.setVisibility(View.GONE);

                }else {
                    callApiShopRequest();
                    tvReportOrderShopName.setVisibility(View.VISIBLE);
                    tvReportOrderShopName.setText(strShopName);
                    svReportOrder.setVisibility(View.GONE);
                }            }
        }, 2000);

    }
}
