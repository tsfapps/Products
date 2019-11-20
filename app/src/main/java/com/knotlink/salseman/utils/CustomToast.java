package com.knotlink.salseman.utils;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {

    public static void toastTop(Activity tActivity, String message) {
        Toast toast = Toast.makeText(tActivity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }

    public static void toastMid(Activity tActivity, String message) {
        Toast toast = Toast.makeText(tActivity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSetMid);
        toast.show();
    }

    public static void toastBottom(Activity tCtx, String strMsg) {
        Toast.makeText(tCtx, strMsg, Toast.LENGTH_SHORT).show();
    }

    public static void toastTotal(Activity tCtx, String strMsg) {
        Toast toast = Toast.makeText(tCtx, "Total : " + strMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }
}
