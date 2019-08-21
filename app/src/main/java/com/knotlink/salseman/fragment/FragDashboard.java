package com.knotlink.salseman.fragment;

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
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.model.ModelVehicleList;
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
    private List<ModelVehicleList> tModelsVehicle;
    private AdapterSalesMan tAdapterSalesMan;

    //    @BindView(R.id.tv_dashboard_user_name)
//    protected TextView tvDashboardUserName;
    @BindView(R.id.tv_dash_attendance)
    protected TextView tvDashAttendance;

    @BindView(R.id.cvDashAttendance)
    protected CardView cvDashAttendance;
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

    private String strSalesId;
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
        spnDashSalesMan.setOnItemSelectedListener(this);
        hideShowContent();

    }

    private void hideShowContent(){
        if (strUserType.equalsIgnoreCase("1")){
            cvDashLead.setVisibility(View.VISIBLE);
            cvDashCold.setVisibility(View.VISIBLE);
            cvDashMeeting.setVisibility(View.VISIBLE);
            rlSpnDashSalesMan.setVisibility(View.GONE);
        }
        else if (strUserType.equalsIgnoreCase("2")){
            cvDashLead.setVisibility(View.GONE);
            cvDashCold.setVisibility(View.GONE);
            cvDashMeeting.setVisibility(View.GONE);
            rlSpnDashSalesMan.setVisibility(View.GONE);
        }
        else if (strUserType.equalsIgnoreCase("3")){
            cvDashLead.setVisibility(View.VISIBLE);
            cvDashCold.setVisibility(View.VISIBLE);
            cvDashMeeting.setVisibility(View.VISIBLE);
            rlSpnDashSalesMan.setVisibility(View.VISIBLE);
            callApiSalesMan();

        }
    }

    private void callApiSalesMan(){

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


    @OnClick(R.id.ll_dash_attendance)
    public void dashAttendanceClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragAttendance()).addToBackStack(null).commit();
    }
    @OnClick(R.id.ll_dash_distance)
    public void disClicked(View view){
        if (!tSharedPrefManager.getStartKm().equals(""))
        {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragDistance.newInstance(tModelsVehicle,0,strUserType)).addToBackStack(null).commit();

        }
        else {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragVehicleList.newInstance(strUserType)).addToBackStack(null).commit();
        }
    }
    @OnClick(R.id.ll_dash_meeting)
    public void meetingClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragMeeting.newInstance(strUserType, strSalesId)).addToBackStack(null).commit();
    }
 @OnClick(R.id.ll_dash_route)
    public void orderClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragRoute.newInstance(strUserType, strSalesId)).addToBackStack(null).commit();
    }

    @OnClick(R.id.ll_dash_lead_generation)
    public void leadClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragLead.newInstance(strUserType, strSalesId)).addToBackStack(null).commit();
    }

 @OnClick(R.id.ll_dash_cold_call)
    public void coldClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragColdCall.newInstance(strUserType, strSalesId)).addToBackStack(null).commit();
    }
 @OnClick(R.id.ll_dash_cash_collection)
    public void cashClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragCash.newInstance(strUserType, strSalesId)).addToBackStack(null).commit();
    }
 @OnClick(R.id.ll_dash_expenses)
    public void expensesClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragExpenses()).addToBackStack(null).commit();
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
        strSalesId = tModelsSalesMan.get(position).getUserId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
