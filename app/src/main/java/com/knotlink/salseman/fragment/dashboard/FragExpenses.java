package com.knotlink.salseman.fragment.dashboard;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.spinner.AdapterExpenseSpinner;
import com.knotlink.salseman.adapter.spinner.AdapterVehicleSpinner;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelExpenseList;
import com.knotlink.salseman.model.ModelExpenses;
import com.knotlink.salseman.model.dash.route.ModelVehicleList;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.ImageConverter;
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

public class FragExpenses extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private ModelExpenses tModels;
    private List<ModelExpenseList> tModelExpenseList;
    private AdapterExpenseSpinner tAdapterExpenseSpinner;
    private Bitmap tBitmap;
    private String strExpCat;
    private String strExpenseType;
    private String strLat;
    private String strLong;
    private String strAddress;
    private String strVehicleNo;
    @BindView(R.id.spnExpenseCategory)
    protected Spinner spnExpenseCategory;
    @BindView(R.id.spnExpenseType)
    protected Spinner spnExpenseType;
    @BindView(R.id.iv_upload_expenses)
    protected ImageView ivUploadExpenses;
    @BindView(R.id.etExpenseAmount)
    protected EditText etExpenseAmount;
    @BindView(R.id.et_remark_expenses)
    protected EditText et_remark_expenses;
    @BindView(R.id.spnExpenseVehicleNo)
    protected Spinner spnExpenseVehicleNo;
    @BindView(R.id.llVehicleNoExp)
    protected LinearLayout llVehicleNoExp;


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
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strAddress = tGpsTracker.getAddressLine(tContext);
        spnExpenseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strExpCat = String.valueOf(parent.getSelectedItemPosition());
                Log.d(Constant.TAG, "Exp Pos : "+strExpCat);
                if (strExpCat.equalsIgnoreCase("2")){
                    llVehicleNoExp.setVisibility(View.VISIBLE);
                    callVehicleListApi();
                }else {
                    llVehicleNoExp.setVisibility(View.GONE);
                }
                callExpenseListApi(strExpCat);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strExpenseType = tModelExpenseList.get(position).getExpenseType();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        callExpenseListApi();

    }


    private void callExpenseListApi(String strExpCat){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelExpenseList>> call = api.expenseList(strExpCat);
        call.enqueue(new Callback<List<ModelExpenseList>>() {
            @Override
            public void onResponse(Call<List<ModelExpenseList>> call, Response<List<ModelExpenseList>> response) {
                tModelExpenseList = response.body();
                tAdapterExpenseSpinner = new AdapterExpenseSpinner(tContext, tModelExpenseList);
                spnExpenseType.setAdapter(tAdapterExpenseSpinner);

            }

            @Override
            public void onFailure(Call<List<ModelExpenseList>> call, Throwable t) {

            }
        });
    }
    private void callVehicleListApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelVehicleList>> call = api.vehicleList();
        call.enqueue(new Callback<List<ModelVehicleList>>() {
            @Override
            public void onResponse(Call<List<ModelVehicleList>> call, Response<List<ModelVehicleList>> response) {
                List<ModelVehicleList>  tModelExpenseList = response.body();
                AdapterVehicleSpinner tAdapter = new AdapterVehicleSpinner(tContext, tModelExpenseList);
                spnExpenseVehicleNo.setAdapter(tAdapter);

            }

            @Override
            public void onFailure(Call<List<ModelVehicleList>> call, Throwable t) {

            }
        });
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
                BitmapDrawable drawable = (BitmapDrawable)ivUploadExpenses.getDrawable();
                tBitmap = drawable.getBitmap();

            }

        } else if (requestCode == Constant.CAMERA) {
            SetImage.setCameraImage(ivUploadExpenses, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivUploadExpenses.setImageBitmap(tBitmap);
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
        String strExpenseAmount = etExpenseAmount.getText().toString().trim();
        String strRemarks = et_remark_expenses.getText().toString().trim();

        String strImage = ImageConverter.imageToString(tBitmap, ivUploadExpenses);
        if (strImage.equals("")){
            strImage = "";
        }

        if (strExpenseAmount.equalsIgnoreCase("")){
            etExpenseAmount.setError("Fill the Bill Amount...");

        }else {
            Api api = ApiClients.getApiClients().create(Api.class);
            Call<ModelExpenses> call = api.uploadExpenses(strUSerId,strExpCat,strExpenseType,strExpenseAmount,
                    strRemarks,strVehicleNo,strImage, strLat, strLong, strAddress);

        call.enqueue(new Callback<ModelExpenses>() {
            @Override
            public void onResponse(Call<ModelExpenses> call, Response<ModelExpenses> response) {

                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragExpenses.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragExpenses()).commit();
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


}
