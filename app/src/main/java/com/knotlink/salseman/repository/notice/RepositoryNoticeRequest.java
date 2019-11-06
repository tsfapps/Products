package com.knotlink.salseman.repository.notice;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.notice.ModelNoticeRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryNoticeRequest {

    private List<ModelNoticeRequest> tModels;
    private MutableLiveData<List<ModelNoticeRequest>> tLiveData = new MutableLiveData<>();

    public  RepositoryNoticeRequest() {
    }


    public MutableLiveData<List<ModelNoticeRequest>> getMutableLiveData(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeRequest>> call = api.getNoticeRequest(strUserId);
        call.enqueue(new Callback<List<ModelNoticeRequest>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeRequest>> call, Response<List<ModelNoticeRequest>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }
            @Override
            public void onFailure(Call<List<ModelNoticeRequest>> call, Throwable t) {
            }
        });
        return tLiveData;
    }
}
