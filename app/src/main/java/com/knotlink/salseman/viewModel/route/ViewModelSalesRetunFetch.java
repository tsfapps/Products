package com.knotlink.salseman.viewModel.route;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.dash.route.ModelSalesReturnShopFetch;
import com.knotlink.salseman.repository.route.RepositorySalesReturnFetch;

import java.util.List;

public class ViewModelSalesRetunFetch extends AndroidViewModel {

    private RepositorySalesReturnFetch tTaskCustomer;
    public ViewModelSalesRetunFetch(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositorySalesReturnFetch();
    }
    public LiveData<List<ModelSalesReturnShopFetch>> salesReturnFetch(String strUserId, String strShopId){
        return tTaskCustomer.getCustomerLiveData(strUserId, strShopId);
    }
}
