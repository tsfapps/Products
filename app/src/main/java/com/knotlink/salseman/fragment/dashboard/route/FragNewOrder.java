package com.knotlink.salseman.fragment.dashboard.route;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.dashboard.FragDashboard;
import com.knotlink.salseman.fragment.dashboard.FragMeeting;
import com.knotlink.salseman.model.dash.ModelActiveStatus;
import com.knotlink.salseman.model.dash.route.ModelNewOrder;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.GetImei;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragNewOrder extends Fragment {
    final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 23;
    Uri imageUri = null;
    public static String CapturedImageDetails;
    public static String Path;
    public static String fileName;


    private Context tContext;
    private FragmentManager tFragmentManager;
    private Bitmap tBitmap;
    private String strLat;
    private String strLong;
    private String strAddress;
    private String strUserId;
    private String strShopId;
    private String strStatusCheckDate;
    private Uri tImageUri;

    final Calendar myCalendar = Calendar.getInstance();

    @BindView(R.id.iv_upload_order)
    protected ImageView ivUploadOrder;
    @BindView(R.id.tvOrderSignMessage)
    protected TextView tvGetSignature;
//    @BindView(R.id.signature_view_order)
//    protected SignatureView signatureViewOrder;
    @BindView(R.id.signature_view_order)
    protected SignatureView signature_view_order;
    @BindView(R.id.sv_order)
    protected ScrollView svOrder;

    @BindView(R.id.tv_order_shop_name)
    protected TextView tvOrderShopName;

    @BindView(R.id.tv_order_date_of_delivery)
    protected TextView tvOrderDateOfDelivery;
    @BindView(R.id.et_order_remarks)
    protected EditText etOrderRemarks;

    private String tTitle;


    private List<ModelShopList> tModels;
    private int i;
    private String strUserType;
    private String strAreaStatus;
    private String strSelectedUserId;
    private String strAttDate;

    public static FragNewOrder newInstance(String strAttDate, String strSelectedUserId, List<ModelShopList> tModels, int i, String strUserType, String strAreaStatus) {

        FragNewOrder fragment = new FragNewOrder();
        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strAttDate = strAttDate;
        fragment.strUserType = strUserType;
        fragment.strAreaStatus = strAreaStatus;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_order, container, false);
        ButterKnife.bind(this,view);
        initFrag();
        return view;
    }
    public void initFrag() {
        tContext = getContext();
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId  = tSharedPrefManager.getUserId();
        tFragmentManager = getFragmentManager();
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strAddress = tGpsTracker.getAddressLine(tContext);

        SetTitle.tbTitle("New Order", getActivity());
        tvOrderShopName.setText(tModels.get(i).getShopName());
        strShopId = tModels.get(i).getShopId();
        String strTodayDate = DateUtils.getTodayDate();
        tvOrderDateOfDelivery.setText(DateUtils.getDeliveryDate(strTodayDate));
        checkStatusApi();
    }
    public void checkStatusApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelActiveStatus> call = api.activeStatus(GetImei.getDeviceIMEI(tContext, getActivity()), strUserId);
        call.enqueue(new Callback<ModelActiveStatus>() {
            @Override
            public void onResponse(@NonNull Call<ModelActiveStatus> call, @NonNull Response<ModelActiveStatus> response) {
               ModelActiveStatus tModels = response.body();
                strStatusCheckDate = tModels.getDate();
            }
            @Override
            public void onFailure(Call<ModelActiveStatus> call, Throwable t) {
            }
        });
    }
    @OnClick(R.id.tvOrderSelectProduct)
    public void tvOrderSelectProductClicked(){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragSelectOrder()).addToBackStack(null).commit();
    }
    @OnClick(R.id.tv_order_date_of_delivery)
    public void deliverDateClicked(View view)
    {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String strCurrentDate = DateUtils.getTodayDate();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
                try {
                    Date myDate = sdf.parse(strCurrentDate);
                    String strMyDate = sdf.format(myCalendar.getTime());
                    Date selDate = sdf.parse(strMyDate);
                    if (selDate.compareTo(myDate)>0) {
                        tvOrderDateOfDelivery.setText(strMyDate);
                    }
                    else {
                        CustomToast.toastMid(getActivity(), Constant.DATE_DELIVERY);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.iv_order_clear_sign)
    public void clrSignClicked(View view){
        signature_view_order.clearCanvas();
    }


    private void confirmSubmit() {
        AlertDialog.Builder submitDialog = new AlertDialog.Builder(
                tContext);
        submitDialog.setTitle("Confirm Submit...");
        submitDialog.setMessage("Are you sure you want submit this order?");
        submitDialog.setIcon(R.drawable.ic_sign_right);
        submitDialog.setPositiveButton("SUBMIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callApi();
                    }
                });

        submitDialog.setNegativeButton("EDIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        submitDialog.show();
    }
    @OnClick(R.id.btn_order_submit)
    public void btnSubmitClicked(View view){
        if (signature_view_order.isBitmapEmpty()){
            CustomToast.toastBottom(getActivity(), "Can't submit without signature...");
        }
        else {
              confirmSubmit();
        }

    }
    @OnTouch(R.id.signature_view_order)
    public boolean onTouchSign(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                svOrder.requestDisallowInterceptTouchEvent(true);
                return false;
            case MotionEvent.ACTION_UP:
                svOrder.requestDisallowInterceptTouchEvent(false);
                return true;
            default:
                return true;
        }
    }
    @OnClick(R.id.iv_upload_order)
    public void onUploadOrderClick() {
        if (tContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            if (!shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                fileName = "Camera_Example.jpg";

                // Create parameters for Intent with filename

                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE, fileName);

                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");

                imageUri = tContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (CheckPermission.isCameraAllowed(tContext)) {
                    Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 10);

                    startActivityForResult( intent, Constant.CAMERA);

                    return;
                }
                CheckPermission.requestCameraPermission(getActivity());

            }
            return;
        }
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constant.CAMERA) {

            if ( resultCode == RESULT_OK) {

                String imageId = convertImageUriToFile( imageUri,getActivity());


                //  Create and excecute AsyncTask to load capture image

                new FragNewOrder.LoadImagesFromSDCard().execute(""+imageId);

            } else if ( resultCode == RESULT_CANCELED) {

                Toast.makeText(tContext, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(tContext, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String [] proj={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {


            }
            else
            {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID     = cursor.getInt(columnIndex);

                    thumbID     = cursor.getInt(columnIndexThumb);

                    Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    CapturedImageDetails  = " CapturedImageDetails : \n\n"
                            +" ImageID :"+imageID+"\n"
                            +" ThumbID :"+thumbID+"\n"
                            +" Path :"+Path+"\n";

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return ""+imageID;
    }
    public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(tContext);



        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {
                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                bitmap = BitmapFactory.decodeStream(tContext.getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        tBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if(tBitmap != null)
            {
                // Set Image to ImageView

                ivUploadOrder.setImageBitmap(tBitmap);
            }

        }

    }




    private String signImageToString(){
        Bitmap bitmapSign = signature_view_order.getSignatureBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapSign.compress(Bitmap.CompressFormat.PNG,10,byteArrayOutputStream);
        byte[] imByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imByte,Base64.DEFAULT);
    }
public void callApi(){
        Log.d(Constant.TAG, "Status Date : "+strAttDate);
        String strDol = tvOrderDateOfDelivery.getText().toString().trim();
        String strRemarks = etOrderRemarks.getText().toString().trim();
        String strImage = imageToString(tBitmap, ivUploadOrder);
        String strImageSign = signImageToString();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelNewOrder> call = api.uploadNewOrder(strAttDate, strUserId, strShopId, strDol,
                strRemarks, strImageSign, strImage, strLat, strLong, strAddress, strAreaStatus);
        call.enqueue(new Callback<ModelNewOrder>() {
        @Override
        public void onResponse(Call<ModelNewOrder> call, Response<ModelNewOrder> response) {
            ModelNewOrder tModels = response.body();
            if (!tModels.getError()) {
                CustomToast.toastMid(getActivity(), tModels.getMessage());
                getFragmentManager().beginTransaction().remove(FragNewOrder.this).commit();
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
            }
            else {
                CustomToast.toastMid(getActivity(), tModels.getMessage());
            }
        }
        @Override
        public void onFailure(Call<ModelNewOrder> call, Throwable t) {
            CustomLog.d(Constant.TAG, "New Order Not Responding :"+t);
        }
    });
}
    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}


