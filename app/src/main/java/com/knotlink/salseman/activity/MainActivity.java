package com.knotlink.salseman.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.knotlink.salseman.adapter.baseadapter.AdapterSalesMan;
import com.knotlink.salseman.adapter.spinner.AdapterAreaSalesMan;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.FragAddNewCustomer;
import com.knotlink.salseman.fragment.dashboard.FragDashboard;
import com.knotlink.salseman.fragment.FragProfile;
import com.knotlink.salseman.fragment.notice.FragNoticeComplain;
import com.knotlink.salseman.fragment.report.FragReport;
import com.knotlink.salseman.fragment.FragTask;
import com.knotlink.salseman.model.ModelAsmList;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.model.dash.ModelActiveStatus;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;
import com.knotlink.salseman.model.notice.ModelNoticeRequest;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.model.task.ModelTaskProspect;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.knotlink.salseman.utils.DateUtils.getTodayDate;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {

    private SharedPrefManager tSharedPrefManager;
    private Context tContext;
    int taskProspectSize;
    int taskSize;
    private String strActiveStatus;
    private String strSttendanceStatus;
    private String strAttDate ;


    private List<ModelSalesMan> tModelsSalesMan;
    private List<ModelAsmList> tModelAsmList;
    private AdapterAreaSalesMan tAdapterAreaSalesMan;
    private AdapterSalesMan tAdapterSalesMan;
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
    @BindView(R.id.tvBottomNoticeReturnBadge)
    protected TextView tvBottomNoticeReturnBadge;
    @BindView(R.id.tvBottomTaskBadge)
    protected TextView tvBottomTaskBadge;
    @BindView(R.id.tvBottomTaskProspectBadge)
    protected TextView tvBottomTaskProspectBadge;
    @BindView(R.id.iv_bottom_task)
    protected ImageView ivBottomTask;
    @BindView(R.id.iv_bottom_profile)
    protected ImageView ivBottomProfile;
    @BindView(R.id.iv_bottom_notice)
    protected ImageView ivBottomNotice;
    @BindView(R.id.rlBadgeNotice)
    protected RelativeLayout rlBadgeNotice;
    @BindView(R.id.tvBottomNoticeRequestBadge)
    protected TextView tvBottomNoticeRequestBadge;
    @BindView(R.id.tvBottomNoticeComplainBadge)
    protected TextView tvBottomNoticeComplainBadge;
    private List<ModelNoticeRequest> tModelsRequest;

    //Spinner
    @BindView(R.id.rlSpnMainSalesMan)
    protected RelativeLayout rlSpnMainSalesMan;
    @BindView(R.id.spnMainSalesMan)
    protected Spinner spnMainSalesMan;
    @BindView(R.id.pbSpnMainSales)
    protected ProgressBar pbSpnMainSales;
    @BindView(R.id.rlSpnMainAreaSalesMan)
    protected RelativeLayout rlSpnMainAreaSalesMan;
    @BindView(R.id.spnMainAreaSalesMan)
    protected Spinner spnMainAreaSalesMan;
    @BindView(R.id.pbSpnMainAreaSales)
    protected ProgressBar pbSpnMainAreaSales;

    private String strSelectedUserId;

    private int menuClicked;
    private int counterRequest;
    private String strUserId;

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

        checkStatusApi();
        callTaskApi();
        callTaskProspectApi();

        switch (strUserType) {
            case "1":
                ivBottomProfile.setVisibility(View.VISIBLE);
                rlBadgeNotice.setVisibility(View.GONE);
                rlSpnMainSalesMan.setVisibility(View.GONE);
                rlSpnMainAreaSalesMan.setVisibility(View.GONE);
                strUserId = tSharedPrefManager.getUserId();

                getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                        FragDashboard.newInstance(strUserId, "1")).commit();
                break;
            case "2":
                ivBottomProfile.setVisibility(View.VISIBLE);
                rlBadgeNotice.setVisibility(View.GONE);
                rlSpnMainSalesMan.setVisibility(View.GONE);
                rlSpnMainAreaSalesMan.setVisibility(View.GONE);
                strUserId = tSharedPrefManager.getUserId();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                        FragDashboard.newInstance(strUserId, "2")).commit();
                break;
            case "3":
                 ivBottomProfile.setVisibility(View.GONE);
                 rlBadgeNotice.setVisibility(View.VISIBLE);
                rlSpnMainSalesMan.setVisibility(View.VISIBLE);
                rlSpnMainAreaSalesMan.setVisibility(View.GONE);
                spnMainSalesMan.setOnItemSelectedListener(this);
                strUserId = tSharedPrefManager.getUserId();
                callApiSalesMan(strUserId);
                if (strSttendanceStatus!=null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                            FragDashboard.newInstance(strSelectedUserId, "3")).commit();
                    strUserId = strSelectedUserId;
                }else {
                    Toast.makeText(tContext, "Select another user plz...", Toast.LENGTH_SHORT).show();
                }
                break;

            case "0":
                 ivBottomProfile.setVisibility(View.GONE);
                 rlBadgeNotice.setVisibility(View.VISIBLE);
                rlSpnMainSalesMan.setVisibility(View.VISIBLE);
                rlSpnMainAreaSalesMan.setVisibility(View.VISIBLE);
                spnMainAreaSalesMan.setOnItemSelectedListener(this);
                spnMainSalesMan.setOnItemSelectedListener(this);
                callApiAreaSalesMan();
                if (strSttendanceStatus!=null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                            FragDashboard.newInstance(strSelectedUserId, "0")).commit();
                    strUserId = strSelectedUserId;
                }else {
                    Toast.makeText(tContext, "Select another user plz...", Toast.LENGTH_SHORT).show();
                }

                 break;
        }
        requestLocation();
        getCounterRequest();
        getCounterComplain();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @OnClick(R.id.ivToolbarLogo)
    public void ivToolbarLogoClicked(View view){
        startActivity(new Intent(tContext, MainActivity.class));
    }
    @OnClick(R.id.iv_bottom_dashboard)
    public void onDashboardClicked(){
        menuClicked = 1;
        switch (strUserType) {
            case "1":
            case "2":
                ivBottomProfile.setVisibility(View.VISIBLE);
                ivBottomNotice.setVisibility(View.GONE);
                rlSpnMainSalesMan.setVisibility(View.GONE);
                rlSpnMainAreaSalesMan.setVisibility(View.GONE);
                strUserId = tSharedPrefManager.getUserId();

                getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                        FragDashboard.newInstance(strUserId, strUserType)).commit();
                break;

            case "3":
                ivBottomProfile.setVisibility(View.GONE);
                ivBottomNotice.setVisibility(View.VISIBLE);
                rlSpnMainSalesMan.setVisibility(View.VISIBLE);
                rlSpnMainAreaSalesMan.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                        FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
                break;
            case "0":
                ivBottomProfile.setVisibility(View.GONE);
                ivBottomNotice.setVisibility(View.VISIBLE);
                rlSpnMainSalesMan.setVisibility(View.VISIBLE);
                rlSpnMainAreaSalesMan.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                        FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
                break;

        }
    }
    @OnClick(R.id.iv_bottom_report)
    public void onReportClicked(){
        menuClicked = 2;
        if (strUserType.equalsIgnoreCase("0")) {
            rlSpnMainSalesMan.setVisibility(View.VISIBLE);
            rlSpnMainAreaSalesMan.setVisibility(View.VISIBLE);
        }else if (strUserType.equalsIgnoreCase("3")){
            rlSpnMainSalesMan.setVisibility(View.VISIBLE);
            rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        }else {
            rlSpnMainSalesMan.setVisibility(View.GONE);
            rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        }
        tSharedPrefManager.clearReportTime();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                FragReport.newInstance(strSelectedUserId, strUserType)).addToBackStack(null) .commit();
    }
    @OnClick(R.id.iv_bottom_task)
    public void onTaskClicked(){
        menuClicked = 3;
        if (strUserType.equalsIgnoreCase("0")) {
            rlSpnMainSalesMan.setVisibility(View.VISIBLE);
            rlSpnMainAreaSalesMan.setVisibility(View.VISIBLE);
        }else if (strUserType.equalsIgnoreCase("3")){
            rlSpnMainSalesMan.setVisibility(View.VISIBLE);
            rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        }else {
            rlSpnMainSalesMan.setVisibility(View.GONE);
            rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                FragTask.newInstance(strSelectedUserId, strUserType)).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_bottom_profile)
    public void onProfileClicked(){
        rlSpnMainSalesMan.setVisibility(View.GONE);
        rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                new FragProfile()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_bottom_notice)
    public void onNoticeClicked(){
        rlSpnMainSalesMan.setVisibility(View.GONE);
        rlSpnMainAreaSalesMan.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                 FragNoticeComplain.newInstance(strAttDate)).addToBackStack(null).commit();
    }
   @OnClick(R.id.iv_logout)
   public void onLogout(View view){
        tSharedPrefManager.clearUserData();
        startActivity(new Intent(tContext, LoginActivity.class));
        finishAffinity();
   }
   @OnClick(R.id.ivAddNewCustomer)
   public void ivAddNewCustomerClicked(View view){
       getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
               new FragAddNewCustomer()).addToBackStack(null).commit();
   }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  void callReturnApi(String strAttDate){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeReturn>> call = api.noticeReturn(strUserId, strAttDate);
        call.enqueue(new Callback<List<ModelNoticeReturn>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeReturn>> call, Response<List<ModelNoticeReturn>> response) {
               List<ModelNoticeReturn> tModelNoticeReturn =response.body();
                if (tModelNoticeReturn.size()>0) {
                    tvBottomNoticeReturnBadge.setVisibility(View.VISIBLE);
                    tvBottomNoticeReturnBadge.setText(String.valueOf(tModelNoticeReturn.size()));
                }
                else {
                    tvBottomNoticeReturnBadge.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<ModelNoticeReturn>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Return Not Responding : "+t);
                CustomLog.d(Constant.TAG, " Return Not Responding : "+call);
            }
        });
    }
    private  void callTaskApi(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTaskCustomer>> call = api.assignedTask(strUserId);
        call.enqueue(new Callback<List<ModelTaskCustomer>>() {
            @Override
            public void onResponse(Call<List<ModelTaskCustomer>> call, Response<List<ModelTaskCustomer>> response) {
               List<ModelTaskCustomer> tModelTaskCustomer =response.body();
                if (tModelTaskCustomer.size()>0) {
                    tvBottomTaskBadge.setVisibility(View.VISIBLE);
                    tvBottomTaskBadge.setText(String.valueOf(tModelTaskCustomer.size()));
                }
                else {
                    tvBottomTaskBadge.setVisibility(View.GONE);
                }



            }
            @Override
            public void onFailure(Call<List<ModelTaskCustomer>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Task Not Responding : "+t);
            }
        });
    }



    private  void callTaskProspectApi(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTaskProspect>> call = api.assignedTaskProspect(strUserId);
        call.enqueue(new Callback<List<ModelTaskProspect>>() {
            @Override
            public void onResponse(Call<List<ModelTaskProspect>> call, Response<List<ModelTaskProspect>> response) {
               List<ModelTaskProspect> tModelTask =response.body();
               taskProspectSize = tModelTask.size();
                if (tModelTask.size()>0) {
                    tvBottomTaskProspectBadge.setVisibility(View.VISIBLE);
                    tvBottomTaskProspectBadge.setText(String.valueOf(tModelTask.size()));
                }
                else {
                    tvBottomTaskProspectBadge.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<List<ModelTaskProspect>> call, Throwable t) {
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


    //Counter Request
    public void getCounterRequest(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeRequest>> call = api.getNoticeRequest(strUserId);
        call.enqueue(new Callback<List<ModelNoticeRequest>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeRequest>> call, Response<List<ModelNoticeRequest>> response) {
                tModelsRequest = response.body();
                counterRequest = tModelsRequest.size();
                if (counterRequest>0) {
                    tvBottomNoticeRequestBadge.setVisibility(View.VISIBLE);
                    tvBottomNoticeRequestBadge.setText(String.valueOf(counterRequest));
                }else {
                    tvBottomNoticeRequestBadge.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<List<ModelNoticeRequest>> call, Throwable t) {
            }
        });
    }
    public void getCounterComplain(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelNoticeComplain>> call = api.getNoticeComplain(strUserId);
        call.enqueue(new Callback<List<ModelNoticeComplain>>() {
            @Override
            public void onResponse(Call<List<ModelNoticeComplain>> call, Response<List<ModelNoticeComplain>> response) {
                List<ModelNoticeComplain> tModelsComplain = response.body();
              int  counterComplain = tModelsComplain.size();
                if (counterComplain>0) {
                    tvBottomNoticeComplainBadge.setVisibility(View.VISIBLE);
                    tvBottomNoticeComplainBadge.setText(String.valueOf(counterComplain));
                }else {
                    tvBottomNoticeComplainBadge.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<List<ModelNoticeComplain>> call, Throwable t) {
            }
        });
    }
    public void checkStatusApi(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelActiveStatus> call = api.activeStatus(getDeviceIMEI(), strUserId);
        call.enqueue(new Callback<ModelActiveStatus>() {
            @Override
            public void onResponse(Call<ModelActiveStatus> call, Response<ModelActiveStatus> response) {
                ModelActiveStatus tModelActive = response.body();
                assert tModelActive != null;
                strSttendanceStatus = tModelActive.getAttendanceStatus();
                strActiveStatus = tModelActive.getActiveStatus();
                strAttDate = tModelActive.getDate();
                callReturnApi(strAttDate);

                if (Objects.requireNonNull(strActiveStatus).equalsIgnoreCase("0")){
                    tSharedPrefManager.clearUserData();
                    startActivity(new Intent(tContext, LoginActivity.class));
                    finishAffinity();
                }

            }
            @Override
            public void onFailure(Call<ModelActiveStatus> call, Throwable t) {
            }
        });
    }

    public String getDeviceIMEI() {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, Constant.REQUEST_READ_PHONE_STATE);
            return "Permission not granted";
        }
        else {
            String deviceUniqueIdentifier = null;
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return deviceUniqueIdentifier;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Constant.REQUEST_READ_PHONE_STATE) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            }
        }
    }


    private void callApiSalesMan(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesMan>> call = api.salesAsmManList(strUserId);
        call.enqueue(new Callback<List<ModelSalesMan>>() {
            @Override
            public void onResponse(Call<List<ModelSalesMan>> call, Response<List<ModelSalesMan>> response) {
                tModelsSalesMan = response.body();
                pbSpnMainSales.setVisibility(View.GONE);
                tAdapterSalesMan = new AdapterSalesMan(tContext, tModelsSalesMan);
                spnMainSalesMan.setAdapter(tAdapterSalesMan);
            }
            @Override
            public void onFailure(Call<List<ModelSalesMan>> call, Throwable t) {
            }
        });
    }

    private void callApiAreaSalesMan(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelAsmList>> call = api.asmList(strUserId);
        call.enqueue(new Callback<List<ModelAsmList>>() {
            @Override
            public void onResponse(Call<List<ModelAsmList>> call, Response<List<ModelAsmList>> response) {
                tModelAsmList = response.body();
                pbSpnMainAreaSales.setVisibility(View.GONE);
                tAdapterAreaSalesMan = new AdapterAreaSalesMan(tContext, tModelAsmList);
                spnMainAreaSalesMan.setAdapter(tAdapterAreaSalesMan);
            }
            @Override
            public void onFailure(Call<List<ModelAsmList>> call, Throwable t) {
                Log.d(Constant.TAG, "ASM List Failure : "+t);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spnMainAreaSalesMan:
                String strAsmId = tModelAsmList.get(position).getUserId();
                callApiSalesMan(strAsmId);
                break;
            case R.id.spnMainSalesMan:
                Log.d(Constant.TAG, "Selected Id Main : "+strSelectedUserId);
                strSelectedUserId = tModelsSalesMan.get(position).getUserId();
                Log.d(Constant.TAG, "Selected Id Main : "+strSelectedUserId);
                switch (menuClicked){
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                            FragReport.newInstance(strSelectedUserId, strUserType)).addToBackStack(null) .commit();
                    break;
                 case 3:
                     getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                             FragTask.newInstance(strSelectedUserId, strUserType)).addToBackStack(null).commit();
                     break;
                     default:
                         getSupportFragmentManager().beginTransaction().replace(R.id.container_main,
                                 FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
                         break;

                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
