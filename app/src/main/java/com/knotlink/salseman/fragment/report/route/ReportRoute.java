package com.knotlink.salseman.fragment.report.route;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportRoute extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private FragmentManager tFragmentManager;

    private String dateFrom;
    private String dateTo;

    public static ReportRoute newInstance(String dateFrom, String dateTo) {

        ReportRoute fragment = new ReportRoute();
        fragment.dateFrom = dateFrom;
        fragment.dateTo = dateTo;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frag_report_route, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }


    private void initFrag(){
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        SetTitle.tbTitle(" Route Report", getActivity());




    }
    @OnClick(R.id.ll_ReportNewOrder)
    public void reportNewOrder(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportNewOrder.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }

    @OnClick(R.id.ll_ReportReceipt)
    public void reportReceipt(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportReceipt.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportSpecialRequest)
    public void reportSpecialRequest(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportSpecialRequest.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_ReportComplain)
    public void reportComplain(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, ReportComplain.newInstance(dateFrom, dateTo)).addToBackStack(null).commit();
    }

}
