package com.knotlink.salseman.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelDistance;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;



import android.content.Intent;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragDistance extends Fragment {



    @BindView(R.id.iv_upload_start)
    protected ImageView ivUploadStart;
    @BindView(R.id.iv_upload_end)
    protected ImageView ivUploadEnd;
    @BindView(R.id.et_distance_startKm)
    protected EditText etDistanceStartKm;
    private Context tContext;
    private Bitmap tBitmap;
    private SharedPrefManager tSharedPrefManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_distance, container, false);
        ButterKnife.bind(this, view);
        initFrag();

        return view;
    }

    private void initFrag(){

        tContext = getContext();
        getLocation();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Upload Distance", getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.iv_upload_start)
    public void onUploadClick(View view){
       showPictureDialog();
       Constant.UPLOAD_START = true;
    }

    @OnClick(R.id.iv_upload_end)
    public void onUploadEndClick(View view){
        showPictureDialog();
        Constant.UPLOAD_START = false;
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Photo Gallery", "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void choosePhotoFromGallery() {
        if (CheckPermission.isReadStorageAllowed(getContext())) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Constant.GALLERY);
            return;
        }
        CheckPermission.requestStoragePermission(getActivity());
    }
    private void takePhotoFromCamera() {
        if (CheckPermission.isCameraAllowed(getContext())) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Constant.CAMERA);
            return;
        }
        CheckPermission.requestCameraPermission(getActivity());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == Constant.GALLERY) {
            if (data != null) {
                if (Constant.UPLOAD_START) {
                    SetImage.setGalleryImage(tContext, ivUploadStart, data);
                    BitmapDrawable drawable = (BitmapDrawable)ivUploadStart.getDrawable();
                    tBitmap = drawable.getBitmap();
                }
                else {
                    SetImage.setGalleryImage(tContext, ivUploadEnd, data);
                }
            }
        } else if (requestCode == Constant.CAMERA) {
            if (Constant.UPLOAD_START) {

                tBitmap = (Bitmap) data.getExtras().get("data");
                ivUploadStart.setImageBitmap(tBitmap);
            }else {
                SetImage.setCameraImage(ivUploadEnd, data);

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "You  denied the permission...", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        tBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imByte,Base64.DEFAULT);
    }

    private void callApi(){

        String strStartKm = etDistanceStartKm.getText().toString().trim();
        GPSTracker gpsTracker = new GPSTracker(tContext);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            String stringLongitude = String.valueOf(gpsTracker.longitude);
            String strCity = gpsTracker.getLocality(tContext);
            String strPinCode = gpsTracker.getPostalCode(tContext);
            String addressLine = gpsTracker.getAddressLine(tContext);
            CustomLog.d(Constant.TAG, "Latitude : "+ stringLatitude +"\nLongitude : "+stringLongitude+"\nAddress Line : "+addressLine);
            String strUserId = tSharedPrefManager.getUserId();

        String strImg = imageToString();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelDistance> call = api.uploadDistance(strUserId, strStartKm, strImg, stringLatitude, stringLongitude,  strPinCode, strCity, addressLine);
        call.enqueue(new Callback<ModelDistance>() {
            @Override
            public void onResponse(Call<ModelDistance> call, Response<ModelDistance> response) {
                ModelDistance tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragDistance .this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelDistance> call, Throwable t) {
                CustomLog.e(Constant.TAG, "Not Responding : "+t);
            }
        });
        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }
    @OnClick(R.id.btn_distance_submit)
    public void uploadImage(View view){
        callApi();

    }

    private void getLocation(){
        GPSTracker gpsTracker = new GPSTracker(tContext);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);

            String country = gpsTracker.getCountryName(tContext);

            String city = gpsTracker.getLocality(tContext);

            String postalCode = gpsTracker.getPostalCode(tContext);

            String addressLine = gpsTracker.getAddressLine(tContext);

            CustomLog.d(Constant.TAG, "Latitude : "+ stringLatitude +"\nLongitude : "+stringLongitude+"\nCountry : "+country
            +"\nCity : "+city+"\nPin Code : "+postalCode+"\nAddress Line : "+addressLine);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }
}
