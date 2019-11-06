package com.knotlink.salseman.repository.route;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnFetch;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryOrderList {

    private List<ModelOrderList> tModels;
    private MutableLiveData<List<ModelOrderList>> tLiveData = new MutableLiveData<>();

    public RepositoryOrderList() {
    }

    public MutableLiveData<List<ModelOrderList>> getCustomerLiveData(String strAttDate, String strUserId, String strShopId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelOrderList>> call = api.orderShopList(strAttDate, strUserId, strShopId);
        call.enqueue(new Callback<List<ModelOrderList>>() {
            @Override
            public void onResponse(Call<List<ModelOrderList>> call, Response<List<ModelOrderList>> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelOrderList>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
