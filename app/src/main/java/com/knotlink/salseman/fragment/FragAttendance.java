package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragAttendance extends Fragment {
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    @BindView(R.id.tv_att_user_name)
    protected TextView tvAttUserName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_attendance, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        SetTitle.tbTitle("Attendance Report", getActivity());
        tSharedPrefManager = new SharedPrefManager(tContext);
        tvAttUserName.setText(tSharedPrefManager.getUserName());
    }
}
