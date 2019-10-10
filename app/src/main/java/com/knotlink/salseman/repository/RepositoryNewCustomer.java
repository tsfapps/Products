package com.knotlink.salseman.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.repo.ModelAddNewCustomer;
import com.knotlink.salseman.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryNewCustomer {
    private ModelAddNewCustomer tModels;
    private MutableLiveData<ModelAddNewCustomer> tLiveData = new MutableLiveData<>();

    public RepositoryNewCustomer() {
    }

    public MutableLiveData<ModelAddNewCustomer> gettLiveData(String strUsreId, String strVendorName, String strContactName,
                                                             String strContactNo, String strLandlineNo, String strWhatsappNo,
                                                             String strEmail1, String strEmail2, String strAddress1,
                                                             String strAddress2, String strAddress3, String strFax,
                                                             String strCity, String strPincode, String strRoute,
                                                             String strGstNo, String strCreditDays, String strCreditLimit,
                                                             String strCustomerType, String strImage1, String strImage2,
                                                             String strImage3, String strLatitude, String strLongitude){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelAddNewCustomer> call = api.addNewCustomer(strUsreId,strVendorName,strContactName,
                strContactNo,strLandlineNo,strWhatsappNo,strEmail1,strEmail2,strAddress1,strAddress2,
                strAddress3,strFax,strCity,strPincode,strRoute,strGstNo,strCreditDays,strCreditLimit,strCustomerType,
                strImage1,strImage2,strImage3,strLatitude,strLongitude);
        call.enqueue(new Callback<ModelAddNewCustomer>() {
            @Override
            public void onResponse(Call<ModelAddNewCustomer> call, Response<ModelAddNewCustomer> response) {
                tModels = response.body();
                tLiveData.setValue(tModels);
            }

            @Override
            public void onFailure(Call<ModelAddNewCustomer> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
