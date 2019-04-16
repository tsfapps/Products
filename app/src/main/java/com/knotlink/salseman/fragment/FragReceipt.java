package com.knotlink.salseman.fragment;

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
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
import com.kyanogen.signatureview.SignatureView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.app.Activity.RESULT_CANCELED;

public class FragReceipt extends Fragment {
    private Context tContext;
    @BindView(R.id.iv_upload_receipt)
    protected ImageView ivUploadReceipt;
    @BindView(R.id.signature_view_receipt)
    protected SignatureView signatureViewReceipt;
    @BindView(R.id.sv_receipt)
    protected ScrollView svReceipt;
    @BindView(R.id.tv_receipt_check_date)
    protected TextView tvReceiptCheckDate;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_receipt, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    public void initFrag() {
        tContext = getContext();
        SetTitle.tbTitle("Receipt", getActivity());
    }

    @OnClick(R.id.tv_receipt_check_date)
    public void checkDateClicked(View view){
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
                if (strMyDate.compareTo(strCurrentDate)<0) {
                    tvReceiptCheckDate.setText(strMyDate);
                }
                else {
                    CustomToast.toastMid(tContext, Constant.DATE_TOAST);
                }
            }
        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    @OnClick(R.id.iv_receipt_clear_sign)
    public void clrSignReceipt(View view){
        signatureViewReceipt.clearCanvas();
    }
    @OnClick(R.id.btn_receipt_submit)
    public void btnSubmitClicked(View view){
        if (signatureViewReceipt.isBitmapEmpty()){
            CustomToast.toastTop(tContext, "Can't submit without signature...");
        }
        Bitmap bitmapSign = signatureViewReceipt.getSignatureBitmap();
    }

    @OnTouch(R.id.signature_view_receipt)
    public boolean onTouchSign(View view, MotionEvent motionEvent){
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                // Disable the scroll view to intercept the touch event
                svReceipt.requestDisallowInterceptTouchEvent(true);
                return false;
            case MotionEvent.ACTION_UP:
                // Allow scroll View to interceot the touch event
                svReceipt.requestDisallowInterceptTouchEvent(false);
                return true;
            case MotionEvent.ACTION_MOVE:
                svReceipt.requestDisallowInterceptTouchEvent(true);
                return false;
            default:
                return true;
        }
    }

    @OnClick(R.id.iv_upload_receipt)
    public void onUploadReceiptClicked(View view){
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
                SetImage.setGalleryImage(tContext, ivUploadReceipt, data);
            }

        } else if (requestCode == Constant.CAMERA) {

            SetImage.setCameraImage(ivUploadReceipt, data);
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
}
