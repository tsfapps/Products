package com.knotlink.salseman.repository.notice;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryNoticeReturn {

    private List<ModelNoticeReturn> tModels;
    private MutableLiveData<List<ModelNoticeReturn>> tLiveData = new MutableLiveData<>();

    public RepositoryNoticeReturn() {
    }
    public MutableLiveData<List<ModelNoticeReturn>> getCustomerLiveData(String strUserId, String strAttDate){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeReturn>> call = api.noticeReturn(strUserId, strAttDate);
        call.enqueue(new Callback<List<ModelNoticeReturn>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeReturn>> call, Response<List<ModelNoticeReturn>> response) {
                tModels = response.body();
                 tLiveData.setValue(tModels);
            }
            @Override
            public void onFailure(Call<List<ModelNoticeReturn>> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
