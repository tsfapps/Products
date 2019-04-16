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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragDashboard extends Fragment {

    private Context tContext;
    private FragmentManager tFragmentManager;
    private SharedPrefManager tSharedPrefManager;
    @BindView(R.id.tv_dashboard_user_name)
    protected TextView tvDashboardUserName;
    @BindView(R.id.tv_dash_attendance)
    protected TextView tvDashAttendance;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dashboard, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        tFragmentManager = getFragmentManager();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        checkLocationPermission();
        SetTitle.tbTitle("Dashboard", getActivity());
        tSharedPrefManager = new SharedPrefManager(tContext);
        tvDashboardUserName.setText("Hello "+tSharedPrefManager.getUserName());
    }
    @OnClick(R.id.iv_dash_attendance)
    public void dashAttendanceClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragAttendance()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_dash_distance)
    public void disClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragDistance()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_dash_meeting)
    public void meetingClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragMeeting()).addToBackStack(null).commit();
    }
 @OnClick(R.id.iv_dash_route)
    public void orderClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragRoute()).addToBackStack(null).commit();
    }

    @OnClick(R.id.iv_dash_lead_generation)
    public void leadClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragLead()).addToBackStack(null).commit();
    }
// @OnClick(R.id.iv_dash_receipt)
//    public void receiptClicked(View view){
//        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragReceipt()).addToBackStack(null).commit();
//    }
 @OnClick(R.id.iv_dash_cold_call)
    public void coldClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragColdCall()).addToBackStack(null).commit();
    }
 @OnClick(R.id.iv_dash_cash_collection)
    public void cashClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragCash()).addToBackStack(null).commit();
    }
 @OnClick(R.id.iv_dash_expenses)
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



}
