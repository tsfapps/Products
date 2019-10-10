package com.knotlink.salseman.fragment.dashboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.knotlink.salseman.model.dash.ModelMeeting;
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

public class FragMeeting extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] languages = { "C","C++","Java","C#","PHP","JavaScript","jQuery","AJAX","JSON" };

    private ModelMeeting tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private Bitmap tBitmap;
    private GPSTracker tGpsTracker;
    private String strLat;
    private String strLong;
    private String strPinCode;
    private String strCity;
    private String strAdders;
    private String strRouteId;
    private String strShopId;
    private String strCustomerType = "0";
    private List<ModelRoute> tModelsRoute;
    private List<ModelCustomerList> tModelsCustomerList;
    private AdapterRouteSelect tAdapterRoute;


    final Calendar myCalendar = Calendar.getInstance();
    String strVendorType;
    @BindView(R.id.tv_meeting_orgName)
    protected TextView tvMeetingOrgName;
    @BindView(R.id.aCtv_meeting_orgName)
    protected AutoCompleteTextView aCtvMeetingOrgName;
    @BindView(R.id.et_meeting_contactName)
    protected EditText etMeetingContactName;
    @BindView(R.id.et_meeting_contactNumber)
    protected EditText etMeetingContactNumber;
    @BindView(R.id.et_meeting_whatsapp_no)
    protected EditText etMeetingWhatsAppNo;
    @BindView(R.id.et_meeting_email)
    protected EditText etMeetingEmail;
    @BindView(R.id.et_meeting_remarks)
    protected EditText etMeetingComments;
     @BindView(R.id.et_meeting_landLine)
    protected EditText et_meeting_landLine;
     @BindView(R.id.et_meetingOrgAddress)
    protected EditText et_meetingOrgAddress;
    @BindView(R.id.btn_meeting_submit)
    protected Button btnMeetingSubmit;
    @BindView(R.id.iv_upload_meeting)
    protected ImageView ivUploadMeeting;
    @BindView(R.id.rg_meeting)
    protected RadioGroup rgMeeting;
    @BindView(R.id.tv_meeting_next_date)
    protected TextView tvMeetingNextDate;
    @BindView(R.id.spnMeetingRoute)
    protected Spinner spnMeetingRoute;
    @BindView(R.id.pbSpnMeetingRoute)
    protected ProgressBar pbSpnMeetingRoute;




    private String strUserId;
    private String strUserType;
    private String strSalesId;
    public static FragMeeting newInstance(String strUserType, String strSalesId) {

        FragMeeting fragment = new FragMeeting();
        fragment.strUserType = strUserType;
        fragment.strSalesId = strSalesId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_meeting, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    public void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Meeting", getActivity());
        tvMeetingNextDate.setText(DateUtils.getTodayDate());
        pbSpnMeetingRoute.setVisibility(View.VISIBLE);
        strVendorType = "Customer";

        rgMeeting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_meeting_customer:
                        CustomToast.toastTop(getActivity(), "Customer Selected");
                        tvMeetingOrgName.setText("Organisation Name*");
                        strVendorType = "Customer";
                        strCustomerType = "0";
                        break;
                    case R.id.rb_meeting_prospect:
                        CustomToast.toastTop(getActivity(), "Prospect Selected");
                        tvMeetingOrgName.setText("Prospect Name*");
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
        spnMeetingRoute.setOnItemSelectedListener(this);

        aCtvMeetingOrgName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strShopId = tModelsCustomerList.get(position).getShopId();
                String strAddress = tModelsCustomerList.get(position).getShopAddress();
                String strContactNo = tModelsCustomerList.get(position).getContactNo();
                String strContactName = tModelsCustomerList.get(position).getContactName();
                String strEmailId = tModelsCustomerList.get(position).getEmail();
                String strWhatsApp = tModelsCustomerList.get(position).getWhatsappNo();
                String strLandLineNo = tModelsCustomerList.get(position).getLandlineNo();
                et_meetingOrgAddress.setText(strAddress);
                etMeetingContactNumber.setText(strContactNo);
                etMeetingContactName.setText(strContactName);
                etMeetingEmail.setText(strEmailId);
                etMeetingWhatsAppNo.setText(strWhatsApp);
                et_meeting_landLine.setText(strLandLineNo);
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
                pbSpnMeetingRoute.setVisibility(View.GONE);
                tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnMeetingRoute.setAdapter(tAdapterRoute);
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
                    aCtvMeetingOrgName.setAdapter(adapter);
//                }else {
//                    Toast.makeText(tContext, "Customer list is empty", Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelCustomerList>> call, Throwable t) {

            }
        });
    }



    @OnClick(R.id.tv_meeting_next_date)
    public void selectMeetingDate(View view){
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
                    // long millis = myDate.getTime();
                    String strMyDate = sdf.format(myCalendar.getTime());
                    Date selDate = sdf.parse(strMyDate);
                    if (selDate.compareTo(myDate)>0) {
                        tvMeetingNextDate.setText(strMyDate);
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
    @OnClick(R.id.iv_upload_meeting)
    public void onUploadMeetingClicked(View view){
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
                SetImage.setGalleryImage(tContext, ivUploadMeeting, data);
                tBitmap = (Bitmap) data.getExtras().get("data");
                ivUploadMeeting.setImageBitmap(tBitmap);
            }
        } else if (requestCode == Constant.CAMERA) {
            SetImage.setCameraImage(ivUploadMeeting, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivUploadMeeting.setImageBitmap(tBitmap);
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
    @OnClick(R.id.btn_meeting_submit)
    public void meetingSubmit(View view){
        callApi();
    }
    private void callApi(){
        String strImage = imageToString(tBitmap, ivUploadMeeting);
        if (strUserType.equalsIgnoreCase("3")) {
            strUserId = strSalesId;
            Log.d(Constant.TAG, "Sales Id : "+strUserId);
        }else {
            strUserId = tSharedPrefManager.getUserId();
        }

        String strOrgName = aCtvMeetingOrgName.getText().toString().trim();
        String strContactName = etMeetingContactName.getText().toString().trim();
        String strContactNumber = etMeetingContactNumber.getText().toString().trim();
        String strWhatsAppNo = etMeetingWhatsAppNo.getText().toString().trim();
        String strLandLineNo = et_meeting_landLine.getText().toString().trim();
        String strEmail = etMeetingEmail.getText().toString().trim();
        String strRemarks = etMeetingComments.getText().toString().trim();
        String strNextMeetDate = tvMeetingNextDate.getText().toString().trim();
        String strNextMeetDateReal = DateUtils.convertDdToYyyy(strNextMeetDate);
        Log.d(Constant.TAG, "Date Real Meeting :"+ strNextMeetDateReal);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMeeting> call = api.uploadMeeting(strUserId, strShopId, strRouteId, strVendorType, strOrgName, strContactName, strContactNumber, strAdders,
                strEmail,strCity,strPinCode,strNextMeetDateReal,strWhatsAppNo,strLandLineNo, strRemarks, strImage, strLat, strLong);
        call.enqueue(new Callback<ModelMeeting>() {
            @Override
            public void onResponse(Call<ModelMeeting> call, Response<ModelMeeting> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragMeeting.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strUserType)).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelMeeting> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding Meeting : "+t);

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