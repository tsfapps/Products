package com.knotlink.salseman.fragment.dashboard.route;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.util.DataUtils;
import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.route.AdapterRoute;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragRouteBinding;
import com.knotlink.salseman.model.dash.route.ModelShopCounter;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.route.ViewModelShopCounter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragRoute extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Activity tActivity;
    @BindView(R.id.rv_route)
    protected RecyclerView rvRoute;
    @BindView(R.id.pbRouteList)
    protected ProgressBar pbRouteList;
    @BindView(R.id.tv_route_routeName)
    protected TextView tvRouteName;
     @BindView(R.id.tv_route_presentDay)
    protected TextView tvRoutePresnetDay;
     @BindView(R.id.swrFragRoute)
    protected SwipeRefreshLayout swrFragRoute;
    private Context tContext;
    private FragmentManager tFragmentManager;
    private List<ModelShopList> tModels;
    private ViewModelShopCounter tViewModel;
    private FragRouteBinding tBinding;


    private String strUserId;
    private String strUserType;
    private String strSelectedId;
    private String strPresentDay;
    private String strAttDate;
    public static FragRoute newInstance(String strAttDate, String strUserType, String strSelectedId, String strPresentDay) {

        FragRoute fragment = new FragRoute();
        fragment.strAttDate = strAttDate;
        fragment.strUserType = strUserType;
        fragment.strSelectedId = strSelectedId;
        fragment.strPresentDay = strPresentDay;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_route, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        tActivity = getActivity();
        tViewModel = ViewModelProviders.of(this).get(ViewModelShopCounter.class);
        tFragmentManager = getFragmentManager();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")) {
            strUserId = strSelectedId ;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();

        }
        tvRoutePresnetDay.setText(strPresentDay);
        pbRouteList.setVisibility(View.VISIBLE);
        swrFragRoute.setOnRefreshListener(this);
        callApi(strUserId);
        SetTitle.tbTitle("Vendor List", getActivity());
        initRvRoute();
        setShopCounter();
    }

    private void initRvRoute(){
        RecyclerView.LayoutManager tManager = new LinearLayoutManager(tContext);
        rvRoute.setLayoutManager(tManager);
    }

    private void callApi(final String strUserId){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelShopList>> call = api.getShopDetail(strAttDate, strUserId, strUserType, strPresentDay);
        call.enqueue(new Callback<List<ModelShopList>>() {
            @Override
            public void onResponse(Call<List<ModelShopList>> call, Response<List<ModelShopList>> response) {
                tModels = response.body();
                pbRouteList.setVisibility(View.GONE);
                if (tModels.size()!=0) {
                    tvRouteName.setText(tModels.get(0).getRouteName());
                }
                AdapterRoute tAdapter = new AdapterRoute(strAttDate, tActivity,tFragmentManager, tModels, strUserId, strUserType,strSelectedId);
                rvRoute.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelShopList>> call, Throwable t) {
                CustomLog.e(Constant.TAG, "ShopList Not Responding : "+t);
            }
        });
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swrFragRoute.setRefreshing(false);
                callApi(strUserId);
            }
        }, 2000);
    }

    private void setShopCounter(){
        tViewModel.getShopCounter(strUserId, strAttDate).observe(this, new Observer<ModelShopCounter>() {
            @Override
            public void onChanged(@Nullable ModelShopCounter modelShopCounter) {
                tBinding.setShopCounter(modelShopCounter);
            }
        });
    }

}
