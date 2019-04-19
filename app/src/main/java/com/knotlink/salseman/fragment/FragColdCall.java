package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelColdCall;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragColdCall extends Fragment {


    private ModelColdCall tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    @BindView(R.id.et_cold_orgName)
    protected EditText etColdOrgName;
     @BindView(R.id.et_cold_contactName)
    protected EditText etColdContactName;
     @BindView(R.id.et_cold_contactNumber)
    protected EditText etColdContactNumber;
     @BindView(R.id.et_cold_address)
    protected EditText etColdAddress;
     @BindView(R.id.et_cold_email)
    protected EditText etColdEmail;
     @BindView(R.id.et_cold_remarks)
    protected EditText etColdRemarks;
     @BindView(R.id.btn_cold_submit)
    protected Button btnColdSubmit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cold_call, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Cold Call", getActivity());
    }
    @OnClick(R.id.btn_cold_submit)
    public void coldSubmit(View view){
        callApi();
    }

    private void callApi() {

        String strUserId = tSharedPrefManager.getUserId();
        String strOrgName = etColdOrgName.getText().toString().trim();
        String strContactName = etColdContactName.getText().toString().trim();
        String strContactNumber = etColdContactNumber.getText().toString().trim();
        String strAddress = etColdAddress.getText().toString().trim();
        String strEmail = etColdEmail.getText().toString().trim();
        String strRemarks = etColdRemarks.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelColdCall> call = api.uploadColdCall(strUserId, strOrgName, strContactName, strContactNumber, strAddress, strEmail, strRemarks);
        call.enqueue(new Callback<ModelColdCall>() {
            @Override
            public void onResponse(Call<ModelColdCall> call, Response<ModelColdCall> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(tContext, tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragColdCall.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
                }
                else {
                    CustomToast.toastTop(tContext, tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelColdCall> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding ColdCall : "+t);
            }
        });
    }
}