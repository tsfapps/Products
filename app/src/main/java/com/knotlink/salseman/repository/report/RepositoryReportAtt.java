package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportColdCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryReportAtt {
    private List<ModelReportAttendance> tModels;
    private MutableLiveData<List<ModelReportAttendance>> tLiveData = new MutableLiveData<>();

    public RepositoryReportAtt(){

    }
    public MutableLiveData<List<ModelReportAttendance>> getCustomerLiveData(String strUserId, String strDateFrom, String strDateTo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelReportAttendance>> call = api.viewReportAttendance(strUserId, "Attendance", strDateFrom, strDateTo);
        call.enqueue(new Callback<List<ModelReportAttendance>>() {
            @Override
            public void onResponse(Call<List<ModelReportAttendance>> call, Response<List<ModelReportAttendance>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelReportAttendance>> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
