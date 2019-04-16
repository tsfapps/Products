package com.knotlink.salseman.utils;

import android.app.Activity;

import com.knotlink.salseman.activity.MainActivity;

public class SetTitle {
    public static void tbTitle(String strTitle, Activity activity){
        MainActivity tActivity = (MainActivity) activity;
        if (tActivity != null) {
            tActivity.setToolbarTitle(strTitle);
        }
    }
}
