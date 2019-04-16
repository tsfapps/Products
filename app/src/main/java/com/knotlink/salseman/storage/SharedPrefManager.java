package com.knotlink.salseman.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.knotlink.salseman.utils.Constant;


public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private Context tContext;

    private Editor tEditor;


    public SharedPrefManager(Context tContext) {
        this.tContext = tContext;
    }

    public static synchronized SharedPrefManager getInstance(Context tCtx){
        if (mInstance == null){
            mInstance = new SharedPrefManager(tCtx);

        }
        return mInstance;
    }


    public void setBannerId(String id){

    }
    public void setUserData(String strUserId, String strName, String strAddress, String strPhone, String strEmail,
                                  String strAdhar, String strAsmId, String strSmId, String strDob,
                                  String strDoj, String strImage, String strVehicleNo, String strUserType) {
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
       // tEditor.putInt(Constant.ID, intId);
        tEditor.putString(Constant.USER_ID, strUserId);
        tEditor.putString(Constant.USER_NAME, strName);
        tEditor.putString(Constant.USER_ADDRESS, strAddress);
        tEditor.putString(Constant.USER_PHONE_NO, strPhone);
        tEditor.putString(Constant.USER_EMAIL, strEmail);
        tEditor.putString(Constant.USER_ADHAR_NO, strAdhar);
        tEditor.putString(Constant.USER_ASM_ID, strAsmId);
        tEditor.putString(Constant.USER_SM_ID, strSmId);
        tEditor.putString(Constant.USER_DOB, strDob);
        tEditor.putString(Constant.USER_DOJ, strDoj);
        tEditor.putString(Constant.USER_VEHICLE_NO, strVehicleNo);
        tEditor.putString(Constant.USER_IMAGE, strImage);
        tEditor.putString(Constant.USER_TYPE, strUserType);
        tEditor.apply();
    }


    public void clearUserData(){
        SharedPreferences tPref = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tPref.edit();
       // tEditor.remove(Constant.ID);
        tEditor.remove(Constant.USER_ID);
        tEditor.remove(Constant.USER_NAME);
        tEditor.remove(Constant.USER_ADDRESS);
        tEditor.remove(Constant.USER_PHONE_NO);
        tEditor.remove(Constant.USER_EMAIL);
        tEditor.remove(Constant.USER_ADHAR_NO);
        tEditor.remove(Constant.USER_ASM_ID);
        tEditor.remove(Constant.USER_SM_ID);
        tEditor.remove(Constant.USER_DOB);
        tEditor.remove(Constant.USER_DOJ);
        tEditor.remove(Constant.USER_VEHICLE_NO);
        tEditor.remove(Constant.USER_IMAGE);
        tEditor.remove(Constant.USER_TYPE);
        tEditor.apply();
        tEditor.clear();
    }

//    public int getId(){
//        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
//       return sp.getInt(Constant.ID, -1);
//    }
    public String getUserId(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_ID, Constant.EMPTY);
    }
    public String getUserName(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_NAME, Constant.EMPTY);
    }
    public String getUserAddress(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_ADDRESS, Constant.EMPTY);
    }
    public String getUserPhone(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_PHONE_NO, Constant.EMPTY);
    }
    public String getUserEmail(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.USER_EMAIL, Constant.EMPTY);
    }

    public String getUserAdhar(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_ADHAR_NO, Constant.EMPTY);
    }
    public String getUserDob(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_DOB, Constant.EMPTY);
    }
    public String getUserDoj(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_DOJ, Constant.EMPTY);
    }
    public String getUserImage(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_IMAGE, Constant.EMPTY);
    }
    public String getUserAsmId(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_ASM_ID, Constant.EMPTY);
    }
    public String getUserSmId(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_SM_ID, Constant.EMPTY);
    }

    public String getUserVehilceNo(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_VEHICLE_NO, Constant.EMPTY);
    }

    public String getUserType(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getString(Constant.USER_TYPE, Constant.EMPTY);
    }

    public void clear(){
        SharedPreferences sharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}