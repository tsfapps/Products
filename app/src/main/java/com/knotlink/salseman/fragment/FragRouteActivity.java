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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterFeedback;
import com.knotlink.salseman.adapter.AdapterSpecialRequest;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelFeedback;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.model.ModelSpecialRequest;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragRouteActivity extends Fragment {

    Context tContext;
    private List<ModelShopList> tModels;
    private ModelSpecialRequest tSpecialModels;
    private ModelFeedback tModelsFeedback;
    private SharedPrefManager tSharedPrefManger;
    private int i;
    private String[] title;
    private String[] strFeedback;
    private String spinner_item;
   // private String spinner_item_feedback;
    private AdapterSpecialRequest tAdapter;
    private AdapterFeedback tAdapterFeedback;
    private EditText edittext;
    private EditText  etFeedback;
    private Spinner spinner;
    private Bitmap tBitmap;
    private ImageView ivFeedback;
   // private Spinner spnFeedback;


    public static FragRouteActivity newInstance(List<ModelShopList> tModels, int i) {
        FragRouteActivity fragment = new FragRouteActivity();
        fragment.tModels = tModels;
        fragment.i = i;
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
        title = getResources().getStringArray(R.array.special_request);
        tAdapter=new AdapterSpecialRequest(tContext, title);
        strFeedback = getResources().getStringArray(R.array.str_arr_feedback);
        tAdapterFeedback=new AdapterFeedback(tContext, strFeedback);
        SetTitle.tbTitle("Route Activity", getActivity());
    }

    @OnClick(R.id.iv_route_new_order)
    public void routeOrderClicked(View view){
        getFragmentManager().beginTransaction().replace(R.id.container_main, FragNewOrder.newInstance(tModels, i)).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_dash_receipt)
    public void receiptClicked(View view){
        getFragmentManager().beginTransaction().replace(R.id.container_main, FragReceipt.newInstance(tModels, i)).addToBackStack(null).commit();
    }

    //Special request clicked
    @OnClick(R.id.iv_route_special_request)
    public void requestClicked(View view) {
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_special_req);
        dialog.setTitle("Special Request");
        dialog.setCancelable(true);

        // set the custom dialog components - text, image and button
        spinner =  dialog.findViewById(R.id.spn_specialRequest);
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
                callApi();
            }
        });
        dialog.show();
    }

    //Special Request Api Call
    private void callApi(){
        String strSHopId = tModels.get(i).getShopId();
        String strRemarks = edittext.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelSpecialRequest> requestCall = api.uploadSpecialRequest(spinner_item, strSHopId, strRemarks);
        requestCall.enqueue(new Callback<ModelSpecialRequest>() {
            @Override
            public void onResponse(Call<ModelSpecialRequest> call, Response<ModelSpecialRequest> response) {
                tSpecialModels = response.body();
                if (!tSpecialModels.getError()){
                    CustomToast.toastTop(tContext, tSpecialModels.getMessage());
                }
                else {
                    CustomToast.toastTop(tContext, tSpecialModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelSpecialRequest> call, Throwable t) {
            }
        });
    }
    //Feedback clicked
    @OnClick(R.id.iv_route_complain)
    public void feedbackSubmit(View view) {
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_feedback);
        dialog.setTitle("Special Request");
        dialog.setCancelable(true);

        // set the custom dialog components - text, image and button
//        spnFeedback =  dialog.findViewById(R.id.spn_feedback);
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
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        tBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imByte,Base64.DEFAULT);
    }

    //Feedback Api Call
    private void callApiFeedback(){
        String strUserId = tSharedPrefManger.getUserId();
        String strSHopId = tModels.get(i).getShopId();
        String strRemarks = etFeedback.getText().toString().trim();
        String strImage = imageToString();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelFeedback> requestCall = api.uploadFeedback(strSHopId, strUserId,strImage, strRemarks);
        requestCall.enqueue(new Callback<ModelFeedback>() {
            @Override
            public void onResponse(Call<ModelFeedback> call, Response<ModelFeedback> response) {
                tModelsFeedback = response.body();
                if (!tModelsFeedback.getError()){
                    CustomToast.toastTop(tContext, tModelsFeedback.getMessage());
                }
                else {
                    CustomToast.toastTop(tContext, tModelsFeedback.getMessage());
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
