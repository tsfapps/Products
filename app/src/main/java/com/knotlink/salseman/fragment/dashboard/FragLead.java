package com.knotlink.salseman.fragment.dashboard;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;
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
import static android.app.Activity.RESULT_OK;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragLead extends Fragment implements AdapterView.OnItemSelectedListener {
    final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 23;
    private Uri imageUri = null;
    public static String CapturedImageDetails;
    public static String Path;
    public static String fileName;


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
    private String strSelectedUserId;

    public static FragLead newInstance(String strUserType, String strSelectedUserId) {

        FragLead fragment = new FragLead();
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
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
        SetTitle.tbTitle("Lead", getActivity());
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

        if (tContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            if (!shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                fileName = "Camera_Example.jpg";

                // Create parameters for Intent with filename

                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE, fileName);

                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");

                imageUri = tContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (CheckPermission.isCameraAllowed(tContext)) {
                    Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 10);

                    startActivityForResult( intent, Constant.CAMERA);

                    return;
                }
                CheckPermission.requestCameraPermission(getActivity());

            }
            return;
        }
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constant.CAMERA) {

            if ( resultCode == RESULT_OK) {

                String imageId = convertImageUriToFile( imageUri,getActivity());

                new FragLead.LoadImagesFromSDCard().execute(""+imageId);

            } else if ( resultCode == RESULT_CANCELED) {

                Toast.makeText(tContext, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(tContext, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String convertImageUriToFile ( Uri imageUri, Activity activity )  {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String [] proj={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {


            }
            else
            {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID     = cursor.getInt(columnIndex);

                    thumbID     = cursor.getInt(columnIndexThumb);

                    Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    CapturedImageDetails  = " CapturedImageDetails : \n\n"
                            +" ImageID :"+imageID+"\n"
                            +" ThumbID :"+thumbID+"\n"
                            +" Path :"+Path+"\n";

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return ""+imageID;
    }
    public class LoadImagesFromSDCard  extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(tContext);



        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {
                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                bitmap = BitmapFactory.decodeStream(tContext.getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        tBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {
                // Error fetching image, try to recover

                /********* Cancel execution of this task. **********/
                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if(tBitmap != null)
            {
                // Set Image to ImageView

                ivUploadLead.setImageBitmap(tBitmap);
            }

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
        if (strUserType.equalsIgnoreCase("3")|| strUserType.equalsIgnoreCase("0")) {
            strUserId = strSelectedUserId;
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
                    getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
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
