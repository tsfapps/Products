package com.knotlink.salseman.repository.route;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelRequestList;
import com.knotlink.salseman.model.task.ModelTaskCustomer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRequestList {

    private List<ModelRequestList> tModels;
    private MutableLiveData<List<ModelRequestList>> tLiveData = new MutableLiveData<>();

    public RepositoryRequestList() {
    }

    public MutableLiveData<List<ModelRequestList>> getCustomerLiveData(String strShopId, String strAttDate){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRequestList>> call = api.requestList(strShopId,strAttDate);
        call.enqueue(new Callback<List<ModelRequestList>>() {
            @Override
            public void onResponse(Call<List<ModelRequestList>> call, Response<List<ModelRequestList>> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelRequestList>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
