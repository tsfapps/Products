package com.knotlink.salseman.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.CaptureSignature;
import com.knotlink.salseman.activity.ProductActivity;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
import com.kyanogen.signatureview.SignatureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.app.Activity.RESULT_CANCELED;

public class FragNewOrder extends Fragment {

    private Context tContext;
    final Calendar myCalendar = Calendar.getInstance();

    @BindView(R.id.iv_upload_order)
    protected ImageView ivUploadOrder;
    @BindView(R.id.tvOrderSignMessage)
    protected TextView tvGetSignature;
    @BindView(R.id.signature_view_order)
    protected SignatureView signatureViewOrder;
    @BindView(R.id.sv_order)
    protected ScrollView svOrder;

    @BindView(R.id.tv_order_shop_name)
    protected TextView tvOrderShopName;
//    @BindView(R.id.tv_order_shop_gst_number)
//    protected TextView tvOrderShopGstNumber;
//
//    @BindView(R.id.tv_order_contact_name)
//    protected TextView tvOrderContactName;
//
//    @BindView(R.id.tv_order_contact_number)
//    protected TextView tvOrderContactNumber;
//
//    @BindView(R.id.tv_order_landLineNumber)
//    protected TextView tvOrderTelephone;
//
//    @BindView(R.id.tv_order_whatsAppNumber)
//    protected TextView tvOrderWhatsAppNumber;
//
//    @BindView(R.id.tv_order_address)
//    protected TextView tvOrderAddress;
//
//    @BindView(R.id.tv_order_email)
//    protected TextView tvOrderEmail;
//
//    @BindView(R.id.tv_order_city)
//    protected TextView tvOrderCity;

   @BindView(R.id.tv_order_date_of_delivery)
    protected TextView tvOrderDateOfDelivery;

    private String tTitle;


    private List<ModelShopList> tModels;
    private int i;

    public static FragNewOrder newInstance(List<ModelShopList> tModels, int i) {

        FragNewOrder fragment = new FragNewOrder();
        fragment.tModels = tModels;
        fragment.i = i;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_order, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    public void initFrag() {
        tContext = getContext();
        SetTitle.tbTitle("New Order", getActivity());
        tvOrderShopName.setText(tModels.get(i).getShopName());
        String strTaskShopName = tModels.get(i).getShopName();
        String strTaskContactName = tModels.get(i).getContactName();
        String strTaskContactNo = tModels.get(i).getContactNo();
        String strTaskGstNo = tModels.get(i).getGstNo();
        String strTaskLandLineNo = tModels.get(i).getLandlineNo();
        String strTaskWhatsAppNo = tModels.get(i).getWhatsappNo();
        String strTaskEmail = tModels.get(i).getEmail();
        String strTaskCity = tModels.get(i).getCity();
        String strTaskAddress = tModels.get(i).getShopAddress();
//        tvOrderContactName.setText(tModels.get(i).getContactName());
//        tvOrderContactNumber.setText(tModels.get(i).getContactNo());
//        tvOrderShopGstNumber.setText(tModels.get(i).getGstNo());
//        tvOrderTelephone.setText(tModels.get(i).getLandlineNo());
//        tvOrderWhatsAppNumber.setText(tModels.get(i).getWhatsAppNo());
//        tvOrderEmail.setText(tModels.get(i).getEmail());
//        tvOrderCity.setText(tModels.get(i).getCity());
//        tvOrderAddress.setText(tModels.get(i).getShopAddress());
        String strTodayDate = DateUtils.getTodayDate();
        tvOrderDateOfDelivery.setText(DateUtils.getDeliveryDate(strTodayDate));
    }

    @OnClick(R.id.tv_order_date_of_delivery)
    public void deliverDateClicked(View view)
    {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String strCurrentDate = DateUtils.getTodayDate();
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_DD_MMM_YYYY, Locale.UK);
                String strMyDate = sdf.format(myCalendar.getTime());
                if (strMyDate.compareTo(strCurrentDate)>1) {
                    tvOrderDateOfDelivery.setText(strMyDate);
                }
                else {
                    CustomToast.toastMid(tContext, Constant.DATE_DELIVERY);
                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.iv_order_clear_sign)
    public void clrSignClicked(View view){
        signatureViewOrder.clearCanvas();
    }
    @OnClick(R.id.btn_order_submit)
    public void btnSubmitClicked(View view){
        if (signatureViewOrder.isBitmapEmpty()){
            CustomToast.toastBottom(tContext, "Can't submit without signature...");
        }
        Bitmap bitmapSign = signatureViewOrder.getSignatureBitmap();
        storeImage(bitmapSign);
        CustomToast.toastMid(tContext, "Signature Saved Successfully...");
    }
    @OnTouch(R.id.signature_view_order)
    public boolean onTouchSign(View view, MotionEvent motionEvent){
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                // Disable the scroll view to intercept the touch event
                svOrder.requestDisallowInterceptTouchEvent(true);
                return false;
            case MotionEvent.ACTION_UP:
                // Allow scroll View to interceot the touch event
                svOrder.requestDisallowInterceptTouchEvent(false);
                return true;
            case MotionEvent.ACTION_MOVE:
                svOrder.requestDisallowInterceptTouchEvent(true);
                return false;
            default:
                return true;
        }
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            CustomLog.d(Constant.TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            CustomLog.d(Constant.TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            CustomLog.d(Constant.TAG, "Error accessing file: " + e.getMessage());
        }
    }
    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + Objects.requireNonNull(getActivity()).getPackageName()
                    + "/Files");
        }

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        CustomLog.d(Constant.TAG, "File Path : "+mImageName);
        return mediaFile;
    }
    @OnClick(R.id.tv_order_select_product)
    public void onSelectProduct(View view) {
        tContext.startActivity(new Intent(tContext, ProductActivity.class));
    }

    @OnClick(R.id.orderGetSignature)
    public void getSignClicked(View view){
        tContext.startActivity(new Intent(tContext, CaptureSignature.class));
    }


    @OnClick(R.id.iv_upload_order)
    public void onUploadOrderClick() {
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
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Constant.GALLERY);
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
                SetImage.setGalleryImage(tContext, ivUploadOrder, data);
            }

        } else if (requestCode == Constant.CAMERA) {

            SetImage.setCameraImage(ivUploadOrder, data);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}


