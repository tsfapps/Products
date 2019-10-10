package com.knotlink.salseman.fragment.dashboard;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.distance.ModelDistance;
import com.knotlink.salseman.model.dash.route.ModelVehicleList;
import com.knotlink.salseman.model.distance.ModelDistancePrevious;
import com.knotlink.salseman.model.distance.ModelDistanceUpdate;
import com.knotlink.salseman.model.distance.ModelStartKm;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;



import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragDistance extends Fragment {



    @BindView(R.id.iv_upload_start)
    protected ImageView ivUploadStart;
    @BindView(R.id.btn_distance_submitStart)
    protected Button btn_distance_submitStart;
    @BindView(R.id.btn_distance_submitEnding)
    protected Button btn_distance_submitEnding;
    @BindView(R.id.iv_upload_end)
    protected ImageView ivUploadEnd;
    @BindView(R.id.et_distance_startKm)
    protected EditText etDistanceStartKm;
    @BindView(R.id.et_distance_endingKm)
    protected EditText etDistanceEndingKm;
    @BindView(R.id.tv_distance_vehicleNumber)
    protected TextView tv_distance_vehicleNumber;
    @BindView(R.id.tvDistancePrevious)
    protected TextView tvDistancePrevious;
    @BindView(R.id.tvDistanceExcess)
    protected TextView tvDistanceExcess;
    private Context tContext;
    private Bitmap tBitmapStart;
    private Bitmap tBitmapEnd;

    private SharedPrefManager tSharedPrefManager;

    private Drawable drawable;
    private String strVehicleNo;
    private String strStartKm;
    private String strEndingKm;
    private List<ModelVehicleList> tLists;
    private int i;
    private String strUserType;

    public static FragDistance newInstance(List<ModelVehicleList> tLists, int i, String strUserType) {

        FragDistance fragment = new FragDistance();
        fragment.tLists = tLists;
        fragment.i = i;
        fragment.strUserType = strUserType;
        return fragment;
    }
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
        etDistanceStartKm.setText(tSharedPrefManager.getStartKm());
        if (!tSharedPrefManager.getStartKm().equals("")){
            btn_distance_submitStart.setVisibility(View.GONE);
            btn_distance_submitEnding.setVisibility(View.VISIBLE);
            strStartKm = tSharedPrefManager.getStartKm();
        }

//            ivUploadStart.setImageBitmap(tSharedPrefManager.getStartImage());
//        }
        if (!tSharedPrefManager.getVehicleNo().equals("")){
            tv_distance_vehicleNumber.setText(tSharedPrefManager.getVehicleNo());
            callPreviousApi(tSharedPrefManager.getVehicleNo());
        }else {
                strVehicleNo = tLists.get(i).getVehicleNo();
                tv_distance_vehicleNumber.setText(strVehicleNo);
                callPreviousApi(strVehicleNo);
        }
    }
    private void callPreviousApi(final String vehicleNumber){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelDistancePrevious> call = api.getDistancePrevious(vehicleNumber);
        call.enqueue(new Callback<ModelDistancePrevious>() {
            @Override
            public void onResponse(Call<ModelDistancePrevious> call, Response<ModelDistancePrevious> response) {
                ModelDistancePrevious tModel = response.body();
                tvDistancePrevious.setText(tModel.getDistanceTraveled());
                tvDistanceExcess.setText(tModel.getExcessDistance());
                if (!tSharedPrefManager.getStartKm().equals("")){
                    callStartKmApi(vehicleNumber);

                }else {
                    etDistanceStartKm.setText(tModel.getStartingKm());
                }
            }

            @Override
            public void onFailure(Call<ModelDistancePrevious> call, Throwable t) {
                Log.d(Constant.TAG, "Previous Failure : "+t);

            }
        });
    }
    private void callStartKmApi(String vehicleNumber){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelStartKm> call = api.getStartKm(vehicleNumber);
        call.enqueue(new Callback<ModelStartKm>() {
            @Override
            public void onResponse(Call<ModelStartKm> call, Response<ModelStartKm> response) {
                ModelStartKm tModel = response.body();
                etDistanceStartKm.setText(tModel.getStartingKm());
                Glide.with(tContext).load(Constant.URL_IMG_DISTANCE +tModel.getStartingImage()).into(ivUploadStart);
            }

            @Override
            public void onFailure(Call<ModelStartKm> call, Throwable t) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.iv_upload_start)
    public void onUploadClick(View view){
//       showPictureDialog();
        takePhotoFromCamera();

        Constant.UPLOAD_START = true;
    }

    @OnClick(R.id.iv_upload_end)
    public void onUploadEndClick(View view){
       // showPictureDialog();
        takePhotoFromCamera();
        Constant.UPLOAD_START = false;
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
            if (Constant.UPLOAD_START) {
                SetImage.setCameraImage(ivUploadStart, data);
                tBitmapStart = (Bitmap) data.getExtras().get("data");
                ivUploadStart.setImageBitmap(tBitmapStart);
            }else {
                SetImage.setCameraImage(ivUploadEnd, data);
                tBitmapEnd = (Bitmap) data.getExtras().get("data");
                ivUploadEnd.setImageBitmap(tBitmapEnd);
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


    private void callStartingApi() {
        final String strImgStart = imageToString(tBitmapStart, ivUploadStart);
        strStartKm = etDistanceStartKm.getText().toString().trim();
        if (strImgStart.equals("")){
            CustomToast.toastTop(getActivity(), "Please upload your speedometer image before starting the trip...");
        }
        else
       if (strStartKm.equals("")) {
            etDistanceStartKm.setError("Enter the last four digit of speedometer reading...");
        } else {
           GPSTracker gpsTracker = new GPSTracker(tContext);
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                String stringLongitude = String.valueOf(gpsTracker.longitude);
                String strCity = gpsTracker.getCity(tContext);
                String strPinCode = gpsTracker.getPostalCode(tContext);
                String addressLine = gpsTracker.getAddressLine(tContext);
                String strUserId = tSharedPrefManager.getUserId();
                Api api = ApiClients.getApiClients().create(Api.class);
                Call<ModelDistance> call = api.uploadDistance(strUserId, strStartKm, strImgStart, stringLatitude, stringLongitude, strPinCode, strCity, addressLine, strVehicleNo);
                call.enqueue(new Callback<ModelDistance>() {
                    @Override
                    public void onResponse(Call<ModelDistance> call, Response<ModelDistance> response) {
                        ModelDistance tModels = response.body();
                        if (!tModels.getError()) {
                            CustomToast.toastTop(getActivity(), tModels.getMessage());
                            getFragmentManager().beginTransaction().remove(FragDistance.this).commit();
                            getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strUserType)).commit();
                            btn_distance_submitStart.setVisibility(View.GONE);
                            btn_distance_submitEnding.setVisibility(View.VISIBLE);
                            tSharedPrefManager.setStartingKm(strStartKm, strVehicleNo, strImgStart);

                        } else {
                            CustomToast.toastTop(getActivity(), tModels.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelDistance> call, Throwable t) {
                        CustomLog.e(Constant.TAG, "Not Responding : " + t);
                    }
                });
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
    }
    //
    @OnClick(R.id.btn_distance_submitStart)
    public void uploadStartData(View view){
        callStartingApi();
    }

    private void callEndingApi(){
        String strVehicleNumber = tv_distance_vehicleNumber.getText().toString().trim();
        CustomLog.e(Constant.TAG, "Vehicle Number : "+strVehicleNumber);
        strEndingKm = etDistanceEndingKm.getText().toString().trim();
        String strImgEnd = imageToString(tBitmapEnd, ivUploadEnd);
        if (strImgEnd.equals("")){
            CustomToast.toastTop(getActivity(), "Upload the speedometer image before ending the trip...");
        }
        else
        if (strEndingKm.equals("")){
            etDistanceEndingKm.setError("Enter the last four digit of speedometer reading...");
        }
        else
        if (Integer.parseInt(strEndingKm) < Integer.parseInt(strStartKm))
        {
            etDistanceEndingKm.setError("Ending kilometer must be greater than starting kilometer...");
        }
        else {
            final int totalDistance = Integer.parseInt(strEndingKm) - Integer.parseInt(strStartKm);
            GPSTracker gpsTracker = new GPSTracker(tContext);
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);
                String stringLongitude = String.valueOf(gpsTracker.longitude);
                String strCity = gpsTracker.getCity(tContext);
                String strPinCode = gpsTracker.getPostalCode(tContext);
                String addressLine = gpsTracker.getAddressLine(tContext);
                String strUserId = tSharedPrefManager.getUserId();

                Api api = ApiClients.getApiClients().create(Api.class);
                Call<ModelDistanceUpdate> call = api.uploadDistanceEnding(strUserId, strEndingKm, strImgEnd, stringLatitude, stringLongitude, strPinCode, strCity, addressLine, strVehicleNumber);
                call.enqueue(new Callback<ModelDistanceUpdate>() {
                    @Override
                    public void onResponse(Call<ModelDistanceUpdate> call, Response<ModelDistanceUpdate> response) {
                        ModelDistanceUpdate tModels = response.body();
                        if (!tModels.getError()) {
                            CustomToast.toastTop(getActivity(), "Today you travelled "+totalDistance+" KM");
                            tSharedPrefManager.clearDistanceStatus();
                            getFragmentManager().beginTransaction().remove(FragDistance.this).commit();
                            getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strUserType)).commit();

                        } else {
                            CustomToast.toastTop(getActivity(), tModels.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelDistanceUpdate> call, Throwable t) {
                        CustomLog.e(Constant.TAG, "Not Responding : " + t);
                    }
                });
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
    }
    @OnClick(R.id.btn_distance_submitEnding)
    public void uploadEndData(View view){
            callEndingApi();
    }

    public static Bitmap getBitImage(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    private void getLocation(){
        GPSTracker gpsTracker = new GPSTracker(tContext);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);

            String country = gpsTracker.getCountryName(tContext);

            String city = gpsTracker.getCity(tContext);

            String postalCode = gpsTracker.getPostalCode(tContext);

            String addressLine = gpsTracker.getAddressLine(tContext);

        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }
}
