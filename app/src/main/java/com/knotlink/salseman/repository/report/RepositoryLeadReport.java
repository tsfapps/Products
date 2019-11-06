package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelReportMeeting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryLeadReport {
    private List<ModelReportLeadGeneration> tModels;
    private MutableLiveData<List<ModelReportLeadGeneration>> tLiveData = new MutableLiveData<>();

    public RepositoryLeadReport(){

    }
    public MutableLiveData<List<ModelReportLeadGeneration>> getCustomerLiveData(String strUserId, String strDateFrom, String strDateTo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelReportLeadGeneration>> call = api.viewReportLead(strUserId, "Lead", strDateFrom, strDateTo);
        call.enqueue(new Callback<List<ModelReportLeadGeneration>>() {
            @Override
            public void onResponse(Call<List<ModelReportLeadGeneration>> call, Response<List<ModelReportLeadGeneration>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelReportLeadGeneration>> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
