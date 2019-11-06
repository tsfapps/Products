package com.knotlink.salseman.repository.route;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnShopFetch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositorySalesReturnFetch {

    private List<ModelSalesReturnShopFetch> tModels;
    private MutableLiveData<List<ModelSalesReturnShopFetch>> tLiveData = new MutableLiveData<>();

    public RepositorySalesReturnFetch() {
    }

    public MutableLiveData<List<ModelSalesReturnShopFetch>> getCustomerLiveData(String strUserId, String strShopId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesReturnShopFetch>> call = api.salesReturnShopFetch(strUserId, strShopId);
        call.enqueue(new Callback<List<ModelSalesReturnShopFetch>>() {
            @Override
            public void onResponse(Call<List<ModelSalesReturnShopFetch>> call, Response<List<ModelSalesReturnShopFetch>> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelSalesReturnShopFetch>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
