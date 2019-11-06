package com.knotlink.salseman.fragment.dashboard.route;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterCity;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.fragment.FragTask;
import com.knotlink.salseman.model.task.ModelNewCustomer;
import com.knotlink.salseman.model.dash.route.ModelRoute;
import com.knotlink.salseman.model.task.ModelTaskProspect;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
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

public class FragNewCustomer extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private String strUserId;
    private String strRouteId;
    private List<ModelRoute> tModelsRoute;

    private GPSTracker tGpsTracker;
    private String strLat;
    private String strLong;

    private Bitmap tBitmap1;
    private Bitmap tBitmap2;
    private Bitmap tBitmap3;
    private int imgNum = 0;
    private String strImage1;
    private String strImage2;
    private String strImage3;

    @BindView(R.id.spnNewCustomerRoute)
    protected Spinner spnNewCustomerRoute;
    @BindView(R.id.spnNewCustomerCity)
    protected Spinner spnNewCustomerCity;
    @BindView(R.id.etNewCustomerShopName)
    protected EditText etNewCustomerShopName;
    @BindView(R.id.etNewCustomerContactName)
    protected EditText etNewCustomerContactName;
    @BindView(R.id.etNewCustomerContactNumber)
    protected EditText etNewCustomerContactNumber;
    @BindView(R.id.etNewCustomerEmail)
    protected EditText etNewCustomerEmail;
    @BindView(R.id.etNewCustomerWhatsAppNo)
    protected EditText etNewCustomerWhatsAppNo;
    @BindView(R.id.etNewCustomerGst)
    protected EditText etNewCustomerGst;
    @BindView(R.id.etNewCustomerAddress)
    protected EditText etNewCustomerAddress;

    @BindView(R.id.etNewCustomerLandLine)
    protected EditText etNewCustomerLandLine;
    @BindView(R.id.etNewCustomerPinCode)
    protected EditText etNewCustomerPinCode;
    @BindView(R.id.etNewCustomerRemarks)
    protected EditText etNewCustomerRemarks;
    @BindView(R.id.btnNewCustomerSubmit)
    protected Button btnNewCustomerSubmit;
    @BindView(R.id.pbSpnNewCustomerRoute)
    protected ProgressBar pbSpnNewCustomerRoute;
    @BindView(R.id.ivNewCustomerFirst)
    protected ImageView ivNewCustomerFirst;
    @BindView(R.id.ivNewCustomerSecond)
    protected ImageView ivNewCustomerSecond;
    @BindView(R.id.ivNewCustomerThird)
    protected ImageView ivNewCustomerThird;

    private ModelTaskProspect tModel;
    private String strUserType;
    private String strSelectedUserId;
    public static FragNewCustomer newInstance(String strSelectedUserId, ModelTaskProspect tModel, String strUserType) {
        FragNewCustomer fragment = new FragNewCustomer();
        fragment.tModel = tModel;
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_customer, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        SetTitle.tbTitle("New Customer Registration", getActivity());
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tGpsTracker = new GPSTracker(tContext);
        strUserId = tSharedPrefManager.getUserId();
        etNewCustomerShopName.setText(tModel.getOrgName());
        etNewCustomerContactName.setText(tModel.getCustomerName());
        etNewCustomerEmail.setText(tModel.getEmail());
        etNewCustomerLandLine.setText(tModel.getLandlineNo());
        etNewCustomerWhatsAppNo.setText(tModel.getWhatsappNo());
        etNewCustomerContactNumber.setText(tModel.getCustomerContactNo());
        etNewCustomerAddress.setText(tModel.getCustomerAddress());
        etNewCustomerPinCode.setText(tModel.getPincode());
        String[] strCity = getResources().getStringArray(R.array.city_list);
        AdapterCity tAdapterCity = new AdapterCity(tContext, strCity);
        spnNewCustomerCity.setAdapter(tAdapterCity);
        pbSpnNewCustomerRoute.setVisibility(View.VISIBLE);
        callApiRoute();
        spnNewCustomerRoute.setOnItemSelectedListener(this);
    }
    @OnClick(R.id.tvCurLocNewCustomer)
    public void tvCurLocNewCustomerClicked(View view){
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        etNewCustomerPinCode.setText(tGpsTracker.getPostalCode(tContext));
        etNewCustomerAddress.setText(tGpsTracker.getAddressLine(tContext));
    }

    private void callApiRoute(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.userRouteList (strUserId);
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                tModelsRoute = response.body();
                pbSpnNewCustomerRoute.setVisibility(View.GONE);
                AdapterRouteSelect tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnNewCustomerRoute.setAdapter(tAdapterRoute);
            }
            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {
            }
        });
    }
    @OnClick(R.id.ivNewCustomerFirst)
    public void ivNewCustomerFirstClicked(){
        imgNum = 1;
        takePhotoFromCamera();
    }
    @OnClick(R.id.ivNewCustomerSecond)
    public void ivNewCustomerSecondClicked(){
        imgNum = 2;
        takePhotoFromCamera();
    }
    @OnClick(R.id.ivNewCustomerThird)
    public void ivNewCustomerThirdClicked(){
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
            SetImage.setCameraImage(ivNewCustomerFirst, data);
            tBitmap1 = (Bitmap) data.getExtras().get("data");
            ivNewCustomerFirst.setImageBitmap(tBitmap1);
        }
        else if (requestCode == Constant.CAMERA & imgNum == 2) {
            SetImage.setCameraImage(ivNewCustomerSecond, data);
            tBitmap2 = (Bitmap) data.getExtras().get("data");
            ivNewCustomerSecond.setImageBitmap(tBitmap2);
        }
        else if (requestCode == Constant.CAMERA & imgNum == 3) {
            SetImage.setCameraImage(ivNewCustomerThird, data);
            tBitmap3 = (Bitmap) data.getExtras().get("data");
            ivNewCustomerThird.setImageBitmap(tBitmap3);
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
    private void callApi(String strImg1, String strImg2, String strImg3){
        String strTaskId = tModel.getTaskId();
        String strShopName = etNewCustomerShopName.getText().toString().trim();
        String strContactName = etNewCustomerContactName.getText().toString().trim();
        String strContactNo = etNewCustomerContactNumber.getText().toString().trim();
        String strEmail = etNewCustomerEmail.getText().toString().trim();
        String strAddress = etNewCustomerAddress.getText().toString().trim();
        String strCity =     tGpsTracker.getCity(tContext);
        String strPincode = etNewCustomerPinCode.getText().toString().trim();
        String strGstNo = etNewCustomerGst.getText().toString().trim();
        String strLandLine = etNewCustomerLandLine.getText().toString().trim();
        String strWhatsAppNo = etNewCustomerWhatsAppNo.getText().toString().trim();
        String strRemarks = etNewCustomerRemarks.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);


        Call<ModelNewCustomer> customerCall = api.newCustomer(strTaskId,strUserId,strRouteId,strShopName,strContactName,strContactNo,strEmail,strAddress,
                strCity,strPincode,strGstNo,strLandLine,strWhatsAppNo,strRemarks,strImg1,strImg2,strImg3,strLat,strLong);
        customerCall.enqueue(new Callback<ModelNewCustomer>() {
            @Override
            public void onResponse(Call<ModelNewCustomer> call, Response<ModelNewCustomer> response) {
                ModelNewCustomer tModel = response.body();
                CustomToast.toastTop(getActivity(), tModel.getMessage());
                getFragmentManager().beginTransaction().remove(FragNewCustomer.this).commit();
                getFragmentManager().beginTransaction().replace(R.id.container_main, FragTask.newInstance(strSelectedUserId, strUserType)).commit();
            }
            @Override
            public void onFailure(Call<ModelNewCustomer> call, Throwable t) {

                CustomLog.d(Constant.TAG, "New Customer Failure : "+t);
            }
        });
    }
    @OnClick(R.id.btnNewCustomerSubmit)
    public void btnNewCustomerSubmitClicked(){
        strImage1 = imageToString(tBitmap1, ivNewCustomerFirst);
        strImage2 = imageToString(tBitmap2, ivNewCustomerSecond);
        strImage3 = imageToString(tBitmap3, ivNewCustomerThird);
        callApi(strImage1, strImage2, strImage3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strRouteId = tModelsRoute.get(position).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
