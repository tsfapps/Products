package com.knotlink.salseman.fragment;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.spinner.AdapterReceiptSpinnerInvoice;
import com.knotlink.salseman.adapter.spinner.AdapterReceiptSpinnerPaymentMode;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelInvoice;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
import com.kyanogen.signatureview.SignatureView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragReceipt extends Fragment implements AdapterView.OnItemSelectedListener{



    private Context tContext;
    @BindView(R.id.iv_upload_receipt)
    protected ImageView ivUploadReceipt;
    @BindView(R.id.signature_view_receipt)
    protected SignatureView signatureViewReceipt;
    @BindView(R.id.sv_receipt)
    protected ScrollView svReceipt;
    @BindView(R.id.tv_receipt_check_date)
    protected TextView tvReceiptCheckDate;
    @BindView(R.id.tv_receipt_shop_name)
    protected TextView tvReceiptShopName;
    @BindView(R.id.tv_receipt_totalOutstandingAmount)
    protected TextView tvReceiptTotalOutstandingAmount;
    @BindView(R.id.tv_receipt_invoice_date)
    protected TextView tvReceiptInvoiceDate;
    @BindView(R.id.tv_receipt_totalInvoiceAmount)
    protected TextView tvReceiptTotalInvoiceAmount;
    @BindView(R.id.spn_receipt_invoiceNumber)
    protected Spinner spnReceiptInvoiceNumber;
    @BindView(R.id.spn_receipt_paymentMode)
    protected Spinner spnReceiptPaymentMode;
    @BindView(R.id.ll_receipt_chequeDetail)
    protected LinearLayout llReceiptChequeDetail;
    final Calendar myCalendar = Calendar.getInstance();

    String[] strPaymentMode ={"--select the payment mode--","Cash","NEFT","Cheque"};
    private List<ModelShopList> tModels;
    private int i;
    public static FragReceipt newInstance(List<ModelShopList> tModels, int i) {
        FragReceipt fragment = new FragReceipt();
        fragment.tModels = tModels;
        fragment.i = i;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_receipt, container, false);
        ButterKnife.bind(this, view);

        initFrag();
        AdapterReceiptSpinnerPaymentMode adapterReceiptSpinnerPaymentMode = new AdapterReceiptSpinnerPaymentMode(tContext, strPaymentMode);
        spnReceiptPaymentMode.setAdapter(adapterReceiptSpinnerPaymentMode);
        spnReceiptPaymentMode.setOnItemSelectedListener(this);

        AdapterReceiptSpinnerInvoice adapterReceiptSpinnerInvoice = new AdapterReceiptSpinnerInvoice(tContext, tModels, i);
        spnReceiptInvoiceNumber.setAdapter(adapterReceiptSpinnerInvoice);
        spnReceiptInvoiceNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                    String strInvoiceNo = tModels.get(i).getInvoiceNo().get(position);
                    String strShopId = tModels.get(i).getShopId();
                    Api api = ApiClients.getApiClients().create(Api.class);
                if (!strInvoiceNo.equalsIgnoreCase("")) {
                    Call<ModelInvoice> call = api.viewInvoice(strShopId, strInvoiceNo);
                    call.enqueue(new Callback<ModelInvoice>() {
                        @Override
                        public void onResponse(Call<ModelInvoice> call, Response<ModelInvoice> response) {
                            ModelInvoice modelInvoice = response.body();
                            tvReceiptInvoiceDate.setText(modelInvoice.getInvoiceDate());
                            tvReceiptTotalInvoiceAmount.setText(modelInvoice.getInvoiceAmount());

                        }

                        @Override
                        public void onFailure(Call<ModelInvoice> call, Throwable t) {

                            CustomLog.d(Constant.TAG, "Invoice Not Responding : " + t);
                        }
                    });
                }else {
                    CustomToast.toastTop(getActivity(), "Invoice data is not available...");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    @SuppressLint("SetTextI18n")
    public void initFrag() {
        tContext = getContext();
        SetTitle.tbTitle("Receipt", getActivity());
        tvReceiptShopName.setText(tModels.get(i).getShopName());
        tvReceiptTotalOutstandingAmount.setText("Rs. "+tModels.get(i).getTotalOutstandingAmount());
        llReceiptChequeDetail.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_receipt_check_date)
    public void checkDateClicked(View view){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
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
                        tvReceiptCheckDate.setText(strMyDate);
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
    @OnClick(R.id.iv_receipt_clear_sign)
    public void clrSignReceipt(View view){
        signatureViewReceipt.clearCanvas();
    }
    @OnClick(R.id.btn_receipt_submit)
    public void btnSubmitClicked(View view){
        if (signatureViewReceipt.isBitmapEmpty()){
            CustomToast.toastTop(getActivity(), "Can't submit without signature...");
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
                Toast.makeText(getActivity(), "You  denied the permission...", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (strPaymentMode[position].equals("Cheque")){
                CustomToast.toastTop(getActivity(), "Kindly fill the information... ");
                llReceiptChequeDetail.setVisibility(View.VISIBLE);
        }
        else llReceiptChequeDetail.setVisibility(View.GONE);

//        switch (view.getId()){
//            case R.id.spn_receipt_paymentMode :
//                CustomLog.d(Constant.TAG, "Payment Mode : "+ strPaymentMode[position]);
//
//                break;
//            case R.id.spn_receipt_invoiceNumber:
//                CustomLog.d(Constant.TAG, "Invoice Number : "+ tModels.get(i).getInvoiceNo().get(position));
//                break;
//        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
