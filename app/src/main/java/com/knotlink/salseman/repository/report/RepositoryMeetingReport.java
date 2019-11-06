package com.knotlink.salseman.repository.report;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.model.task.ModelTaskCustomer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryMeetingReport {
    private List<ModelReportMeeting> tModels;
    private MutableLiveData<List<ModelReportMeeting>> tLiveData = new MutableLiveData<>();

    public RepositoryMeetingReport(){

    }
    public MutableLiveData<List<ModelReportMeeting>> getCustomerLiveData(String strUserId, String strDateFrom, String strDateTo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelReportMeeting>> call = api.viewReportMeeting(strUserId, "Meeting", strDateFrom, strDateTo);
        call.enqueue(new Callback<List<ModelReportMeeting>>() {
            @Override
            public void onResponse(Call<List<ModelReportMeeting>> call, Response<List<ModelReportMeeting>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelReportMeeting>> call, Throwable t) {

            }
        });
        return tLiveData;
    }

}
