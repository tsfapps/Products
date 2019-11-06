package com.knotlink.salseman.viewModel.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.knotlink.salseman.model.report.ModelReportAttendance;
import com.knotlink.salseman.model.report.ModelReportColdCall;
import com.knotlink.salseman.repository.report.RepositoryReportAtt;
import com.knotlink.salseman.repository.report.RepositoryReportCold;

import java.util.List;

public class ViewModelReportAtt extends AndroidViewModel {

    private RepositoryReportAtt tRepository;

    public ViewModelReportAtt(@NonNull Application application) {
        super(application);
        tRepository = new RepositoryReportAtt();
    }
    public LiveData<List<ModelReportAttendance>> getAttReport(String strUserId, String strDateFrom, String strDateTo){
        return tRepository.getCustomerLiveData(strUserId,strDateFrom,strDateTo);
    }
}
