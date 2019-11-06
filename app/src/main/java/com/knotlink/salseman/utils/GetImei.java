package com.knotlink.salseman.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

public class GetImei {
    public static String getDeviceIMEI(Context tContext, Activity tActivity) {
        int permissionCheck = ContextCompat.checkSelfPermission(tContext, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(tActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, Constant.REQUEST_READ_PHONE_STATE);
            return "Permission not granted";
        }
        else {
            String deviceUniqueIdentifier = null;
            TelephonyManager tm = (TelephonyManager) tContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = Settings.Secure.getString(tContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return deviceUniqueIdentifier;
        }

    }

}
