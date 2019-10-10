package com.knotlink.salseman.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
import com.knotlink.salseman.repository.RepositoryNoticeComplain;
import java.util.List;

public class NoticeComplainViewModel extends AndroidViewModel {

    private RepositoryNoticeComplain repositoryNoticeComplain;
    public NoticeComplainViewModel(@NonNull Application application) {
        super(application);
        repositoryNoticeComplain = new RepositoryNoticeComplain();
    }
    public LiveData<List<ModelNoticeComplain>> getAllNotice(String strUserId){
        return repositoryNoticeComplain.getMutableLiveData(strUserId);
    }
}
