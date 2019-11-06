package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelTimeReport;
import com.knotlink.salseman.repository.report.RepositoryLeadReport;
import com.knotlink.salseman.repository.report.RepositoryTimeReport;

import java.util.List;

public class ViewModelTimeReport extends AndroidViewModel {

    private RepositoryTimeReport tRepository;

    public ViewModelTimeReport(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryTimeReport();
    }
    public LiveData<List<ModelTimeReport>> getTimeReport(String strUserId, String strDate){
        return tRepository.getCustomerLiveData(strUserId,strDate);
    }
}
