package com.knotlink.salseman.viewModel.notice;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.notice.ModelNoticeReturn;
import com.knotlink.salseman.repository.notice.RepositoryNoticeReturn;

import java.util.List;

public class ViewModelNoticeReturn extends AndroidViewModel {

    private RepositoryNoticeReturn tTaskCustomer;
    public ViewModelNoticeReturn(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryNoticeReturn();
    }
    public LiveData<List<ModelNoticeReturn>> getNoticeReturn(String strUserId, String strAttDate){
        return tTaskCustomer.getCustomerLiveData(strUserId, strAttDate);
    }
}
