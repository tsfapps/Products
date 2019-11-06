package com.knotlink.salseman.repository.route;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelShopCounter;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryShopCounter {

    private ModelShopCounter tModels;
    private MutableLiveData<ModelShopCounter> tLiveData = new MutableLiveData<>();

    public RepositoryShopCounter() {
    }

    public MutableLiveData<ModelShopCounter> getCustomerLiveData( String strUserId, String strAttDate){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelShopCounter> call = api.shopCountter(strUserId, strAttDate);
        call.enqueue(new Callback<ModelShopCounter>() {
            @Override
            public void onResponse(Call<ModelShopCounter> call, Response<ModelShopCounter> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<ModelShopCounter> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
