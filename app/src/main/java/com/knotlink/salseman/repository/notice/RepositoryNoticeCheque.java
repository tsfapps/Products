package com.knotlink.salseman.repository.notice;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.notice.ModelNoticeCheque;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryNoticeCheque {

    private List<ModelNoticeCheque> tModels;
    private MutableLiveData<List<ModelNoticeCheque>> tLiveData = new MutableLiveData<>();

    public RepositoryNoticeCheque() {
    }
    public MutableLiveData<List<ModelNoticeCheque>> getCustomerLiveData(String strUserId, String strAttDate){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeCheque>> call = api.noticeCheque(strUserId, strAttDate);
        call.enqueue(new Callback<List<ModelNoticeCheque>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeCheque>> call, Response<List<ModelNoticeCheque>> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }
            @Override
            public void onFailure(Call<List<ModelNoticeCheque>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
