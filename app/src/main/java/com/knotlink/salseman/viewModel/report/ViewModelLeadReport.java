package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportLeadGeneration;
import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.repository.report.RepositoryLeadReport;
import com.knotlink.salseman.repository.report.RepositoryMeetingReport;

import java.util.List;

public class ViewModelLeadReport extends AndroidViewModel {

    private RepositoryLeadReport tRepository;

    public ViewModelLeadReport(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryLeadReport();
    }
    public LiveData<List<ModelReportLeadGeneration>> getLeadReport(String strUserId, String strDateFrom, String strDateTo){
        return tRepository.getCustomerLiveData(strUserId,strDateFrom,strDateTo);
    }
}
