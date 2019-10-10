package com.knotlink.salseman.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.adapter.spinner.AdapterCustomerTypeSpinner;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragAddNewCustomerBinding;
import com.knotlink.salseman.model.dash.ModelCustomerType;
import com.knotlink.salseman.model.dash.route.ModelRoute;
import com.knotlink.salseman.model.repo.ModelAddNewCustomer;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.viewModel.NewCustomerViewModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragAddNewCustomer extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private GPSTracker tGpsTracker;

    private String strUserId;
    private String strVendorName;
    private String strContactName;
    private String strContactNo;
    private String strLandlineNo;
    private String strWhatsappNo;
    private String strEmail1;
    private String strEmail2;
    private String strAddress1;
    private String strAddress2;
    private String strAddress3;
    private String strFax;
    private String strCity;
    private String strPincode;
    private String strRoute;
    private String strGstNo;
    private String strCreditDays;
    private String strCreditLimit;
    private String strCustomerType;
    private String strImage1;
    private String strImage2;
    private String strImage3;
    private ImageView tImage1;
    private ImageView tImage2;
    private ImageView tImage3;
    private String strLatitude;
    private String strLongitude;
    private List<ModelRoute> tModelsRoute;
    private List<ModelCustomerType> tModelCustomerType;




    private FragAddNewCustomerBinding tBinding;
    private NewCustomerViewModel tViewModel;
//    @BindView(R.id.etAddNewCustomerVendorName)
    private EditText etAddNewCustomerVendorName;
    private EditText etAddNewCustomerContactName;
    private EditText etAddNewCustomerContactNumber;
    private EditText etAddNewCustomerLandLine;
    private EditText etAddNewCustomerWhatsAppNo;
    private EditText etAddNewCustomerEmail1;
    private EditText etAddNewCustomerEmail2;
    private EditText etAddNewCustomerAddress1;
    private EditText etAddNewCustomerAddress2;
    private EditText etAddNewCustomerAddress;
    private EditText etAddNewCustomerFax;
    private EditText etAddNewCustomerCity;
    private EditText etAddNewCustomerPincode;

    private EditText etAddNewCustomerGstNo;
    private EditText etAddNewCustomerCreditDays;
    private EditText etAddNewCustomerCreditLimit;

    private Spinner spnAddNewCustomerRoute;
    private ProgressBar pbSpnAddNewCustomerRoute;
    private Spinner spnAddNewCustomerType;
    private ProgressBar pbSpnAddNewCustomerType;
    private Bitmap tBitmap1;
    private Bitmap tBitmap2;
    private Bitmap tBitmap3;
    private int imgNum = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_add_new_customer, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();
        tViewModel = ViewModelProviders.of(this).get(NewCustomerViewModel.class);

        tGpsTracker = new GPSTracker(tContext);
        strLatitude = String.valueOf(tGpsTracker.latitude);
        strLongitude = String.valueOf(tGpsTracker.longitude);
        spnAddNewCustomerRoute = tBinding.spnAddNewCustomerRoute;
        spnAddNewCustomerRoute.setOnItemSelectedListener(this);
        pbSpnAddNewCustomerRoute = tBinding.pbSpnAddNewCustomerRoute;
        spnAddNewCustomerType = tBinding.spnAddNewCustomerType;
        spnAddNewCustomerType.setOnItemSelectedListener(this);
        pbSpnAddNewCustomerType = tBinding.pbSpnAddNewCustomerType;
        etAddNewCustomerVendorName = tBinding.etAddNewCustomerVendorName;
        etAddNewCustomerContactName = tBinding.etAddNewCustomerContactName;
        etAddNewCustomerContactNumber = tBinding.etAddNewCustomerContactNumber;
        etAddNewCustomerLandLine = tBinding.etAddNewCustomerLandLine;
        etAddNewCustomerWhatsAppNo = tBinding.etAddNewCustomerWhatsAppNo;
        etAddNewCustomerEmail1 = tBinding.etAddNewCustomerEmail1;
        etAddNewCustomerEmail2 = tBinding.etAddNewCustomerEmail2;
        etAddNewCustomerAddress1 = tBinding.etAddNewCustomerAddress1;
        etAddNewCustomerAddress2 = tBinding.etAddNewCustomerAddress2;
        etAddNewCustomerAddress = tBinding.etAddNewCustomerAddress;
        etAddNewCustomerFax = tBinding.etAddNewCustomerFax;
        etAddNewCustomerCity = tBinding.etAddNewCustomerCity;
        etAddNewCustomerPincode = tBinding.etAddNewCustomerPinCode;
        etAddNewCustomerGstNo = tBinding.etAddNewCustomerGst;
        etAddNewCustomerCreditDays = tBinding.etAddNewCustomerCreditDays;
        etAddNewCustomerCreditLimit = tBinding.etAddNewCustomerCreditLimit;
        tImage1 = tBinding.ivAddNewCustomerFirst;
        tImage2 = tBinding.ivAddNewCustomerSecond;
        tImage3 = tBinding.ivAddNewCustomerThird;
        callApiRoute();
        callApiCustomerType();
    }

    private void addNewCustomer(){
        tViewModel.getResponseNewCstomer(strUserId, strVendorName, strContactName,
                strContactNo, strLandlineNo, strWhatsappNo, strEmail1, strEmail2, strAddress1,
                strAddress2, strAddress3, strFax, strCity, strPincode, strRoute, strGstNo, strCreditDays,
                strCreditLimit, strCustomerType, strImage1, strImage2, strImage3, strLatitude,
                strLongitude).observe(this, new Observer<ModelAddNewCustomer>() {
            @Override
            public void onChanged(@Nullable ModelAddNewCustomer modelAddNewCustomer) {
                Toast.makeText(tContext, modelAddNewCustomer.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(tContext, MainActivity.class));
            }
        });
    }

    private void callApiRoute(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.userRouteList (strUserId);
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                tModelsRoute = response.body();
                pbSpnAddNewCustomerRoute.setVisibility(View.GONE);
                AdapterRouteSelect tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnAddNewCustomerRoute.setAdapter(tAdapterRoute);
            }
            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {
            }
        });
    }
    private void callApiCustomerType(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelCustomerType>> call = api.customerType ();
        call.enqueue(new Callback<List<ModelCustomerType>>() {
            @Override
            public void onResponse(Call<List<ModelCustomerType>> call, Response<List<ModelCustomerType>> response) {
                tModelCustomerType= response.body();
                pbSpnAddNewCustomerType.setVisibility(View.GONE);
                AdapterCustomerTypeSpinner tAdapterRoute = new AdapterCustomerTypeSpinner(tContext, tModelCustomerType);
                spnAddNewCustomerType.setAdapter(tAdapterRoute);
            }
            @Override
            public void onFailure(Call<List<ModelCustomerType>> call, Throwable t) {
            }
        });
    }
    @OnClick(R.id.tvCurLocAddNewCustomer)
    public void tvCurLocAddNewCustomerClicked(){
        etAddNewCustomerAddress.setText(tGpsTracker.getAddressLine(tContext));
        etAddNewCustomerPincode.setText(tGpsTracker.getPostalCode(tContext));
        etAddNewCustomerCity.setText(tGpsTracker.getCity(tContext));
    }
    @OnClick(R.id.btnAddNewCustomerSubmit)
    public void btnAddNewCustomerSubmitClicked(){
        strVendorName = etAddNewCustomerVendorName.getText().toString().trim();
        strContactName = etAddNewCustomerContactName.getText().toString().trim();
        strContactNo = etAddNewCustomerContactNumber.getText().toString().trim();
        strLandlineNo = etAddNewCustomerLandLine.getText().toString().trim();
        strWhatsappNo = etAddNewCustomerWhatsAppNo.getText().toString().trim();
        strEmail1 = etAddNewCustomerEmail1.getText().toString().trim();
        strEmail2 = etAddNewCustomerEmail2.getText().toString().trim();
        strAddress1 = etAddNewCustomerAddress1.getText().toString().trim();
        strAddress2 = etAddNewCustomerAddress2.getText().toString().trim();
        strAddress3 = etAddNewCustomerAddress.getText().toString().trim();
        strFax = etAddNewCustomerFax.getText().toString().trim();
        strCity = etAddNewCustomerCity.getText().toString().trim();
        strPincode = etAddNewCustomerPincode.getText().toString().trim();
        strGstNo = etAddNewCustomerGstNo.getText().toString().trim();
        strCreditDays = etAddNewCustomerCreditDays.getText().toString().trim();
        strCreditLimit = etAddNewCustomerCreditLimit.getText().toString().trim();
        strImage1 = imageToString(tBitmap1, tImage1);
        strImage2 = imageToString(tBitmap2, tImage2);
        strImage3 = imageToString(tBitmap3, tImage3);
        addNewCustomer();
    }
    @OnClick(R.id.ivAddNewCustomerFirst)
    public void ivAddNewCustomerFirstClicked(){
        imgNum = 1;
        takePhotoFromCamera();
    }
    @OnClick(R.id.ivAddNewCustomerSecond)
    public void ivAddNewCustomerSecondClicked(){
        imgNum = 2;
        takePhotoFromCamera();
    }
    @OnClick(R.id.ivAddNewCustomerThird)
    public void ivAddNewCustomerThirdClicked(){
        imgNum = 3;
        takePhotoFromCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
       if (requestCode == Constant.CAMERA & imgNum == 1) {
            SetImage.setCameraImage(tImage1, data);
           tBitmap1 = (Bitmap) data.getExtras().get("data");
            tImage1.setImageBitmap(tBitmap1);
        }
      else if (requestCode == Constant.CAMERA & imgNum == 2) {
            SetImage.setCameraImage(tImage2, data);
           tBitmap2 = (Bitmap) data.getExtras().get("data");
            tImage2.setImageBitmap(tBitmap2);
        }
      else if (requestCode == Constant.CAMERA & imgNum == 3) {
            SetImage.setCameraImage(tImage3, data);
           tBitmap3 = (Bitmap) data.getExtras().get("data");
            tImage3.setImageBitmap(tBitmap3);
        }
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnAddNewCustomerRoute:
                strRoute = tModelsRoute.get(position).getId();
                break;
            case R.id.spnAddNewCustomerType:
                strCustomerType = tModelCustomerType.get(position).getCustomerType();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
