package com.knotlink.salseman.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

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

    //Distance Upload
    public void setStartingKm(String startTime, String vehicleNo, String strImageStart){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.START_KM, startTime);
        tEditor.putString(Constant.START_VEHICLE_NO, vehicleNo);
        tEditor.putString(Constant.IS_UPLOAD_IMAGE, strImageStart);
       // tEditor.putString(Constant.ENDING_KM, startTimeLabel);
        tEditor.apply();
    }
    public String getStartKm(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.START_KM, Constant.EMPTY);
    }
    public Bitmap getStartImage(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);

        String previouslyEncodedImage = sp.getString(Constant.START_IMAGE, Constant.EMPTY);

        if( !previouslyEncodedImage.equalsIgnoreCase("") ) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return  bitmap;
        }

        return null;
    }
    public boolean getIsImageUpload(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getBoolean(Constant.IS_UPLOAD_IMAGE, Constant.EMPTY_BOOL);
    }
    public String getEndingKm(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.ENDING_KM, Constant.EMPTY);
    }
    public String getVehicleNo(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.START_VEHICLE_NO, Constant.EMPTY);
    }
    public void clearDistanceStatus(){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.remove(Constant.START_KM);
        tEditor.remove(Constant.START_VEHICLE_NO);
        tEditor.remove(Constant.IS_UPLOAD_IMAGE);
        tEditor.apply();
        tEditor.clear();
    }


    //Attendance
    public void setShiftTime(String startTime, String startTimeLabel){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.START_TIME, startTime);
        tEditor.putString(Constant.START_TIME_LABEL, startTimeLabel);
        tEditor.apply();
    }
    public String getShiftTime(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.START_TIME, Constant.EMPTY);
    }

    public void setReportTimeStart(String startTime){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.REPORT_TIME_START, startTime);
        tEditor.apply();
    }
    public void setStartImage(String strEncodedImg){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
         tEditor=tSharedPreferences.edit();
        tEditor.putString(Constant.START_IMAGE,strEncodedImg);
        tEditor.apply();


    }
    public void setReportTimeEnd(String startEndTime){
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putString(Constant.REPORT_TIME_END, startEndTime);
        tEditor.apply();
    }
    public String getReportTimeFrom(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.REPORT_TIME_START, Constant.EMPTY);
    }
    public String getReportTimeTo(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.REPORT_TIME_END, Constant.EMPTY);
    }
    public void clearReportTime(){
        SharedPreferences tPref = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tPref.edit();
        tEditor.remove(Constant.REPORT_TIME_START);
        tEditor.remove(Constant.REPORT_TIME_END);
        tEditor.apply();
        tEditor.clear();
    }
    public void clearReportTimeStart(){
        SharedPreferences tPref = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tPref.edit();
        tEditor.remove(Constant.REPORT_TIME_START);
        tEditor.apply();
        tEditor.clear();
    }
    public void clearReportTimeEnd(){
        SharedPreferences tPref = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tPref.edit();
        tEditor.remove(Constant.REPORT_TIME_END);
        tEditor.apply();
        tEditor.clear();
    }

    //Attendance
    public String getShiftTimeLabel(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        return sp.getString(Constant.START_TIME_LABEL, Constant.EMPTY);
    }

    public void setStatus(Boolean status)
    {
        SharedPreferences tSharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        tEditor = tSharedPreferences.edit();
        tEditor.putBoolean(Constant.CHECK_IN_STATUS, status);
        tEditor.apply();

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
    public Boolean getCheckInStatus(){
        SharedPreferences sp = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
       return sp.getBoolean(Constant.CHECK_IN_STATUS, Constant.EMPTY_BOOL);
    }

    public void clear(){
        SharedPreferences sharedPreferences = tContext.getSharedPreferences(Constant.TSF_SHARED_PREFENCE, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}