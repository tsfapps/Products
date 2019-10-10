package com.knotlink.salseman.fragment.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragDashboard extends Fragment implements AdapterView.OnItemSelectedListener {

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

    @BindView(R.id.rlSpnDashSalesMan)
    protected RelativeLayout rlSpnDashSalesMan;
    @BindView(R.id.spnDashSalesMan)
    protected Spinner spnDashSalesMan;
    @BindView(R.id.pbSpnDashSales)
    protected ProgressBar pbSpnDashSales;
  @BindView(R.id.rlSpnDashAreaSalesMan)
    protected RelativeLayout rlSpnDashAreaSalesMan;
    @BindView(R.id.spnDashAreaSalesMan)
    protected Spinner spnDashAreaSalesMan;
    @BindView(R.id.pbSpnDashAreaSales)
    protected ProgressBar pbSpnDashAreaSales;

    private String strPresentDay;
    private boolean checkAttendance = true;

    private String strSelectedUserId;
    private String strUserId;
    private String strUserType;
    public static FragDashboard newInstance(String userType) {
        FragDashboard fragment = new FragDashboard();
        fragment.strUserType = userType;
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
        tContext = getContext();
        pbSpnDashSales.setVisibility(View.VISIBLE);
        checkLocationPermission();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        SetTitle.tbTitle("Dashboard", getActivity());
        tFragmentManager = getFragmentManager();
        hideShowContent();
        checkStatusApi();
    }
    public void checkStatusApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelActiveStatus> call = api.activeStatus(strUserId);
        call.enqueue(new Callback<ModelActiveStatus>() {
            @Override
            public void onResponse(Call<ModelActiveStatus> call, Response<ModelActiveStatus> response) {
                ModelActiveStatus tModelActive = response.body();
                assert tModelActive != null;
                strAttStatus = tModelActive.getAttendanceStatus();
                strAttTime = tModelActive.getTime();
                strAttDate = tModelActive.getDate();
//                checkAttendance = tModelActive.getAttendanceStatus().equalsIgnoreCase("1");
                strPresentDay = tModelActive.getDay();

            }
            @Override
            public void onFailure(Call<ModelActiveStatus> call, Throwable t) {
            }
        });
    }
    private void hideShowContent(){
        if (strUserType.equalsIgnoreCase("1")){
            cvDashLead.setVisibility(View.VISIBLE);
            cvDashCold.setVisibility(View.VISIBLE);
            cvDashMeeting.setVisibility(View.VISIBLE);
            rlSpnDashSalesMan.setVisibility(View.GONE);
            rlSpnDashAreaSalesMan.setVisibility(View.GONE);
        }
        else if (strUserType.equalsIgnoreCase("2")){
            cvDashLead.setVisibility(View.GONE);
            cvDashCold.setVisibility(View.GONE);
            cvDashMeeting.setVisibility(View.GONE);
            rlSpnDashSalesMan.setVisibility(View.GONE);
            rlSpnDashAreaSalesMan.setVisibility(View.GONE);
        }
        else if (strUserType.equalsIgnoreCase("3")){
            cvDashLead.setVisibility(View.VISIBLE);
            cvDashCold.setVisibility(View.VISIBLE);
            cvDashMeeting.setVisibility(View.VISIBLE);
            rlSpnDashSalesMan.setVisibility(View.VISIBLE);
            rlSpnDashAreaSalesMan.setVisibility(View.GONE);
            spnDashSalesMan.setOnItemSelectedListener(this);
            callApiSalesMan(strUserId);
        }
        else if (strUserType.equalsIgnoreCase("0")){
            cvDashLead.setVisibility(View.VISIBLE);
            cvDashCold.setVisibility(View.VISIBLE);
            cvDashMeeting.setVisibility(View.VISIBLE);
            rlSpnDashSalesMan.setVisibility(View.VISIBLE);
            rlSpnDashAreaSalesMan.setVisibility(View.VISIBLE);
            spnDashAreaSalesMan.setOnItemSelectedListener(this);
            spnDashSalesMan.setOnItemSelectedListener(this);
            callApiAreaSalesMan();
        }
    }
    private void callApiSalesMan(String strUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesMan>> call = api.salesAsmManList(strUserId);
        call.enqueue(new Callback<List<ModelSalesMan>>() {
            @Override
            public void onResponse(Call<List<ModelSalesMan>> call, Response<List<ModelSalesMan>> response) {
                tModelsSalesMan = response.body();
                pbSpnDashSales.setVisibility(View.GONE);
                tAdapterSalesMan = new AdapterSalesMan(tContext, tModelsSalesMan);
                spnDashSalesMan.setAdapter(tAdapterSalesMan);
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
                Log.d(Constant.TAG, "ASM List Size : "+tModelAsmList.size());
                pbSpnDashAreaSales.setVisibility(View.GONE);
                tAdapterAreaSalesMan = new AdapterAreaSalesMan(tContext, tModelAsmList);
                spnDashAreaSalesMan.setAdapter(tAdapterAreaSalesMan);
            }
            @Override
            public void onFailure(Call<List<ModelAsmList>> call, Throwable t) {
                Log.d(Constant.TAG, "ASM List Failure : "+t);
            }
        });
    }

    @OnClick(R.id.ll_dash_attendance)
    public void dashAttendanceClicked(){
        if (strAttStatus !=null) {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragAttendance.newInstance(strAttStatus, strAttTime, strAttDate)).addToBackStack(null).commit();
        }else {
            Toast.makeText(tContext, "Check the network status....", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.ll_dash_distance)
    public void disClicked(View view){
        if (checkAttendance) {
            if (!tSharedPrefManager.getStartKm().equals(""))
            {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragDistance.newInstance(tModelsVehicle,0,strUserType)).addToBackStack(null).commit();
            }
            else {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragVehicleList.newInstance(strUserType)).addToBackStack(null).commit();
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
         tFragmentManager.beginTransaction().replace(R.id.container_main, FragRoute.newInstance(strUserType, strSelectedUserId, strPresentDay)).addToBackStack(null).commit();

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
        switch (requestCode) {

            case Constant.MY_PERMISSIONS_REQUEST_LOCATION: {

                Toast.makeText(tContext, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spnDashAreaSalesMan:
                String strAsmId = tModelAsmList.get(position).getUserId();
                callApiSalesMan(strAsmId);
                break;
            case R.id.spnDashSalesMan:
                strSelectedUserId = tModelsSalesMan.get(position).getUserId();
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
