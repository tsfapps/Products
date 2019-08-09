package com.knotlink.salseman.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelAttendance;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragAttendance extends Fragment {
    private SharedPrefManager tSharedPrefManager;
    private GPSTracker tGpsTracker;
    private Context tContext;
    private ModelAttendance tModels;
    @BindView(R.id.tv_att_user_name)
    protected TextView tvAttUserName;
     @BindView(R.id.tv_att_time_label)
    protected TextView tvAttTimeLabel;
    @BindView(R.id.tv_att_time)
    protected TextView tvShiftingTime;
    @BindView(R.id.btn_att_check_in_out)
    protected Button btnAttCheckInOut;
    private boolean isLoggedIn = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_attendance, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        SetTitle.tbTitle("Attendance Report", getActivity());
        tGpsTracker = new GPSTracker(tContext);
        tSharedPrefManager = new SharedPrefManager(tContext);
        tvAttUserName.setText(tSharedPrefManager.getUserName());
        if (!tSharedPrefManager.getCheckInStatus()){
            btnAttCheckInOut.setText(Constant.BTN_CHECK_IN);
        }
        else{ btnAttCheckInOut.setText(Constant.BTN_CHECK_OUT);}
        tvShiftingTime.setText(tSharedPrefManager.getShiftTime());
        tvAttTimeLabel.setText(tSharedPrefManager.getShiftTimeLabel());
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn_att_check_in_out)
    public void checkedInAtt(View view){

        if (!tSharedPrefManager.getCheckInStatus()) {
            callLoginApi();

        }
        else {
            callLogoutApi();


        }
    }

    private void callLoginApi(){
        String strUserId = tSharedPrefManager.getUserId();
        String strLat = String.valueOf(tGpsTracker.latitude);
        String strLong = String.valueOf(tGpsTracker.longitude);
        String strCity = tGpsTracker.getCity(tContext);
        String strPinCode = tGpsTracker.getPostalCode(tContext);
        String strAddress = tGpsTracker.getAddressLine(tContext);
        Api api = ApiClients.getApiClients().create(Api.class);
        CustomLog.d(Constant.TAG, "\nLatitude : "+strLat+"\nLongitude : "+strLong+"\nLogin Address : "+ strAddress);
        Call<ModelAttendance> attendanceCall = api.uploadLogin(strUserId, strCity, strPinCode, strAddress, strLat, strLong);
        attendanceCall.enqueue(new Callback<ModelAttendance>() {
            @Override
            public void onResponse(Call<ModelAttendance> call, Response<ModelAttendance> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    btnAttCheckInOut.setText("Check Out");
                    tSharedPrefManager.setStatus(true);
                    tvAttTimeLabel.setText(Constant.START_LABEL);
                    tSharedPrefManager.setShiftTime(DateUtils.getCurrentTime(), Constant.START_LABEL);
                    tvShiftingTime.setText(DateUtils.getCurrentTime());
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelAttendance> call, Throwable t) {
                CustomLog.e(Constant.TAG, "Not Responding : " + t);
            }
        });
    }
    private void callLogoutApi(){
        String strUserIdLogout = tSharedPrefManager.getUserId();
        String strLogoutLat = String.valueOf(tGpsTracker.latitude);
        String strLogoutLong = String.valueOf(tGpsTracker.longitude);
        String strLogoutCity = tGpsTracker.getCity(tContext);
        String strLogoutPinCode = tGpsTracker.getPostalCode(tContext);
        String strLogoutAddress = tGpsTracker.getAddressLine(tContext);
        Api api = ApiClients.getApiClients().create(Api.class);
        CustomLog.d(Constant.TAG, "\nLogout Latitude : "+strLogoutLat+"\nLogin Longitude : "+strLogoutLong);
        Call<ModelAttendance> attendanceCall = api.uploadLogout(strUserIdLogout, strLogoutCity, strLogoutPinCode, strLogoutAddress, strLogoutLat, strLogoutLong);
        attendanceCall.enqueue(new Callback<ModelAttendance>() {
            @Override
            public void onResponse(Call<ModelAttendance> call, Response<ModelAttendance> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    btnAttCheckInOut.setText("Check In");
                    tSharedPrefManager.setStatus(false);
                    tvAttTimeLabel.setText(Constant.FINISH_LABEL);
                    tSharedPrefManager.setShiftTime(DateUtils.getCurrentTime(), Constant.FINISH_LABEL);
                    tvShiftingTime.setText(DateUtils.getCurrentTime());
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
        }
            @Override
            public void onFailure(Call<ModelAttendance> call, Throwable t) {
                CustomLog.e(Constant.TAG, "Not Responding : "+t);

            }
        });
    }
}
