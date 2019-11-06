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
import com.knotlink.salseman.adapter.notice.AdapterNoticeCheque;
import com.knotlink.salseman.adapter.notice.AdapterNoticeComplain;
import com.knotlink.salseman.databinding.FragNoticeChequeBinding;
import com.knotlink.salseman.model.notice.ModelNoticeCheque;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.notice.NoticeComplainViewModel;
import com.knotlink.salseman.viewModel.notice.ViewModelNoticeCheque;

import java.util.List;

public class FragNoticeCheque extends Fragment {
    private Context tContext;
    private String strUserId;
    private FragNoticeChequeBinding tBinding;
    private AdapterNoticeCheque tAdapter;
    private ViewModelNoticeCheque tViewModel;

    private String strAttDate;
    public static FragNoticeCheque newInstance(String strAttDate) {

        FragNoticeCheque fragment = new FragNoticeCheque();
        fragment.strAttDate = strAttDate;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_notice_cheque, container, false);
        View view = tBinding.getRoot();
        initFrag();
        return view;
    }

    private void initFrag() {
        tContext = getContext();
        SetTitle.tbTitle("Cheque/NEFT Notification", getActivity());
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        RecyclerView rvNoticeCheque = tBinding.rvNoticeCheque;
        rvNoticeCheque.setLayoutManager(new LinearLayoutManager(tContext));
        rvNoticeCheque.setItemAnimator(new DefaultItemAnimator());
        rvNoticeCheque.setNestedScrollingEnabled(false);
        tViewModel = ViewModelProviders.of(this).get(ViewModelNoticeCheque.class);
        tAdapter = new AdapterNoticeCheque();
        rvNoticeCheque.setAdapter(tAdapter);
        getAllCheque();
        TextView tvRequest = tBinding.tvNoticeChequeRequest;
        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeRequest.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });

        TextView tvNoticeComplainReturn = tBinding.tvNoticeChequeReturn;
        tvNoticeComplainReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeReturn.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });

        TextView tvNoticeChequeComplain = tBinding.tvNoticeChequeComplain;
        tvNoticeChequeComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragNoticeComplain.newInstance(strAttDate)).addToBackStack(null).commit();
            }
        });
    }

    private void getAllCheque(){
        tViewModel.getNoticeCheque(strUserId, strAttDate).observe(this, new Observer<List<ModelNoticeCheque>>() {
            @Override
            public void onChanged(@Nullable List<ModelNoticeCheque> tModelNoticeCheque) {
                if (tModelNoticeCheque.size()>0) {
                    tAdapter.settModels(tModelNoticeCheque);
                }else {
                    CustomDialog.showEmptyTitle(tContext, "No new notification");

                }
            }
        });
    }


}
