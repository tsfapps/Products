package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelUserActivityCount;
import com.knotlink.salseman.repository.report.RepositoryActivityCount;
import com.knotlink.salseman.repository.report.RepositoryLeadReport;

import java.util.List;

public class ViewModelActivityCount extends AndroidViewModel {

    private RepositoryActivityCount tRepository;

    public ViewModelActivityCount(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryActivityCount();
    }
    public LiveData<ModelUserActivityCount> getActivityCount(String strUserId, String strDateFrom, String strDateTo){
        return tRepository.getCustomerLiveData(strUserId,strDateFrom,strDateTo);
    }
}
