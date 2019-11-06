package com.knotlink.salseman.fragment.dashboard.route;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.route.AdapterOrderList;
import com.knotlink.salseman.databinding.FragOrderListBinding;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.route.ViewModelOrderList;

import java.util.List;

import butterknife.ButterKnife;

public class FragOrderList extends Fragment {

    private AdapterOrderList tAdapterOrderList;
    private Context tContext;
    private ViewModelOrderList tViewModels;
    private FragOrderListBinding tBinding;


    private ProgressBar pbOrderList;
    private TextView tvOrderListShopName;

    private String strUserId;
    private String strShopId;
    private String strShopName;
    private String strAttDate;
    private String strUserType;
    private String strSelectedUserId;


    public static FragOrderList newInstance( String strShopId,String strShopName, String strAttDate, String strUserType, String strSelectedUserId) {

        FragOrderList fragment = new FragOrderList();
        fragment.strShopId = strShopId;
        fragment.strShopName = strShopName;
        fragment.strAttDate = strAttDate;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_order_list, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        GPSTracker tGPSTracker = new GPSTracker(tContext);
        String strLat = String.valueOf(tGPSTracker.latitude);
        String strLong = String.valueOf(tGPSTracker.longitude);
        String strAddress = tGPSTracker.getAddressLine(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle(" All Orders", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelOrderList.class);
        RecyclerView rvOrderList = tBinding.rvOrderList;
        pbOrderList = tBinding.pbOrderList;
        tvOrderListShopName = tBinding.tvOrderListShopName;
        tvOrderListShopName.setText(strShopName);
        rvOrderList.setLayoutManager(new LinearLayoutManager(tContext));
        rvOrderList.setNestedScrollingEnabled(false);
        rvOrderList.setItemAnimator(new DefaultItemAnimator());
        tAdapterOrderList = new AdapterOrderList(strUserId, strLat, strLong, strAddress);
        rvOrderList.setAdapter(tAdapterOrderList);
        getOrderList();
    }

    private void getOrderList(){
        tViewModels.ordeList(strAttDate, strUserId, strShopId).observe(this, new Observer<List<ModelOrderList>>() {
            @Override
            public void onChanged(@Nullable List<ModelOrderList> modelOrderLists) {
                pbOrderList.setVisibility(View.GONE);
                tAdapterOrderList.settModels(modelOrderLists);
            }
        });
    }


}
