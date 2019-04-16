package com.knotlink.salseman.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {
//    private Context tCtx;
//    private String message;
//
//    public CustomToast(Context tCtx, String message) {
//        this.tCtx = tCtx;
//        this.message = message;
//    }

//    public st CustomToast(Context tCtx) {
//        this.tCtx = tCtx;
//    }

    public static void toastTop(Context tCtx, String message){
        Toast toast = Toast.makeText(tCtx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }
    public static void toastMid(Context tCtx, String message){
        Toast toast = Toast.makeText(tCtx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSetMid);
        toast.show();
    }

    public static void toastBottom(Context tCtx, String strMsg){
        Toast.makeText(tCtx, strMsg, Toast.LENGTH_SHORT).show();
    }
    public static void toastTotal(Context tCtx, String strMsg){
        Toast toast = Toast.makeText(tCtx, "Total : "+strMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }
}
