package com.knotlink.salseman.repository.notice;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
import com.knotlink.salseman.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryNoticeComplain {

    private List<ModelNoticeComplain> tModels;
    private MutableLiveData<List<ModelNoticeComplain>> mutableLiveData = new MutableLiveData<>();

    public RepositoryNoticeComplain() {
    }

    public MutableLiveData<List<ModelNoticeComplain>> getMutableLiveData(String strUserId) {

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeComplain>> call = api.getNoticeComplain(strUserId);
        call.enqueue(new Callback<List<ModelNoticeComplain>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeComplain>> call, Response<List<ModelNoticeComplain>> response) {
                   tModels = response.body();
                   mutableLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<List<ModelNoticeComplain>> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
