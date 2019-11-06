package com.knotlink.salseman.viewModel.route;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.dash.route.ModelInsertReceipt;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;
import com.knotlink.salseman.repository.route.RepositoryInsertReceipt;
import com.knotlink.salseman.repository.route.RepositoryOrderList;

import java.util.List;

public class ViewModelInsertReceipt extends AndroidViewModel {

    private RepositoryInsertReceipt tTaskCustomer;
    public ViewModelInsertReceipt(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryInsertReceipt();
    }
    public LiveData<ModelInsertReceipt> insertReceipt(String strUserId, String strShopId, String strBankName,
                                                      String strInvoiceNo, String strInvoiceDate, String strInvoiceAmount,
                                                      String strTotalOutStandingAmount, String strBalanceAmount,
                                                      String strReceivedAmount,String strPendingAmount,String strPaymentMode,
                                                      String strLat, String strLong, String strNeftChequeNo,
                                                      String strNeftChequeMatureDtae, String strChequeImage,
                                                      String strSignature,String strRemarks,String strAreaStatus){
        return tTaskCustomer.getCustomerLiveData(strUserId,strShopId,strBankName,strInvoiceNo,strInvoiceDate,strInvoiceAmount,
                strTotalOutStandingAmount,strBalanceAmount,strReceivedAmount,strPendingAmount,strPaymentMode,strLat,strLong,
                strNeftChequeNo,strNeftChequeMatureDtae,strChequeImage,strSignature,strRemarks,strAreaStatus);
    }
}
