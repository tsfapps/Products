package com.knotlink.salseman.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterTaskProspect;
import com.knotlink.salseman.databinding.FragTaskProspectBinding;
import com.knotlink.salseman.model.task.ModelTaskProspect;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.ViewModelTaskProspect;

import java.util.List;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragTaskProspect extends Fragment{

    private AdapterTaskProspect  tAdapterTaskProspect;
    private ViewModelTaskProspect tViewModelProspect;
    private FragTaskProspectBinding tBinding;
    private FragmentManager tFragmentManager;

    private RecyclerView rvTaskProspect;

    private ProgressBar pbFragTask;
    private TextView tvProspectTaskCustomer;
    private TextView tvProspectTaskProspect;

    private String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    public static FragTaskProspect newInstance(String strSelectedUserId, String strUserType) {

        FragTaskProspect fragment = new FragTaskProspect();
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_task_prospect, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        Context tContext = getContext();
        tFragmentManager = getFragmentManager();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        tViewModelProspect = ViewModelProviders.of(this).get(ViewModelTaskProspect.class);

        FragmentManager tFragmentManager = getFragmentManager();

        //GPS Calling
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        String strLat = String.valueOf(tGpsTracker.latitude);
        String strLong = String.valueOf(tGpsTracker.longitude);
        SetTitle.tbTitle("Task Assigned", getActivity());

        pbFragTask = tBinding.pbFragTask;
        tvProspectTaskCustomer = tBinding.tvProspectTaskCustomer;
        tvProspectTaskProspect = tBinding.tvProspectTaskProspect;

        tvProspectTaskCustomer.setEnabled(true);
        tvProspectTaskProspect.setEnabled(false);


        //Adapter Task Prospect Calling
        tAdapterTaskProspect = new AdapterTaskProspect(strUserId, tFragmentManager, strLat, strLong, strUserType);
        rvTaskProspect = tBinding.rvTaskProspect;
        rvTaskProspect.setLayoutManager(new LinearLayoutManager(tContext));
        rvTaskProspect.setItemAnimator(new DefaultItemAnimator());
        rvTaskProspect.setNestedScrollingEnabled(false);
        rvTaskProspect.setAdapter(tAdapterTaskProspect);

        if (strUserType.equalsIgnoreCase("0")||strUserType.equalsIgnoreCase("3")){
            strUserId = strSelectedUserId;
            getAllTaskProspect();

        }else if(strUserType.equalsIgnoreCase("1")){
            strUserId = tSharedPrefManager.getUserId();
            getAllTaskProspect();
        }else if (strUserType.equalsIgnoreCase("2")) {
            strUserId = tSharedPrefManager.getUserId();
        }
    }



    private void getAllTaskProspect(){
        tViewModelProspect.getTaskProspect(strUserId).observe(this, new Observer<List<ModelTaskProspect>>() {
            @Override
            public void onChanged(@Nullable List<ModelTaskProspect> modelTaskProspects) {
                pbFragTask.setVisibility(View.GONE);
                if (modelTaskProspects.size()>0){
                    tAdapterTaskProspect.settModels(modelTaskProspects);
                    rvTaskProspect.setVisibility(View.VISIBLE);

                }else {
                    rvTaskProspect.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.tvProspectTaskCustomer)
    public void tvProspectTaskCustomerClicked(){
        tFragmentManager.beginTransaction().replace(R.id.container_main,
                FragTask.newInstance(strSelectedUserId, strUserType)).addToBackStack(null).commit();
    }



}
