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

public class FragProfile extends Fragment {
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    @BindView(R.id.tv_profile_name)
    protected TextView tvProfileName;
    @BindView(R.id.tv_profile_vehicle_no)
    protected TextView tvProfileVehicleNo;
    @BindView(R.id.tv_profile_phone)
    protected TextView tvProfilePhone;
    @BindView(R.id.tv_profile_email)
    protected TextView tvProfileEmail;
    @BindView(R.id.tv_profile_adhar_no)
    protected TextView tvProfileAdharNo;
    @BindView(R.id.tv_profile_address)
    protected TextView tvProfileAddress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    public void initFrag() {
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("My Account", getActivity());
        setProfileData();
    }
    public void setProfileData(){
        tvProfileName.setText(tSharedPrefManager.getUserName());
        tvProfileVehicleNo.setText(tSharedPrefManager.getUserVehilceNo());
        tvProfilePhone.setText(tSharedPrefManager.getUserPhone());
        tvProfileEmail.setText(tSharedPrefManager.getUserEmail());
       // tvProfileUserType.setText(tSharedPrefManager.get);
        tvProfileAdharNo.setText(tSharedPrefManager.getUserAdhar());
        tvProfileAddress.setText(tSharedPrefManager.getUserAddress());
    }
}
