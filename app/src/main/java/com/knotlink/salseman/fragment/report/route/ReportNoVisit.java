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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.report.route.AdapterRouteNoActivity;
import com.knotlink.salseman.adapter.report.route.AdapterRouteNoVisit;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.route.ModelRouteNoActivity;
import com.knotlink.salseman.model.report.route.ModelRouteNoVisit;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportNoVisit extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
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

    private String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    private String dateFrom;
    private String dateTo;
    private String shopId;
    private String strShopName;

    public static ReportNoVisit newInstance(String dateFrom, String dateTo, String shopId, String strShopName, String strUserType, String strSelectedUserId) {

        ReportNoVisit fragment = new ReportNoVisit();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        fragment.shopId = shopId;
        fragment.strShopName = strShopName;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
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
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle(" No Visit Report", getActivity());
        pbReportOrder.setVisibility(View.VISIBLE);
        swrReportOrder.setOnRefreshListener(this);
        tvReportOrderFromDate.setText(dateFrom);
        tvReportOrderToDate.setText(dateTo);
        rvReportOrder.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        Api api = ApiClients.getApiClients().create(Api.class);

        Call<List<ModelRouteNoVisit>> call = api.viewReportNoVisit(strUserId,"No Visit", dateFrom, dateTo);
call.enqueue(new Callback<List<ModelRouteNoVisit>>() {
    @Override
    public void onResponse(Call<List<ModelRouteNoVisit>> call, Response<List<ModelRouteNoVisit>> response) {
        List<ModelRouteNoVisit> tModels =response.body();
        pbReportOrder.setVisibility(View.GONE);
        if (tModels.size()>0){
            AdapterRouteNoVisit tAdapter = new AdapterRouteNoVisit(tContext, tModels);
            rvReportOrder.setAdapter(tAdapter);}
        else {
            CustomDialog.showEmptyDialog(tContext);
        }
    }

    @Override
    public void onFailure(Call<List<ModelRouteNoVisit>> call, Throwable t) {

    }
});    }
    private  void callApiShopRequest(){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRouteNoVisit>> call = api.viewReportShopNoVisit(shopId,"No Visit", dateFrom, dateTo);
        call.enqueue(new Callback<List<ModelRouteNoVisit>>() {
    @Override
    public void onResponse(Call<List<ModelRouteNoVisit>> call, Response<List<ModelRouteNoVisit>> response) {
        List<ModelRouteNoVisit> tModels =response.body();
        pbReportOrder.setVisibility(View.GONE);
        if (tModels.size()>0){
            AdapterRouteNoVisit tAdapter = new AdapterRouteNoVisit(tContext, tModels);
            rvReportOrder.setAdapter(tAdapter);}
        else {
            CustomDialog.showEmptyDialog(tContext);
        }
    }

    @Override
    public void onFailure(Call<List<ModelRouteNoVisit>> call, Throwable t) {

    }
});    }

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
