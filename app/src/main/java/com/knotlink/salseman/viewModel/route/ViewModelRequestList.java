package com.knotlink.salseman.viewModel.route;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.dash.route.ModelRequestList;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.repository.RepositoryTaskCustomer;
import com.knotlink.salseman.repository.route.RepositoryRequestList;

import java.util.List;

public class ViewModelRequestList extends AndroidViewModel {

    private RepositoryRequestList tTaskCustomer;
    public ViewModelRequestList(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryRequestList();
    }
    public LiveData<List<ModelRequestList>> getAllTaskCustomer(String strShopId, String strAttDate){
        return tTaskCustomer.getCustomerLiveData(strShopId, strAttDate);
    }
}
