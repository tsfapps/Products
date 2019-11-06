package com.knotlink.salseman.viewModel.route;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.dash.route.ModelRequestList;
import com.knotlink.salseman.model.dash.route.ModelShopCounter;
import com.knotlink.salseman.repository.route.RepositoryRequestList;
import com.knotlink.salseman.repository.route.RepositoryShopCounter;

import java.util.List;

public class ViewModelShopCounter extends AndroidViewModel {

    private RepositoryShopCounter tTaskCustomer;
    public ViewModelShopCounter(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryShopCounter();
    }
    public LiveData<ModelShopCounter> getShopCounter(String strShopId, String strAttDate){
        return tTaskCustomer.getCustomerLiveData(strShopId, strAttDate);
    }
}
