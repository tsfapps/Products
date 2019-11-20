package com.knotlink.salseman.fragment.dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FragExpenses extends Fragment {
    final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 23;
    private Uri imageUri = null;
    public static String CapturedImageDetails;
    public static String Path;
    public static String fileName;

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

                new FragExpenses.LoadImagesFromSDCard().execute(""+imageId);

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
            /********* Which columns values want to get *******/
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
                ivUploadExpenses.setImageBitmap(tBitmap);
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
