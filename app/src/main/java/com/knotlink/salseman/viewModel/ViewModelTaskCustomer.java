package com.knotlink.salseman.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.repository.RepositoryTaskCustomer;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class ViewModelTaskCustomer extends AndroidViewModel {

    private RepositoryTaskCustomer tTaskCustomer;
    public ViewModelTaskCustomer(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryTaskCustomer();
    }
    public LiveData<List<ModelTaskCustomer>> getAllTaskCustomer(String strUserId){
        return tTaskCustomer.getCustomerLiveData(strUserId);
    }
}
