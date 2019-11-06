package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportMeeting;
import com.knotlink.salseman.repository.report.RepositoryMeetingReport;

import java.util.List;

public class ViewModelMeetingReport extends AndroidViewModel {

    private RepositoryMeetingReport tRepository;

    public ViewModelMeetingReport(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryMeetingReport();
    }
    public LiveData<List<ModelReportMeeting>> getMeetingReport(String strUserId, String strDateFrom, String strDateTo){
        return tRepository.getCustomerLiveData(strUserId,strDateFrom,strDateTo);
    }
}
