package com.knotlink.salseman.repository;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.task.ModelTaskProspect;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryTaskProspect {
    private List<ModelTaskProspect> tModels;
    private MutableLiveData<List<ModelTaskProspect>> tLiveData = new MutableLiveData<>();

    public RepositoryTaskProspect() {
    }

    public MutableLiveData<List<ModelTaskProspect>> getProspectLiveData(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTaskProspect>> call = api.assignedTaskProspect(strUserId);
        call.enqueue(new Callback<List<ModelTaskProspect>>() {
            @Override
            public void onResponse(Call<List<ModelTaskProspect>> call, Response<List<ModelTaskProspect>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }
            @Override
            public void onFailure(Call<List<ModelTaskProspect>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
