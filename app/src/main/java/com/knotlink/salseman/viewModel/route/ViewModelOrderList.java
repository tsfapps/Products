package com.knotlink.salseman.viewModel.route;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.dash.route.ModelSalesReturnFetch;
import com.knotlink.salseman.model.dash.route.order.ModelOrderList;
import com.knotlink.salseman.repository.route.RepositoryOrderList;
import com.knotlink.salseman.repository.route.RepositorySalesReturnFetch;

import java.util.List;

public class ViewModelOrderList extends AndroidViewModel {

    private RepositoryOrderList tTaskCustomer;
    public ViewModelOrderList(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryOrderList();
    }
    public LiveData<List<ModelOrderList>> ordeList(String strAttDate, String strUserId, String strShopId){
        return tTaskCustomer.getCustomerLiveData(strAttDate, strUserId, strShopId);
    }
}
