package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.repository.report.RepositoryLeadReport;
import com.knotlink.salseman.repository.report.RepositoryReportCold;

import java.util.List;

public class ViewModelReportCold extends AndroidViewModel {

    private RepositoryReportCold tRepository;

    public ViewModelReportCold(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryReportCold();
    }
    public LiveData<List<ModelReportColdCall>> getColdReport(String strUserId, String strDateFrom, String strDateTo){
        return tRepository.getCustomerLiveData(strUserId,strDateFrom,strDateTo);
    }
}
