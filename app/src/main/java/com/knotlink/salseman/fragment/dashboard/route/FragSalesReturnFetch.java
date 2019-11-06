package com.knotlink.salseman.fragment.dashboard.route;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.route.AdapterSalesReturnFetch;
import com.knotlink.salseman.databinding.FragSalesReturnFetchBinding;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnShopFetch;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.route.ViewModelSalesRetunFetch;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragSalesReturnFetch extends Fragment {

    private FragmentManager tFragmentManager;
    private AdapterSalesReturnFetch tAdapterSalesReturnFetch;
    private Context tContext;
    private ViewModelSalesRetunFetch tViewModels;
    private FragSalesReturnFetchBinding tBinding;


    private ProgressBar pbSalesReturnFetch;
    private TextView tvSalesReturnFetchShop;


    private String strUserId;

    private List<ModelShopList> tModels;
    private int i;
    private String strUserType;
    private String strSelectedUserId;


    public static FragSalesReturnFetch newInstance(List<ModelShopList> tModels, int i, String strUserType, String strSelectedUserId) {

        FragSalesReturnFetch fragment = new FragSalesReturnFetch();

        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_sales_return_fetch, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }


    private void initFrag(){
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("3")||strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle("All Sales Return", getActivity());
        tViewModels = ViewModelProviders.of(this).get(ViewModelSalesRetunFetch.class);
        RecyclerView rvSalesReturnFetch = tBinding.rvSalesReturnFetch;
        pbSalesReturnFetch = tBinding.pbSalesReturnFetch;
        tvSalesReturnFetchShop = tBinding.tvSalesReturnFetchShop;
        tvSalesReturnFetchShop.setText(tModels.get(i).getShopName());
        rvSalesReturnFetch.setLayoutManager(new LinearLayoutManager(tContext));
        rvSalesReturnFetch.setNestedScrollingEnabled(false);
        rvSalesReturnFetch.setItemAnimator(new DefaultItemAnimator());
        tAdapterSalesReturnFetch = new AdapterSalesReturnFetch();
        rvSalesReturnFetch.setAdapter(tAdapterSalesReturnFetch);
        getSalesReturn();
    }
    private void getSalesReturn(){
        String strShopId = tModels.get(i).getShopId();
        tViewModels.salesReturnFetch(strUserId, strShopId).observe(this, new Observer<List<ModelSalesReturnShopFetch>>() {
            @Override
            public void onChanged(@Nullable List<ModelSalesReturnShopFetch> tModelSalesReturnShopFetch) {
                pbSalesReturnFetch.setVisibility(View.GONE);
                if (tModelSalesReturnShopFetch.size()>0) {
                    tAdapterSalesReturnFetch.settModels(tModelSalesReturnShopFetch);
                }else {
                    Toast.makeText(tContext, "There is no any sales return for this shop...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.ivSalesReturnFetchAdd)
    public void tvSalesReturnFetchAddClicked(){
        tFragmentManager.beginTransaction().replace(R.id.container_main,
                FragSalesReturn.newInstance(tModels, i, strUserType, strSelectedUserId)).addToBackStack(null).commit();

    }


}
