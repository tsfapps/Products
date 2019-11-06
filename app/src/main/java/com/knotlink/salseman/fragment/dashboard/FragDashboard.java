package com.knotlink.salseman.fragment.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.SingletonStatus;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.adapter.baseadapter.AdapterSalesMan;
import com.knotlink.salseman.adapter.spinner.AdapterAreaSalesMan;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.dashboard.route.FragRoute;
import com.knotlink.salseman.fragment.dashboard.route.FragVehicleList;
import com.knotlink.salseman.model.ModelAsmList;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.model.dash.ModelActiveStatus;
import com.knotlink.salseman.model.dash.route.ModelVehicleList;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.GetImei;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragDashboard extends Fragment{

    private Context tContext;
    private FragmentManager tFragmentManager;
    private SharedPrefManager tSharedPrefManager;
    private List<ModelSalesMan> tModelsSalesMan;
    private List<ModelAsmList> tModelAsmList;
    private AdapterAreaSalesMan tAdapterAreaSalesMan;
    private List<ModelVehicleList> tModelsVehicle;
    private AdapterSalesMan tAdapterSalesMan;
    private String strAttStatus;
    private String strAttTime;
    private String strAttDate;

    @BindView(R.id.tv_dash_attendance)
    protected TextView tvDashAttendance;

//    @BindView(R.id.ll_dash_distance)
//    protected LinearLayout ll_dash_distance;
//    @BindView(R.id.ll_dash_route)
//    protected LinearLayout ll_dash_route;
//    @BindView(R.id.ll_dash_lead_generation)
//    protected LinearLayout ll_dash_lead_generation;
//    @BindView(R.id.ll_dash_meeting)
//    protected LinearLayout ll_dash_meeting;
//    @BindView(R.id.ll_dash_cold_call)
//    protected LinearLayout ll_dash_cold_call;
//    @BindView(R.id.ll_dash_cash_collection)
//    protected LinearLayout ll_dash_cash_collection;
//    @BindView(R.id.ll_dash_expenses)
//    protected LinearLayout ll_dash_expenses;
//    @BindView(R.id.cvDashAttendance)
//    protected CardView cvDashAttendance;
    @BindView(R.id.cvDashDistance)
    protected CardView cvDashDistance;
    @BindView(R.id.cvDashRoute)
    protected CardView cvDashRoute;
    @BindView(R.id.cvDashLead)
    protected CardView cvDashLead;
    @BindView(R.id.cvDashMeeting)
    protected CardView cvDashMeeting;
    @BindView(R.id.cvDashCold)
    protected CardView cvDashCold;
    @BindView(R.id.cvDashCash)
    protected CardView cvDashCash;
    @BindView(R.id.cvDashExpenses)
    protected CardView cvDashExpenses;


    private String strPresentDay;
    private boolean checkAttendance = true;
    private boolean viewPermission = true;

    private String strUserId;
    private String strSelectedUserId;
    private String strUserType;
    public static FragDashboard newInstance(String strSelectedUserId, String userType) {
        FragDashboard fragment = new FragDashboard();
        fragment.strUserType = userType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dashboard, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        Log.d(Constant.TAG, "Selected Id Dash : "+strSelectedUserId);
        tContext = getContext();
        checkLocationPermission();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();

        if (strUserType.equalsIgnoreCase("0")||strUserType.equalsIgnoreCase("3")){
            checkStatusApi(strSelectedUserId);
        }else {
            checkStatusApi(strUserId);
        }
        SetTitle.tbTitle("Dashboard", getActivity());
        tFragmentManager = getFragmentManager();
        hideShowContent();



    }
    public void checkStatusApi(String srUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelActiveStatus> call = api.activeStatus(GetImei.getDeviceIMEI(tContext, getActivity()), srUserId);
        call.enqueue(new Callback<ModelActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ModelActiveStatus> call, @NonNull Response<ModelActiveStatus> response) {
                ModelActiveStatus tModelActive = response.body();
                assert tModelActive != null;
                strAttStatus = tModelActive.getAttendanceStatus();
                strAttTime = tModelActive.getTime();
                strAttDate = tModelActive.getDate();
                if (strUserType.equalsIgnoreCase("0")||strUserType.equalsIgnoreCase("3")){
                    checkAttendance = true;
                }else{
                    checkAttendance = !strAttStatus.equalsIgnoreCase("0");
                }

                strPresentDay = tModelActive.getDay();

            }
            @Override
            public void onFailure(Call<ModelActiveStatus> call, Throwable t) {
            }
        });
    }
    private void hideShowContent(){

        switch (strUserType){
            case "1":
                cvDashLead.setVisibility(View.VISIBLE);
                cvDashCold.setVisibility(View.VISIBLE);
                cvDashMeeting.setVisibility(View.VISIBLE);
                viewPermission = false;
                break;

                case "2":
                    cvDashLead.setVisibility(View.GONE);
                    cvDashCold.setVisibility(View.GONE);
                    cvDashMeeting.setVisibility(View.GONE);
                    viewPermission = false;

                    break;
                case "3":
                case "0":
                    cvDashLead.setVisibility(View.VISIBLE);
                    cvDashCold.setVisibility(View.VISIBLE);
                    cvDashMeeting.setVisibility(View.VISIBLE);
                    viewPermission = true;
                break;
        }


    }


    @OnClick(R.id.ll_dash_attendance)
    public void dashAttendanceClicked(){
        if (strAttStatus !=null) {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragAttendance.newInstance(strUserType, strAttStatus,
                    strAttTime, strAttDate)).addToBackStack(null).commit();
        }else {
            Toast.makeText(tContext, "Check the network status....", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.ll_dash_distance)
    public void disClicked(View view){
        if (checkAttendance) {
            if (!tSharedPrefManager.getStartKm().equals(""))
            {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragDistance.newInstance(strAttDate, strSelectedUserId, tModelsVehicle,0,strUserType)).addToBackStack(null).commit();
            }
            else {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragVehicleList.newInstance(strAttDate, strSelectedUserId, strUserType)).addToBackStack(null).commit();
            }        }else {
            Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
        }

    }
    @OnClick(R.id.ll_dash_meeting)
    public void meetingClicked(View view){
        if (checkAttendance) {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragMeeting.newInstance(strUserType, strSelectedUserId)).addToBackStack(null).commit();

        }else {
            Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
        }
    }
 @OnClick(R.id.ll_dash_route)
    public void orderClicked(View view){
     if (checkAttendance) {
         tFragmentManager.beginTransaction().replace(R.id.container_main, FragRoute.newInstance(strAttDate, strUserType,
                 strSelectedUserId, strPresentDay)).addToBackStack(null).commit();

     }else {
         Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
     }
    }

    @OnClick(R.id.ll_dash_lead_generation)
    public void leadClicked(View view){
        if (checkAttendance) {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragLead.newInstance(strUserType, strSelectedUserId)).addToBackStack(null).commit();

        }else {
            Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
        }

    }

 @OnClick(R.id.ll_dash_cold_call)
    public void coldClicked(View view){
     if (checkAttendance) {
         tFragmentManager.beginTransaction().replace(R.id.container_main, FragColdCall.newInstance(strUserType, strSelectedUserId)).addToBackStack(null).commit();

     }else {
         Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
     }
    }
 @OnClick(R.id.ll_dash_cash_collection)
    public void cashClicked(View view){
     if (checkAttendance) {
         tFragmentManager.beginTransaction().replace(R.id.container_main, FragCash.newInstance(strUserType, strSelectedUserId)).addToBackStack(null).commit();

     }else {
         Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
     }
    }
 @OnClick(R.id.ll_dash_expenses)
    public void expensesClicked(View view){
     if (checkAttendance) {
         tFragmentManager.beginTransaction().replace(R.id.container_main, new FragExpenses()).addToBackStack(null).commit();
     }else {
         Toast.makeText(tContext, "Please check in first to continue...", Toast.LENGTH_SHORT).show();
     }



    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(tContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(tContext)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        Constant.MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constant.MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == Constant.MY_PERMISSIONS_REQUEST_LOCATION) {
            Toast.makeText(tContext, "permission denied", Toast.LENGTH_LONG).show();
        }

    }



}
