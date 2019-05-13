package com.knotlink.salseman.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelExpenses;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragExpenses extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private ModelExpenses tModels;
    @BindView(R.id.iv_upload_expenses)
    protected ImageView ivUploadExpenses;
    @BindView(R.id.et_breakfast)
    protected EditText etBreakfast;
    @BindView(R.id.et_lunch)
    protected EditText etLunch;
    @BindView(R.id.et_dinner)
    protected EditText etDinner;
    @BindView(R.id.et_mobile)
    protected EditText etMobile;
    @BindView(R.id.et_parking)
    protected EditText etParking;
    @BindView(R.id.et_road_toll)
    protected EditText etRoadToll;
    @BindView(R.id.et_petty)
    protected EditText etPetty;
    @BindView(R.id.et_cash)
    protected EditText etCash;
    @BindView(R.id.et_cleaning_items)
    protected EditText etCleaningItem;
    @BindView(R.id.et_petrol)
    protected EditText etPetrol;
    @BindView(R.id.et_others)
    protected EditText etOthers;
    @BindView(R.id.et_remark_expenses)
    protected EditText etRemarksExp;
    @BindView(R.id.tv_total_expenses)
    protected TextView tvTotalExpenses;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_expenses, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag() {
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Daily Expenses", getActivity());
    }


    @OnClick(R.id.btn_total_expenses)
    public void btnTotalExpensesClicked(View view) {

        String strBreakfast = etBreakfast.getText().toString().trim();
        String strLunch = etLunch.getText().toString().trim();
        String strDinner = etDinner.getText().toString().trim();
        String strMobile = etMobile.getText().toString().trim();
        String strParking = etParking.getText().toString().trim();
        String strRoadToll = etRoadToll.getText().toString().trim();
        String strPetty = etPetty.getText().toString().trim();
        String strCash = etCash.getText().toString().trim();
        String strCleaningItem = etCleaningItem.getText().toString().trim();
        String strPetrol = etPetrol.getText().toString().trim();
        String strOthers = etOthers.getText().toString().trim();
        int totalExpenses = Integer.parseInt(strBreakfast) + Integer.parseInt(strLunch) + Integer.parseInt(strDinner) + Integer.parseInt(strMobile) +
                Integer.parseInt(strParking) + Integer.parseInt(strRoadToll) + Integer.parseInt(strPetty)
                + Integer.parseInt(strCash) + Integer.parseInt(strCleaningItem) + Integer.parseInt(strPetrol) + Integer.parseInt(strOthers);
        tvTotalExpenses.setText(String.valueOf(totalExpenses));

    }

    @OnClick(R.id.iv_upload_expenses)
    public void onUploadExpensesClicked(View view) {
        showPictureDialog();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Photo Gallery", "Camera"};
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
                SetImage.setGalleryImage(tContext, ivUploadExpenses, data);
            }

        } else if (requestCode == Constant.CAMERA) {

            SetImage.setCameraImage(ivUploadExpenses, data);
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
    @OnClick(R.id.btn_submit_expenses)
    public void submitExpenses(){
        callApi();
    }
    private void callApi(){
        String strUSerId = tSharedPrefManager.getUserId();
        String strBreakfast = etBreakfast.getText().toString().trim();
        String strLunch = etLunch.getText().toString().trim();
        String strDinner = etDinner.getText().toString().trim();
        String strMobile  = etMobile.getText().toString().trim();
        String strParking = etParking.getText().toString().trim();
        String strRoadToll = etRoadToll.getText().toString().trim();
        String strPettyCash = etPetty.getText().toString().trim();
        // String strPetrol = etPetrol.getText().toString().trim();
        String strOthers = etOthers.getText().toString().trim();
        String strTotal = tvTotalExpenses.getText().toString().trim();
        String strRemarks = etRemarksExp.getText().toString().trim();

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelExpenses> call = api.uploadExpenses(strUSerId, strBreakfast, strLunch, strDinner,
                strMobile, strParking, strRoadToll, strPettyCash, strOthers, strTotal, strRemarks);

        call.enqueue(new Callback<ModelExpenses>() {
            @Override
            public void onResponse(Call<ModelExpenses> call, Response<ModelExpenses> response) {

                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragExpenses.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ModelExpenses> call, Throwable t) {

            }
        });
    }
}
