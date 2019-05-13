package com.knotlink.salseman.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.knotlink.salseman.R;
import com.knotlink.salseman.services.NetworkStateService;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CustomLog;

public class SplashActivity extends AppCompatActivity {
    private SharedPrefManager tSharedPrefManager;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;
    private final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tSharedPrefManager = new SharedPrefManager(getApplicationContext());
        startHandler();
    }

    public void startHandler()
    {
        Handler tHandler = new Handler();
        tHandler.postDelayed(
                new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                if (tSharedPrefManager.getUserId().equalsIgnoreCase("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 2000);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        checkLocationPermissions();
//        if (!isServiceRunning(NetworkStateService.class)) {
//            startNetworkService();
//        }
//    }

//    private void startNetworkService() {
//        Intent intent = new Intent(this, NetworkStateService.class);
//        startService(intent);
//    }

//    private boolean isServiceRunning(Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                CustomLog.d(TAG,"NetworkService is running");
//                return true;
//            }
//        }
//        CustomLog.d(TAG,"NetworkService is not running");
//        return false;
//    }
//    private void checkLocationPermissions() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        } else {
//
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startHandler();
//                } else {
//                    CustomLog.d(TAG,"onRequestPermission result denied finishing activity");
//                    // todo  need to correct logic..
//                  /*  if (sharePref.getPermissionSettingPage()) {
//                        openAppPermissionSetting();
//                    } else {
//                        sharePref.setPermissionSettingPage(true);
//                        finish();
//                    }*/
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
//    }

}
