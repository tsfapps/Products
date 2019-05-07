package com.knotlink.salseman.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.knotlink.salseman.fragment.FragDashboard;
import com.knotlink.salseman.fragment.dispatcher.FragDispatcher;
import com.knotlink.salseman.fragment.FragProfile;
import com.knotlink.salseman.fragment.FragReport;
import com.knotlink.salseman.fragment.FragTask;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.knotlink.salseman.utils.DateUtils.getTodayDate;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SharedPrefManager tSharedPrefManager;
    private Context tContext;

    private String strUserType;
    @BindView(R.id.toolbar)
    protected Toolbar tToolbar;
    @BindView(R.id.tvToolbar)
    protected TextView tTitle;
    @BindView(R.id.tv_date_main)
    protected TextView tvDateMain;
    @BindView(R.id.iv_bottom_dashboard)
    protected ImageView ivBottomDashboard;
    @BindView(R.id.iv_bottom_report)
    protected ImageView ivBottomReport;
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
        switch (strUserType) {
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("1")).commit();
                break;
            case "2":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance("2")).commit();
                break;
        }
        requestLocation();
    }
    @OnClick(R.id.iv_bottom_dashboard)
    public void onDashboardClicked(){
        switch (strUserType) {
            case "1":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).addToBackStack(null).commit();
                break;
            case "2":
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragDispatcher()).addToBackStack(null).commit();
                break;
        }
    }
    @OnClick(R.id.iv_bottom_report)
    public void onReportClicked(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragReport()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_bottom_task)
    public void onTaskClicked(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragTask()).addToBackStack(null).commit();
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
