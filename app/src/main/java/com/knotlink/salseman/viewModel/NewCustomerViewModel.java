package com.knotlink.salseman.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.knotlink.salseman.model.repo.ModelAddNewCustomer;
import com.knotlink.salseman.repository.RepositoryNewCustomer;

public class NewCustomerViewModel extends AndroidViewModel {


    private RepositoryNewCustomer tRepositoryNewCustomer;
    public NewCustomerViewModel(@NonNull Application application) {
        super(application);
        tRepositoryNewCustomer = new RepositoryNewCustomer();
    }

    public LiveData<ModelAddNewCustomer> getResponseNewCstomer(String strUserId, String strVendorName, String strContactName,
                                                               String strContactNo, String strLandlineNo, String strWhatsappNo,
                                                               String strEmail1, String strEmail2, String strAddress1,
                                                               String strAddress2, String strAddress3, String strFax,
                                                               String strCity, String strPincode, String strRoute,
                                                               String strGstNo, String strCreditDays, String strCreditLimit,
                                                               String strCustomerType, String strImage1, String strImage2,
                                                               String strImage3, String strLatitude, String strLongitude){

        return tRepositoryNewCustomer.gettLiveData(strUserId, strVendorName, strContactName,
                strContactNo, strLandlineNo, strWhatsappNo, strEmail1, strEmail2, strAddress1,
                strAddress2, strAddress3, strFax, strCity, strPincode, strRoute, strGstNo, strCreditDays,
                strCreditLimit, strCustomerType, strImage1, strImage2, strImage3, strLatitude, strLongitude);
    }

}
