package com.knotlink.salseman.fragment.notifiy;

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
import com.knotlink.salseman.viewModel.NoticeComplainViewModel;
import com.knotlink.salseman.adapter.notify.AdapterNoticeComplain;
import com.knotlink.salseman.databinding.FragNoticeBinding;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
 import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CustomDialog;

import java.util.List;

public class FragNoticeComplain extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private String strUserId;
    private FragNoticeBinding tBinding;
    private AdapterNoticeComplain tAdapter;
    private NoticeComplainViewModel noticeComplainViewModel;
    private TextView tvRequest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_notice, container, false);
        View view = tBinding.getRoot();
        initFrag();
        return view;
    }

    private void initFrag() {
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        RecyclerView rvNoticeComplain = tBinding.rvNoticeComplain;
        rvNoticeComplain.setLayoutManager(new LinearLayoutManager(tContext));
        rvNoticeComplain.setItemAnimator(new DefaultItemAnimator());
        rvNoticeComplain.setNestedScrollingEnabled(false);
        noticeComplainViewModel = ViewModelProviders.of(this).get(NoticeComplainViewModel.class);
        tAdapter = new AdapterNoticeComplain();
        rvNoticeComplain.setAdapter(tAdapter);
        getAllComplain();
        tvRequest = tBinding.tvNoticeComplainRequest;
        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, new FragNoticeRequest()).addToBackStack(null).commit();
            }
        });
    }

    private void getAllComplain(){
        noticeComplainViewModel.getAllNotice(strUserId).observe(this, new Observer<List<ModelNoticeComplain>>() {
            @Override
            public void onChanged(@Nullable List<ModelNoticeComplain> modelNoticeComplains) {
                if (modelNoticeComplains.size()>0) {
                    tAdapter.settModels(modelNoticeComplains);
                }else {
                    CustomDialog.showEmptyTitle(tContext, "No new notification");

                }
            }
        });
    }


}
