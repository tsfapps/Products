package com.knotlink.salseman.fragment.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.act.AdapterCustomerList;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelCustomerList;
import com.knotlink.salseman.model.dash.ModelLead;
import com.knotlink.salseman.model.dash.route.ModelRoute;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragLead extends Fragment implements AdapterView.OnItemSelectedListener {
    private ModelLead tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private Bitmap tBitmap;
    private GPSTracker tGpsTracker;
    private String strRouteId;
    private String strCustomerType = "0";

    private List<ModelRoute> tModelsRoute;
    private List<ModelCustomerList> tModelsCustomerList;
    private AdapterRouteSelect tAdapterRoute;


    private String strLat;
    private String strLong;
    private String strPinCode;
    private String strCity;
    private String strAdders;
    private String strShopId;
    final Calendar myCalendar = Calendar.getInstance();

    @BindView(R.id.tv_lead_orgNameLabel)
    protected TextView tvLeadOrgNameLabel;
    @BindView(R.id.aCtvLeadOrgName)
    protected AutoCompleteTextView aCtvLeadOrgName;
    @BindView(R.id.et_lead_contactName)
    protected EditText etLeadContactName;
    @BindView(R.id.et_lead_contactNumber)
    protected EditText etLeadContactNumber;
    @BindView(R.id.et_lead_whatsAppNo)
    protected EditText etLeadWhatsApp;
    @BindView(R.id.et_lead_email)
    protected EditText etLeadEmail;
    @BindView(R.id.et_lead_comments)
    protected EditText etLeadComments;
    @BindView(R.id.et_lead_landLine)
    protected EditText etLeadLandLine;
    @BindView(R.id.etLeadOrgAddress)
    protected EditText etLeadOrgAddress;
    @BindView(R.id.tv_lead_nextMeetingDate)
    protected TextView tvLeadNextMeetingDate;
    @BindView(R.id.btn_lead_submit)
    protected Button btnLeadSubmit;
    @BindView(R.id.iv_upload_lead)
    protected ImageView ivUploadLead;
    @BindView(R.id.rg_lead)
    protected RadioGroup rgLead;
    @BindView(R.id.spnLeadRoute)
    protected Spinner spnLeadRoute;
    @BindView(R.id.pbSpnLeadRoute)
    protected ProgressBar pbSpnLeadRoute;


    private String strUserId;
    String strVendorType;

    private String strUserType;
    private String strSalesId;

    public static FragLead newInstance(String strUserType, String strSalesId) {

        FragLead fragment = new FragLead();
        fragment.strUserType = strUserType;
        fragment.strSalesId = strSalesId;
        return fragment;
    }


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
        pbSpnLeadRoute.setVisibility(View.VISIBLE);
        strVendorType = "Customer";
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Lead Generation", getActivity());
        tvLeadNextMeetingDate.setText(DateUtils.getTodayDate());
        rgLead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_lead_org:
                        CustomToast.toastTop(getActivity(), "Customer Selected");
                        tvLeadOrgNameLabel.setText("Organisation Name*");
                        strVendorType = "Customer";
                        strCustomerType = "0";
                        break;
                    case R.id.rb_lead_prospect:
                        CustomToast.toastTop(getActivity(), "Prospect Selected");
                        tvLeadOrgNameLabel.setText("Prospect Name*");
                        strVendorType = "Prospect";
                        strCustomerType = "1";
                        break;
                }
            }
        });

        tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strCity = tGpsTracker.getCity(tContext);
        strPinCode = tGpsTracker.getPostalCode(tContext);
        strAdders = tGpsTracker.getAddressLine(tContext);
        callApiRoute();
        spnLeadRoute.setOnItemSelectedListener(this);

        aCtvLeadOrgName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strShopId = tModelsCustomerList.get(position).getShopId();
                String strAddress = tModelsCustomerList.get(position).getShopAddress();
                String strContactNo = tModelsCustomerList.get(position).getContactNo();
                String strContactName = tModelsCustomerList.get(position).getContactName();
                String strEmailId = tModelsCustomerList.get(position).getEmail();
                String strWhatsApp = tModelsCustomerList.get(position).getWhatsappNo();
                String strLandLineNo = tModelsCustomerList.get(position).getLandlineNo();
                etLeadOrgAddress.setText(strAddress);
                etLeadContactNumber.setText(strContactNo);
                etLeadContactName.setText(strContactName);
                etLeadEmail.setText(strEmailId);
                etLeadWhatsApp.setText(strWhatsApp);
                etLeadLandLine.setText(strLandLineNo);
            }
        });

    }

    private void callApiRoute(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.allRouteList();
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                tModelsRoute = response.body();
                pbSpnLeadRoute.setVisibility(View.GONE);
                tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnLeadRoute.setAdapter(tAdapterRoute);
            }

            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {

            }
        });
    }
    private void callApiShopList(String strRouteId){
        Log.d(Constant.TAG, "CustomerType : "+strCustomerType);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelCustomerList>> customerListCall = api.getCustomerList(strRouteId, strCustomerType);
        customerListCall.enqueue(new Callback<List<ModelCustomerList>>() {
            @Override
            public void onResponse(Call<List<ModelCustomerList>> call, Response<List<ModelCustomerList>> response) {
                int i;
                tModelsCustomerList = response.body();
//                if (tModelsCustomerList.size() > 0) {
                    ArrayList<String> searchArrayList = new ArrayList<String>();

                    for (i = 0; i < tModelsCustomerList.size(); i++) {
                        searchArrayList.add(tModelsCustomerList.get(i).getShopName());
                    }
                    AdapterCustomerList adapter = new AdapterCustomerList(tContext, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, searchArrayList);
                    aCtvLeadOrgName.setAdapter(adapter);
//                }else {
//                    Toast.makeText(tContext, "Customer list is empty", Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelCustomerList>> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.iv_upload_lead)
    public void onUploadLeadClicked(View view){
        takePhotoFromCamera();
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
            SetImage.setCameraImage(ivUploadLead, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivUploadLead.setImageBitmap(tBitmap);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "You  denied the permission...", Toast.LENGTH_LONG).show();
            }
        }
    }
    @OnClick(R.id.btn_lead_submit)
    public void leadSubmit(View view){
        callApi();
    }
    @OnClick(R.id.tv_lead_nextMeetingDate)
    public void leadNextDateSelected(View view){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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
                        tvLeadNextMeetingDate.setText(strMyDate);
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
    private void callApi(){
        if (strUserType.equalsIgnoreCase("3")) {
            strUserId = strSalesId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();

        }
        String strOrgName = aCtvLeadOrgName.getText().toString().trim();
        String strContactName = etLeadContactName.getText().toString().trim();
        String strContactNumber = etLeadContactNumber.getText().toString().trim();
        String strWhatsApp = etLeadWhatsApp.getText().toString().trim();
        String strEmail = etLeadEmail.getText().toString().trim();
        String strRemarks = etLeadComments.getText().toString().trim();
        String strLandLine = etLeadLandLine.getText().toString().trim();
        String strNextMeetDate = tvLeadNextMeetingDate.getText().toString().trim();
        String strNextMeetDateReal = DateUtils.convertDdToYyyy(strNextMeetDate);
        String strImage = imageToString(tBitmap, ivUploadLead);
        Log.d(Constant.TAG, "NextMeetDate : "+strNextMeetDateReal);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelLead> call = api.uploadLead(strUserId,strShopId, strRouteId, strVendorType, strOrgName, strContactName, strContactNumber, strAdders,
                strEmail,strCity,strPinCode,strNextMeetDateReal,strWhatsApp,strLandLine, strRemarks, strImage,strLat, strLong );
        call.enqueue(new Callback<ModelLead>() {
            @Override
            public void onResponse(Call<ModelLead> call, Response<ModelLead> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragLead.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strUserType)).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());

                }

            }
            @Override
            public void onFailure(Call<ModelLead> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding Lead : "+t);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strRouteId = tModelsRoute.get(position).getId();
        callApiShopList(strRouteId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}