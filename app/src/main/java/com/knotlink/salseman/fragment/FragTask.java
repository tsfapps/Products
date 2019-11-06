package com.knotlink.salseman.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterTaskCustomer;
import com.knotlink.salseman.databinding.FragTaskBinding;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.ViewModelTaskCustomer;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragTask extends Fragment{


    private AdapterTaskCustomer tAdapterTaskCustomer;
    private ViewModelTaskCustomer tViewModelCustomer;
    private FragTaskBinding tBinding;
    private FragmentManager tFragmentManager;

    private RecyclerView rvTask;
    private ProgressBar pbFragTask;

    private String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    public static FragTask newInstance(String strSelectedUserId, String strUserType) {

        FragTask fragment = new FragTask();
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_task, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        Context tContext = getContext();
        tFragmentManager = getFragmentManager();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        tViewModelCustomer = ViewModelProviders.of(this).get(ViewModelTaskCustomer.class);

        //GPS Calling
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        String strLat = String.valueOf(tGpsTracker.latitude);
        String strLong = String.valueOf(tGpsTracker.longitude);
        SetTitle.tbTitle("Task Assigned", getActivity());

        pbFragTask = tBinding.pbFragTask;
        TextView tvCustomerTaskProspect = tBinding.tvCustomerTaskProspect;
        TextView tvCustomerTaskCustomer = tBinding.tvCustomeTaskCustomer;

        //Adapter Task Customer Calling
        tAdapterTaskCustomer = new AdapterTaskCustomer(strLat, strLong);
        rvTask = tBinding.rvTask;
        rvTask.setLayoutManager(new LinearLayoutManager(tContext));
        rvTask.setItemAnimator(new DefaultItemAnimator());
        rvTask.setNestedScrollingEnabled(false);
        rvTask.setAdapter(tAdapterTaskCustomer);
        tvCustomerTaskCustomer.setEnabled(false);
        tvCustomerTaskProspect.setEnabled(true);

        if (strUserType.equalsIgnoreCase("0")||strUserType.equalsIgnoreCase("3")){
            strUserId = strSelectedUserId;
            getAllTaskCustomer();

        }else if(strUserType.equalsIgnoreCase("1")){
            strUserId = tSharedPrefManager.getUserId();
            getAllTaskCustomer();
        }else if (strUserType.equalsIgnoreCase("2")) {
            strUserId = tSharedPrefManager.getUserId();
            getAllTaskCustomer();
            tvCustomerTaskProspect.setVisibility(View.GONE);
            tvCustomerTaskCustomer.setEnabled(false);

        }
    }
    private void getAllTaskCustomer(){
       
        tViewModelCustomer.getAllTaskCustomer(strUserId).observe(this, new Observer<List<ModelTaskCustomer>>() {
            @Override
            public void onChanged(@Nullable List<ModelTaskCustomer> tModelTaskCustomer) {
                pbFragTask.setVisibility(View.GONE);
                if (tModelTaskCustomer.size()>0) {
                    tAdapterTaskCustomer.settModels(tModelTaskCustomer);
                    rvTask.setVisibility(View.VISIBLE);
                }else {
                    rvTask.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick(R.id.tvCustomerTaskProspect)
    public void tvProspectTaskProspectClicked(){
        tFragmentManager.beginTransaction().replace(R.id.container_main,
                FragTaskProspect.newInstance(strSelectedUserId, strUserType)).addToBackStack(null).commit();
    }


}
