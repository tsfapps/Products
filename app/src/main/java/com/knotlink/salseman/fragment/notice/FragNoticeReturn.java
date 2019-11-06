package com.knotlink.salseman.fragment.notice;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.knotlink.salseman.adapter.notice.AdapterNoticeReturn;
import com.knotlink.salseman.databinding.FragNoticeReturnBinding;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.notice.ViewModelNoticeReturn;

import java.util.List;

public class FragNoticeReturn extends Fragment {
    private Context tContext;
    private String strUserId;
    private FragNoticeReturnBinding tBinding;
    private AdapterNoticeReturn tAdapter;
    private ViewModelNoticeReturn tViewModelNoticeReturn;

    private String strAttDate;
    public static FragNoticeReturn newInstance(String strAttDate) {
        FragNoticeReturn fragment = new FragNoticeReturn();
        fragment.strAttDate = strAttDate;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_notice_return, container, false);
        View view = tBinding.getRoot();
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        SetTitle.tbTitle("Return Notification", getActivity());
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        RecyclerView rvNoticeRequest = tBinding.rvNoticeRequest;
        rvNoticeRequest.setLayoutManager(new LinearLayoutManager(tContext));
        rvNoticeRequest.setItemAnimator(new DefaultItemAnimator());
        rvNoticeRequest.setNestedScrollingEnabled(false);
        tViewModelNoticeReturn = ViewModelProviders.of(this).get(ViewModelNoticeReturn.class);
        tAdapter = new AdapterNoticeReturn();
        rvNoticeRequest.setAdapter(tAdapter);
        getAllReturn();
        TextView tvRequest = tBinding.tvNoticeReturnRequest;
        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeRequest.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });
        TextView tvComplain = tBinding.tvNoticeReturnComplain;
        tvComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeComplain.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });

        TextView tvNoticeReturnCheque = tBinding.tvNoticeReturnCheque;
        tvNoticeReturnCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeCheque.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });
    }

    private void getAllReturn(){
        tViewModelNoticeReturn.getNoticeReturn(strUserId, strAttDate).observe(this, new Observer<List<ModelNoticeReturn>>() {
            @Override
            public void onChanged(@Nullable List<ModelNoticeReturn> tModelNoticeReturn) {
                if (tModelNoticeReturn.size()>0) {
                    tAdapter.settModels(tModelNoticeReturn);
                }else {
                    CustomDialog.showEmptyTitle(tContext, "No new notification");

                }
            }
        });
    }


}
