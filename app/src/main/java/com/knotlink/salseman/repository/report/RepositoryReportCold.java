package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryReportCold {
    private List<ModelReportColdCall> tModels;
    private MutableLiveData<List<ModelReportColdCall>> tLiveData = new MutableLiveData<>();

    public RepositoryReportCold(){

    }
    public MutableLiveData<List<ModelReportColdCall>> getCustomerLiveData(String strUserId, String strDateFrom, String strDateTo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelReportColdCall>> call = api.viewReportColdCall(strUserId, "Cold Call", strDateFrom, strDateTo);
        call.enqueue(new Callback<List<ModelReportColdCall>>() {
            @Override
            public void onResponse(Call<List<ModelReportColdCall>> call, Response<List<ModelReportColdCall>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelReportColdCall>> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
