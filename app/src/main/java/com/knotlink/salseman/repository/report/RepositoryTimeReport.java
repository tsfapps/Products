package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelTimeReport;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryTimeReport {
    private List<ModelTimeReport> tModels;
    private MutableLiveData<List<ModelTimeReport>> tLiveData = new MutableLiveData<>();

    public RepositoryTimeReport(){

    }
    public MutableLiveData<List<ModelTimeReport>> getCustomerLiveData(String strUserId, String strDate){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTimeReport>> call = api.timeReport(strUserId,  strDate);
        call.enqueue(new Callback<List<ModelTimeReport>>() {
            @Override
            public void onResponse(Call<List<ModelTimeReport>> call, Response<List<ModelTimeReport>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelTimeReport>> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
