package com.knotlink.salseman.repository.route;

import android.arch.lifecycle.MutableLiveData;

import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelInsertReceipt;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryInsertReceipt {

    private ModelInsertReceipt tModel;
    private MutableLiveData<ModelInsertReceipt> tLiveData = new MutableLiveData<>();

    public RepositoryInsertReceipt() {
    }

    public MutableLiveData<ModelInsertReceipt> getCustomerLiveData(String strUserId, String strShopId, String strBankName,
                                                                   String strInvoiceNo, String strInvoiceDate, String strInvoiceAmount,
                                                                   String strTotalOutStandingAmount, String strBalanceAmount,
                                                                   String strReceivedAmount,String strPendingAmount,String strPaymentMode,
                                                                   String strLat, String strLong, String strNeftChequeNo,
                                                                   String strNeftChequeMatureDtae, String strChequeImage,
                                                                   String strSignature,String strRemarks,String strAreaStatus
                                                                   ){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelInsertReceipt> call = api.insertReceipt(strUserId,strShopId,strBankName,strInvoiceNo,strInvoiceDate,strInvoiceAmount,
                strTotalOutStandingAmount,strBalanceAmount,strReceivedAmount,strPendingAmount,strPaymentMode,strLat,strLong,
                strNeftChequeNo,strNeftChequeMatureDtae,strChequeImage,strSignature,strRemarks,strAreaStatus);
        call.enqueue(new Callback<ModelInsertReceipt>() {
            @Override
            public void onResponse(Call<ModelInsertReceipt> call, Response<ModelInsertReceipt> response) {
                 tModel = response.body();
                 tLiveData.setValue(tModel);

            }

            @Override
            public void onFailure(Call<ModelInsertReceipt> call, Throwable t) {

            }
        });
        return tLiveData;
    }
}
