package com.knotlink.salseman;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.ModelActiveStatus;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.GetImei;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingletonStatus {


    private Context tContext;
    private Activity tActivity;
    private String strUserId;

    public SingletonStatus(Context tContext, Activity tActivity, String strUserId) {
        this.tContext = tContext;
        this.tActivity = tActivity;
        this.strUserId = strUserId;
    }

    private ModelActiveStatus tModels;

    private String strDate;
    public void checkStatusApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelActiveStatus> call = api.activeStatus(GetImei.getDeviceIMEI(tContext, tActivity), strUserId);
        call.enqueue(new Callback<ModelActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ModelActiveStatus> call, @NonNull Response<ModelActiveStatus> response) {
                 tModels = response.body();
                 strDate = tModels.getDate();
                 setStrStatusDate(strDate);
                Log.d(Constant.TAG, "Status Date : "+strDate);
                getStrStatusDate();
            }
            @Override
            public void onFailure(Call<ModelActiveStatus> call, Throwable t) {
            }
        });
    }



    public void setStrStatusDate(String strDate) {
        this.strDate = strDate;
    }
    public String getStrStatusDate(){
        Log.d(Constant.TAG, "Status Date Return: "+strDate);
        return strDate;
    }


    public String getStausTime(){
        return tModels.getTime();
    }
    public String getStatusAtt(){
        return tModels.getAttendanceStatus();
    }
    public String getStatusActive(){
        return tModels.getActiveStatus();
    }


}
