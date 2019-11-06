package com.knotlink.salseman.fragment.dashboard.route;

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
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterTaskCustomer;
import com.knotlink.salseman.adapter.route.AdapterRequestList;
import com.knotlink.salseman.databinding.FragRequestListBinding;
import com.knotlink.salseman.databinding.FragTaskBinding;
import com.knotlink.salseman.fragment.FragTaskProspect;
import com.knotlink.salseman.model.dash.route.ModelRequestList;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.ViewModelTaskCustomer;
import com.knotlink.salseman.viewModel.route.ViewModelRequestList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragRequestList extends Fragment{


    private AdapterRequestList tAdapterRequestList;
    private ViewModelRequestList tViewModelRequestList;
    private FragRequestListBinding tBinding;
    private FragmentManager tFragmentManager;

    private RecyclerView rvRequestList;
    private ProgressBar pbFragRequestList;

    private String strUserId;
    private String strShopId;
    private String strAttDate;
    public static FragRequestList newInstance(String strShopId, String strAttDate) {

        FragRequestList fragment = new FragRequestList();
        fragment.strShopId = strShopId;
        fragment.strAttDate = strAttDate;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_request_list, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        Context tContext = getContext();
        tFragmentManager = getFragmentManager();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        tViewModelRequestList = ViewModelProviders.of(this).get(ViewModelRequestList.class);

        //GPS Calling
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        String strLat = String.valueOf(tGpsTracker.latitude);
        String strLong = String.valueOf(tGpsTracker.longitude);
        SetTitle.tbTitle("Special Request", getActivity());

        pbFragRequestList = tBinding.pbFragRequestList;

        //Adapter Task Customer Calling
        tAdapterRequestList = new AdapterRequestList(strLat, strLong);
        rvRequestList = tBinding.rvRequestList;
        rvRequestList.setLayoutManager(new LinearLayoutManager(tContext));
        rvRequestList.setItemAnimator(new DefaultItemAnimator());
        rvRequestList.setNestedScrollingEnabled(false);
        rvRequestList.setAdapter(tAdapterRequestList);


            strUserId = tSharedPrefManager.getUserId();
           getAllRequestList();

    }
    private void getAllRequestList(){
       
        tViewModelRequestList.getAllTaskCustomer(strShopId,strAttDate).observe(this, new Observer<List<ModelRequestList>>() {
            @Override
            public void onChanged(@Nullable List<ModelRequestList> tModelRequestList) {
                pbFragRequestList.setVisibility(View.GONE);
                if (tModelRequestList.size()>0) {
                    tAdapterRequestList.settModels(tModelRequestList);
                    rvRequestList.setVisibility(View.VISIBLE);
                }else {
                    rvRequestList.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Request is there", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @OnClick(R.id.ivRequestAdd)
//    public void ivRequestAddClicked(){
//        tFragmentManager.beginTransaction().replace(R.id.container_main, FragRequestList.newInstance(tModels.get(i)
//                .getShopId(), strAttDate)).addToBackStack(null).commit();
//
//    }

}
