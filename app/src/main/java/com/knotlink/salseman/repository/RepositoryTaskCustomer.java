package com.knotlink.salseman.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryTaskCustomer {

    private List<ModelTaskCustomer> tModels;
    private MutableLiveData<List<ModelTaskCustomer>> tLiveData = new MutableLiveData<>();

    public RepositoryTaskCustomer() {
    }

    public MutableLiveData<List<ModelTaskCustomer>> getCustomerLiveData(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTaskCustomer>> call = api.assignedTask(strUserId);
        call.enqueue(new Callback<List<ModelTaskCustomer>>() {
            @Override
            public void onResponse(Call<List<ModelTaskCustomer>> call, Response<List<ModelTaskCustomer>> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelTaskCustomer>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
