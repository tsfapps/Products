package com.knotlink.salseman.viewModel.notice;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.notice.ModelNoticeCheque;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;
import com.knotlink.salseman.repository.notice.RepositoryNoticeCheque;
import com.knotlink.salseman.repository.notice.RepositoryNoticeReturn;

import java.util.List;

public class ViewModelNoticeCheque extends AndroidViewModel {

    private RepositoryNoticeCheque tTaskCustomer;
    public ViewModelNoticeCheque(@NonNull Application application) {
        super(application);
        tTaskCustomer = new RepositoryNoticeCheque();
    }
    public LiveData<List<ModelNoticeCheque>> getNoticeCheque(String strUserId, String strAttDate){
        return tTaskCustomer.getCustomerLiveData(strUserId, strAttDate);
    }
}
