package com.knotlink.salseman.fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelLead;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragLead extends Fragment {
    private ModelLead tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private Bitmap tBitmap;
    @BindView(R.id.et_lead_orgName)
    protected EditText etLeadOrgName;
    @BindView(R.id.et_lead_contactName)
    protected EditText etLeadContactName;
    @BindView(R.id.et_lead_contactNumber)
    protected EditText etLeadContactNumber;
    @BindView(R.id.et_lead_address)
    protected EditText etLeadAddress;
    @BindView(R.id.et_lead_email)
    protected EditText etLeadEmail;
    @BindView(R.id.et_lead_comments)
    protected EditText etLeadComments;
    @BindView(R.id.btn_lead_submit)
    protected Button btnLeaddSubmit;
    @BindView(R.id.iv_upload_lead)
    protected ImageView ivUploadLead;
    @BindView(R.id.rg_lead)
    protected RadioGroup rgLead;
    String strVendorType;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_lead, container, false);
        ButterKnife.bind(this,view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Lead Generation", getActivity());
        rgLead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_lead_org:
                        CustomToast.toastTop(tContext, "Customer Selected");
                        etLeadOrgName.setHint("Organisation Name*");
                        strVendorType = "Customer";
                        break;
                    case R.id.rb_lead_prospect:
                        CustomToast.toastTop(tContext, "Prospect Selected");
                        etLeadOrgName.setHint("Prospect Name*");
                        strVendorType = "Prospect";
                        break;
                }
            }
        });
    }
    @OnClick(R.id.iv_upload_lead)
    public void onUploadLeadClicked(View view){
        showPictureDialog();
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
                SetImage.setGalleryImage(tContext, ivUploadLead, data);
                BitmapDrawable drawable = (BitmapDrawable)ivUploadLead.getDrawable();
                tBitmap = drawable.getBitmap();

            }

        } else if (requestCode == Constant.CAMERA) {
            SetImage.setCameraImage(ivUploadLead, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivUploadLead.setImageBitmap(tBitmap);
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
    @OnClick(R.id.btn_lead_submit)
    public void leadSubmit(View view){
        callApi();
    }


    private void callApi(){

        String strUserId = tSharedPrefManager.getUserId();
        String strOrgName = etLeadOrgName.getText().toString().trim();
        String strContactName = etLeadContactName.getText().toString().trim();
        String strContactNumber = etLeadContactNumber.getText().toString().trim();
        String strAddress = etLeadAddress.getText().toString().trim();
        String strEmail = etLeadEmail.getText().toString().trim();
        String strRemarks = etLeadComments.getText().toString().trim();
        String strImage = imageToString();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelLead> call = api.uploadLead(strUserId, strVendorType, strOrgName, strContactName, strContactNumber, strAddress, strEmail, strRemarks, strImage);
        call.enqueue(new Callback<ModelLead>() {
            @Override
            public void onResponse(Call<ModelLead> call, Response<ModelLead> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(tContext, tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragLead.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
                }
                else {
                    CustomToast.toastTop(tContext, tModels.getMessage());

                }

            }

            @Override
            public void onFailure(Call<ModelLead> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding Lead : "+t);

            }
        });
    }
}
