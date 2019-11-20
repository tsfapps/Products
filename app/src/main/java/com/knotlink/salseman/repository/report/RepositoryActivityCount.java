package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelUserActivityCount;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryActivityCount {
    private ModelUserActivityCount tModel;
    private MutableLiveData<ModelUserActivityCount> tLiveData = new MutableLiveData<>();

    public RepositoryActivityCount(){

    }
    public MutableLiveData<ModelUserActivityCount> getCustomerLiveData(String strUserId, String strDateFrom, String strDateTo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelUserActivityCount> call = api.userActivityCount(strUserId, strDateFrom, strDateTo);
        call.enqueue(new Callback<ModelUserActivityCount>() {
            @Override
            public void onResponse(Call<ModelUserActivityCount> call, Response<ModelUserActivityCount> response) {
                tModel = response.body();
                tLiveData.setValue(tModel);
            }

            @Override
            public void onFailure(Call<ModelUserActivityCount> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
