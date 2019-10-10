package com.knotlink.salseman.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.knotlink.salseman.model.task.ModelTaskProspect;
import com.knotlink.salseman.repository.RepositoryTaskProspect;
import com.knotlink.salseman.utils.Constant;

import java.util.List;

public class ViewModelTaskProspect extends AndroidViewModel {
    private RepositoryTaskProspect tRepository;
    public ViewModelTaskProspect(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryTaskProspect();
    }
    public LiveData<List<ModelTaskProspect>> getTaskProspect(String strUserId){
                return tRepository.getProspectLiveData(strUserId);
    }
}
