package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterRoute;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragRoute extends Fragment {
    @BindView(R.id.rv_route)
    protected RecyclerView rvRoute;
    @BindView(R.id.tv_route_routeName)
    protected TextView tvRouteName;
     @BindView(R.id.tv_route_presentDay)
    protected TextView tvRoutePresnetDay;
    private Context tContext;
    private FragmentManager tFragmentManager;
    private SharedPrefManager tSharedPrefManager;
    private List<ModelShopList> tModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_route, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tvRoutePresnetDay.setText(DateUtils.getPresentDay());
        callApi();
        SetTitle.tbTitle("Shop List", getActivity());
        initRvRoute();
    }

    private void initRvRoute(){
        RecyclerView.LayoutManager tManager = new LinearLayoutManager(tContext);
        rvRoute.setLayoutManager(tManager);

    }

    private void callApi(){

        String strUserId = tSharedPrefManager.getUserId();
        String strPresentDay = DateUtils.getPresentDay();
        CustomLog.d(Constant.TAG, "\nUser ID : "+ strUserId+"\nPresent Day : "+strPresentDay);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelShopList>> call = api.getShopDetail(strUserId, strPresentDay);
        call.enqueue(new Callback<List<ModelShopList>>() {
            @Override
            public void onResponse(Call<List<ModelShopList>> call, Response<List<ModelShopList>> response) {
                tModels = response.body();
                if (tModels.size()!=0) {
                    tvRouteName.setText(tModels.get(0).getRouteName());
                }
                AdapterRoute tAdapter = new AdapterRoute(tContext,tFragmentManager, tModels);
                rvRoute.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelShopList>> call, Throwable t) {
                CustomLog.e(Constant.TAG, "ShopList Not Responding : "+t);
            }
        });
    }
}
