package com.knotlink.salseman.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.notice.ModelNoticeRequest;
import com.knotlink.salseman.repository.RepositoryNoticeRequest;

import java.util.List;

public class NoticeRequestViewModel extends AndroidViewModel {

    private RepositoryNoticeRequest tRepository;
    public NoticeRequestViewModel(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryNoticeRequest();
    }
    public LiveData<List<ModelNoticeRequest>> getAllRequest(String strUserId){
        return tRepository.getMutableLiveData(strUserId);
    }
}
