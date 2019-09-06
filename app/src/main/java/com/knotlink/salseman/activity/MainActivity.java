package com.knotlink.salseman.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterTask;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.FragDashboard;
import com.knotlink.salseman.fragment.FragProfile;
import com.knotlink.salseman.fragment.report.FragReport;
import com.knotlink.salseman.fragment.FragTask;
import com.knotlink.salseman.model.task.ModelTask;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.knotlink.salseman.utils.DateUtils.getTodayDate;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private long lastPressedTime;
    private static final int PERIOD = 2000;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;



//    private String strUserId;
    private String strUserType;
    @BindView(R.id.toolbar)
    protected Toolbar tToolbar;
    @BindView(R.id.ivToolbarLogo)
    protected ImageView ivToolbarLogo;
    @BindView(R.id.tvToolbar)
    protected TextView tTitle;
    @BindView(R.id.tv_date_main)
    protected TextView tvDateMain;
    @BindView(R.id.iv_bottom_dashboard)
    protected ImageView ivBottomDashboard;
    @BindView(R.id.iv_bottom_report)
    protected ImageView ivBottomReport;
    @BindView(R.id.tvBottomTaskBadge)
    protected TextView tvBottomTaskBadge;
    @BindView(R.id.iv_bottom_task)
    protected ImageView ivBottomTask;
    @BindView(R.id.iv_bottom_profile)
    protected ImageView ivBottomProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(tToolbar);
        tvDateMain.setText(getTodayDate());
        initFrag();
       }

    public void setToolbarTitle(String strTitle){
        tTitle.setText(strTitle);
    }
    public void initFrag(){
        tContext = getApplicationContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserType = tSharedPrefManager.getUserType();

        callTaskApi();
        switch (strUserType) {
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("1")).commit();
                break;
            case "2":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("2")).commit();
                break;
         case "3":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("3")).commit();
                break;
        }
        requestLocation();

    }

    @OnClick(R.id.ivToolbarLogo)
    public void ivToolbarLogoClicked(View view){
        startActivity(new Intent(tContext, MainActivity.class));
    }
    @OnClick(R.id.iv_bottom_dashboard)
    public void onDashboardClicked(){
        switch (strUserType) {
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("1")).commit();
                break;
            case "2":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("2")).commit();
                break;
            case "3":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("3")).commit();
                break;
        }
    }
    @OnClick(R.id.iv_bottom_report)
    public void onReportClicked(){
        tSharedPrefManager.clearReportTime();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragReport.newInstance(strUserType)).addToBackStack(null) .commit();
    }
    @OnClick(R.id.iv_bottom_task)
    public void onTaskClicked(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragTask.newInstance(strUserType,"")).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_bottom_profile)
    public void onProfileClicked(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragProfile()).addToBackStack(null).commit();
    }
   @OnClick(R.id.iv_logout)
   public void onLogout(View view){
        tSharedPrefManager.clearUserData();
        startActivity(new Intent(tContext, LoginActivity.class));
   }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  void callTaskApi(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTask>> call = api.assignedTask(strUserId);
        call.enqueue(new Callback<List<ModelTask>>() {
            @Override
            public void onResponse(Call<List<ModelTask>> call, Response<List<ModelTask>> response) {
               List<ModelTask> tModelTask =response.body();
               int taskSize = tModelTask.size();
                if (taskSize>0) {
                    tvBottomTaskBadge.setVisibility(View.VISIBLE);
                    tvBottomTaskBadge.setText(String.valueOf(taskSize));
                }
                else {
                    tvBottomTaskBadge.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<ModelTask>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Task Not Responding : "+t);
            }
        });
    }

    private void requestLocation(){
    GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build();
    googleApiClient.connect();
    LocationRequest locationRequest = LocationRequest.create();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(5 * 1000);
    locationRequest.setFastestInterval(2 * 1000);
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest);

    //**************************
    builder.setAlwaysShow(true); //this is the key ingredient
    //**************************

    PendingResult<LocationSettingsResult> result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(@NonNull LocationSettingsResult result) {
            final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();

            if (status.getStatusCode()==LocationSettingsStatusCodes.SUCCESS){

            }
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:

                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                MainActivity.this, 1000);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        }
    });
}

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }












}
