package com.knotlink.salseman.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterSpecialRequest;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelFeedback;
import com.knotlink.salseman.model.ModelGetLocaion;
import com.knotlink.salseman.model.ModelNoActivity;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.model.ModelSpecialRequest;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DistanceLatLong;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragRouteActivity extends Fragment {

    private Context tContext;
    private List<ModelShopList> tModels;
    private ModelSpecialRequest tSpecialModels;
    private ModelFeedback tModelsFeedback;
    private SharedPrefManager tSharedPrefManger;

    //Status
   private String strStatusNoActivity;
   private String strStatusOrder;
   private String strStatusReceipt;
   private String strStatusRequest;
   private String strStatusFeedback;

    private String[] title;
    private String[] strFeedback;
    private String spinner_item;
    private String strShopId;
    private String strUserId;
    private String strShopLat;
    private String strShopLong;
   // private String spinner_item_feedback;
    private AdapterSpecialRequest tAdapter;
    private EditText edittext;
    private EditText etFeedback;
    private Bitmap tBitmap;
    private ImageView ivFeedback;
   // private Spinner spnFeedback;

    private GPSTracker tGpsTracker;
    private String strLat;
    private String strLong;
    private String strPinCode;
    private String strCity;
    private String strAdders;
    private String strAreaStatus;
    private double shopLat;
    private double shopLong;
    private double myLat;
    private double myLong;

    @BindView(R.id.btnGetLocation)
    protected Button btnGetLocation;
    @BindView(R.id.btnNoActivity)
    protected Button btnNoActivity;
    @BindView(R.id.tvRouteActivityOrder)
    protected TextView tvRouteActivityOrder;
    @BindView(R.id.tvStatusOrder)
    protected TextView tvStatusOrder;
    @BindView(R.id.tvStatusReceipt)
    protected TextView tvStatusReceipt;
    @BindView(R.id.tvStatusRequest)
    protected TextView tvStatusRequest;
    @BindView(R.id.tvStatusFeedback)
    protected TextView tvStatusFeedback;
    @BindView(R.id.tvRouteActivityShopName)
    protected TextView tvRouteActivityShopName;
    private int i;
    private String strUserType;

    public static FragRouteActivity newInstance(List<ModelShopList> tModels, int i, String strUserType) {
        FragRouteActivity fragment = new FragRouteActivity();
        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strUserType = strUserType;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_route_activity, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        tSharedPrefManger = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManger.getUserId();

        tvRouteActivityShopName.setText(tModels.get(i).getShopName());
        strStatusNoActivity = tModels.get(i).getNoActivityStatus();
        strStatusOrder = tModels.get(i).getNewOrderStatus();
        strStatusReceipt = tModels.get(i).getReceiptStatus();
        strStatusRequest = tModels.get(i).getSpecialRequestStatus();
        strStatusFeedback = tModels.get(i).getComplainStatus();

        if (strStatusNoActivity.equalsIgnoreCase("1")){
            btnNoActivity.setBackgroundResource(R.drawable.bg_dis);
        }else {
            tvStatusOrder.setBackgroundResource(R.drawable.bg_btn_main);
        } if (strStatusOrder.equalsIgnoreCase("1")){
            tvStatusOrder.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusOrder.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusReceipt.equalsIgnoreCase("1")){
            tvStatusReceipt.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusReceipt.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusRequest.equalsIgnoreCase("1")){
            tvStatusRequest.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusRequest.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusFeedback.equalsIgnoreCase("1")){
            tvStatusFeedback.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusFeedback.setBackgroundResource(R.drawable.ic_cross);
        }
        title = getResources().getStringArray(R.array.special_request);
        tAdapter=new AdapterSpecialRequest(tContext, title);
        strFeedback = getResources().getStringArray(R.array.str_arr_feedback);
        SetTitle.tbTitle("Route Activity", getActivity());
        strShopId = tModels.get(i).getShopId();
        strShopLat = tModels.get(i).getLatitude();
        strShopLong = tModels.get(i).getLongitude();
        if (!strShopLat.equalsIgnoreCase("0")&& !strShopLong.equalsIgnoreCase("0")){
            btnGetLocation.setVisibility(View.GONE);
        }
        tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strCity = tGpsTracker.getCity(tContext);
        strPinCode = tGpsTracker.getPostalCode(tContext);
        strAdders = tGpsTracker.getAddressLine(tContext);
        if (strUserType.equalsIgnoreCase("1")){
            tvRouteActivityOrder.setText("New Order");
        }
        else if (strUserType.equalsIgnoreCase("2")){
            tvRouteActivityOrder.setText("Sales Return");
        }
         shopLat = Double.parseDouble(strShopLat);
         shopLong = Double.parseDouble(strShopLong);
         myLat = tGpsTracker.latitude;
         myLong = tGpsTracker.longitude;
        double distActivity = DistanceLatLong.distance(shopLat, shopLong, myLat, myLong);
        if (distActivity>0.015){
            strAreaStatus = "0";
        }else {
            strAreaStatus = "1";
        }
    }

    @OnClick(R.id.ll_route_new_order)
    public void routeOrderClicked(View view){
        assert getFragmentManager() != null;
        if (strUserType.equalsIgnoreCase("1")){
            getFragmentManager().beginTransaction().replace(R.id.container_main, FragNewOrder.newInstance(tModels, i, strUserType, strAreaStatus)).addToBackStack(null).commit();

        }else if (strUserType.equalsIgnoreCase("2")){
            getFragmentManager().beginTransaction().replace(R.id.container_main, FragSalesReturn.newInstance(tModels, i, strAreaStatus)).addToBackStack(null).commit();
        }
    }
    @OnClick(R.id.ll_dash_receipt)
    public void receiptClicked(View view){
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.container_main, FragReceipt.newInstance(tModels, i, strAreaStatus)).addToBackStack(null).commit();
    }
    @OnClick(R.id.btnGetLocation)
    public void btnGetLocationClicked(View view){
        callLocationApi();
    }
    @OnClick(R.id.btnNoActivity)
    public void btnNoActivityClicked(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(tContext);
        final EditText etRemarks = new EditText(tContext);
        etRemarks.setBackgroundResource(R.drawable.bg_et);
        etRemarks.setSingleLine();
        FrameLayout container = new FrameLayout(tContext);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
        params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
        params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
        params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_8);
        etRemarks.setLayoutParams(params);
        container.addView(etRemarks);
        alert.setTitle("Mention the reason");
        alert.setView(container);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String strRemark = etRemarks.getText().toString();
                if (!strRemark.equalsIgnoreCase("")) {
                    Api api = ApiClients.getApiClients().create(Api.class);
                    Call<ModelNoActivity> callNoActivity = api.uploadNoActivity(strUserId, strShopId, strRemark, strLat, strLong, strAreaStatus);
                    callNoActivity.enqueue(new Callback<ModelNoActivity>() {
                        @Override
                        public void onResponse(Call<ModelNoActivity> call, Response<ModelNoActivity> response) {
                            ModelNoActivity tModel = response.body();
                            Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<ModelNoActivity> call, Throwable t) {
                        }
                    });
                }else {
                    CustomDialog.showEmptyDialog(tContext);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
    }

    private void callLocationApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelGetLocaion> call =api.updateLocation(strShopId, strAdders, strCity, strPinCode, strLat, strLong);
        call.enqueue(new Callback<ModelGetLocaion>() {
            @Override
            public void onResponse(Call<ModelGetLocaion> call, Response<ModelGetLocaion> response) {
                ModelGetLocaion tModel = response.body();
                assert tModel != null;
                if (!tModel.getError()){
                    CustomToast.toastTop(getActivity(), tModel.getMessage() );
                }
                CustomToast.toastTop(getActivity(), tModel.getMessage() );
            }

            @Override
            public void onFailure(Call<ModelGetLocaion> call, Throwable t) {

                Log.d(Constant.TAG, "Location Update  Failure : "+t);
            }
        });
    }
    //Special request clicked
    @OnClick(R.id.ll_route_special_request)
    public void requestClicked(View view) {
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_special_req);
        dialog.setTitle("Special Request");
        dialog.setCancelable(true);

        // set the custom dialog components - text, image and button
        Spinner spinner = dialog.findViewById(R.id.spn_specialRequest);
        edittext =  dialog.findViewById(R.id.et_specialRequest);
        Button button =  dialog.findViewById(R.id.btn_specialRequest);
        spinner.setAdapter(tAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                spinner_item = title[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callSpecialRequestApi();
            }
        });
        dialog.show();
    }

    //Special Request Api Call
    private void callSpecialRequestApi(){
        String strRemarks = edittext.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelSpecialRequest> requestCall = api.uploadSpecialRequest(strUserId, spinner_item, strShopId, strRemarks, strLat, strLong, strAreaStatus);
        requestCall.enqueue(new Callback<ModelSpecialRequest>() {
            @Override
            public void onResponse(Call<ModelSpecialRequest> call, Response<ModelSpecialRequest> response) {
                tSpecialModels = response.body();
                if (!tSpecialModels.getError()){
                    CustomToast.toastTop(getActivity(), tSpecialModels.getMessage());
                }
                else {
                    CustomToast.toastTop(getActivity(), tSpecialModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelSpecialRequest> call, Throwable t) {
            }
        });
    }
    //Feedback clicked
    @OnClick(R.id.ll_route_complain)
    public void feedbackSubmit(View view) {
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.frag_route_activity_feedback);
        dialog.setTitle("Special Request");
        dialog.setCancelable(true);

        ivFeedback = dialog.findViewById(R.id.iv_feedback_image);
        etFeedback =  dialog.findViewById(R.id.et_feedback);
        Button button =  dialog.findViewById(R.id.btn_feedback);


        ivFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callApiFeedback();
            }
        });
        dialog.show();
    }

    //Feedback Api Call
    private void callApiFeedback(){
        String strRemarks = etFeedback.getText().toString().trim();
        String strImage = imageToString(tBitmap, ivFeedback);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelFeedback> requestCall = api.uploadFeedback(strShopId, strUserId, strImage, strRemarks, strLat, strLong, strAreaStatus);
        requestCall.enqueue(new Callback<ModelFeedback>() {
            @Override
            public void onResponse(Call<ModelFeedback> call, Response<ModelFeedback> response) {
                tModelsFeedback = response.body();
                if (!tModelsFeedback.getError()){
                    CustomToast.toastTop(getActivity(), tModelsFeedback.getMessage());
                }
                else {
                    CustomToast.toastTop(getActivity(), tModelsFeedback.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ModelFeedback> call, Throwable t) {

            }
        });
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
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constant.GALLERY);
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
                SetImage.setGalleryImage(tContext, ivFeedback, data);
                BitmapDrawable drawable = (BitmapDrawable)ivFeedback.getDrawable();
                tBitmap = drawable.getBitmap();
            }

        } else if (requestCode == Constant.CAMERA) {
            SetImage.setCameraImage(ivFeedback, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivFeedback.setImageBitmap(tBitmap);
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



}
