package com.knotlink.salseman.fragment.notifiy;

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
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.viewModel.NoticeRequestViewModel;
import com.knotlink.salseman.adapter.notify.AdapterNoticeRequest;
import com.knotlink.salseman.databinding.FragNoticeRequestBinding;
import com.knotlink.salseman.model.notice.ModelNoticeRequest;
import com.knotlink.salseman.utils.CustomDialog;

import java.util.List;

public class FragNoticeRequest extends Fragment {


    private FragNoticeRequestBinding tBinding;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private NoticeRequestViewModel tViewModelRequest;
    private AdapterNoticeRequest tAdapterRequest;
    private TextView tvNoticeRequestComplain;
    private String strUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_notice_request, container, false);
        View view = tBinding.getRoot();
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        RecyclerView rvNoticeRequest = tBinding.rvNoticeRequest;
        rvNoticeRequest.setLayoutManager(new LinearLayoutManager(tContext));
        rvNoticeRequest.setItemAnimator(new DefaultItemAnimator());
        rvNoticeRequest.setNestedScrollingEnabled(false);
        tViewModelRequest = ViewModelProviders.of(this).get(NoticeRequestViewModel.class);
        tAdapterRequest = new AdapterNoticeRequest();
        rvNoticeRequest.setAdapter(tAdapterRequest);
        getAllRequest();
        tvNoticeRequestComplain = tBinding.tvNoticeRequestComplain;
        tvNoticeRequestComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, new FragNoticeComplain()).addToBackStack(null).commit();
            }
        });
    }
    private void getAllRequest(){
        tViewModelRequest.getAllRequest(strUserId).observe(this, new Observer<List<ModelNoticeRequest>>() {
            @Override
            public void onChanged(@Nullable List<ModelNoticeRequest> modelNoticeRequests) {

                if (modelNoticeRequests.size()>0) {
                    tAdapterRequest.setRequest(modelNoticeRequests);
                }else {
                    CustomDialog.showEmptyTitle(tContext, "No new notification");

                }
            }
        });
    }


}
