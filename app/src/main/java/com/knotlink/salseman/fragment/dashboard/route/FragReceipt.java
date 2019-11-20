package com.knotlink.salseman.fragment.dashboard.route;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.spinner.AdapterReceiptSpinnerInvoice;
import com.knotlink.salseman.adapter.spinner.AdapterReceiptSpinnerPaymentMode;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragReceiptBinding;
import com.knotlink.salseman.model.dash.route.ModelInsertReceipt;
import com.knotlink.salseman.model.dash.route.ModelInvoice;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.route.ViewModelInsertReceipt;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
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
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragReceipt extends Fragment implements AdapterView.OnItemSelectedListener{
    private Context tContext;
    private FragReceiptBinding tBinding;
    private ViewModelInsertReceipt tViewModel;
    private Bitmap tBitmap;
   private String strUserId;
    private String strShopId;
    private String strBankName;
    private String strInvoiceNo;
    private String strInvoiceDate;
    private String strInvoiceAmount;
    private String strTotalOutStandingAmount;
    private String strBalanceAmount;
    private String strReceivedAmount;
    private String strPendingAmount;
    private String strPaymentMo;
    private String strLat;
    private String strLong;
    private String strNeftChequeNo;
    private String strNeftChequeAmount;
    private String strNeftChequeMatureDtae;
    private String strChequeImage;
    private String strSignature;
    private String strRemarks;
    private int tosAmt;
    private int pendingRcvAmt;



    @BindView(R.id.iv_upload_receipt)
    protected ImageView ivUploadReceipt;
    @BindView(R.id.signature_view_receipt)
    protected SignatureView signatureViewReceipt;
    @BindView(R.id.sv_receipt)
    protected ScrollView svReceipt;
    @BindView(R.id.tv_receipt_shop_name)
    protected TextView tvReceiptShopName;
    @BindView(R.id.tvReceiptOutstandingAmount)
    protected TextView tvReceiptOutstandingAmount;
    @BindView(R.id.tvReceiptBalanceAmount)
    protected TextView tvReceiptBalanceAmount;
    @BindView(R.id.tv_receipt_invoice_date)
    protected TextView tvReceiptInvoiceDate;
    @BindView(R.id.tvReceiptDaysRemain)
    protected TextView tvReceiptDaysRemain;
    @BindView(R.id.tvReceiptInvoiceAmount)
    protected TextView tvReceiptInvoiceAmount;
    @BindView(R.id.etReceiptReceivedAmount)
    protected EditText etReceiptReceivedAmount;
    @BindView(R.id.tvReceiptPendingAmount)
    protected TextView tvReceiptPendingAmount;
    @BindView(R.id.spn_receipt_invoiceNumber)
    protected Spinner spnReceiptInvoiceNumber;
    @BindView(R.id.pbSpnReceiptInvNo)
    protected ProgressBar pbSpnReceiptInvNo;
    @BindView(R.id.spn_receipt_paymentMode)
    protected Spinner spnReceiptPaymentMode;
    @BindView(R.id.flChequeImage)
    protected FrameLayout flChequeImage;
    @BindView(R.id.llReceiptNeftChequeDetail)
    protected LinearLayout llReceiptNeftChequeDetail;
    @BindView(R.id.tvReceiptNeftChequeDate)
    protected TextView tvReceiptNeftChequeDate;
    @BindView(R.id.etReceiptNeftChequeNumber)
    protected EditText etReceiptNeftChequeNumber;
    @BindView(R.id.etReceiptNeftChequeBankName)
    protected EditText etReceiptNeftChequeBankName;
    @BindView(R.id.etReceiptNeftChequeAmount)
    protected EditText etReceiptNeftChequeAmount;
    final Calendar myCalendar = Calendar.getInstance();

    String[] strPaymentMode ={"Cash","NEFT","Cheque"};
    private String strUserType;
    private String strSelectedUserId;
    private List<ModelShopList> tModels;
    private int i;
    private String strAreaStatus;
    private String strInvoiceAmntNet;

    public static FragReceipt newInstance(String strUserType,String strSelectedUserId, List<ModelShopList> tModels, int i, String strAreaStatus) {
        FragReceipt fragment = new FragReceipt();
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strAreaStatus = strAreaStatus;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_receipt, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        AdapterReceiptSpinnerPaymentMode adapterReceiptSpinnerPaymentMode = new AdapterReceiptSpinnerPaymentMode(tContext, strPaymentMode);
        spnReceiptPaymentMode.setAdapter(adapterReceiptSpinnerPaymentMode);
        spnReceiptPaymentMode.setOnItemSelectedListener(this);

        AdapterReceiptSpinnerInvoice adapterReceiptSpinnerInvoice = new AdapterReceiptSpinnerInvoice(tContext, tModels, i);
        spnReceiptInvoiceNumber.setAdapter(adapterReceiptSpinnerInvoice);
        spnReceiptInvoiceNumber.setOnItemSelectedListener(this);
        return view;
    }
    @SuppressLint("SetTextI18n")
    public void initFrag() {
        tContext = getContext();
        GPSTracker tGPSTracker = new GPSTracker(tContext);
        strShopId = tModels.get(i).getShopId();
        strBankName = etReceiptNeftChequeBankName.getText().toString().trim();
        strNeftChequeNo = etReceiptNeftChequeNumber.getText().toString().trim();
        strNeftChequeAmount = etReceiptNeftChequeAmount.getText().toString().trim();
        strTotalOutStandingAmount = tvReceiptOutstandingAmount.getText().toString().trim();
        strBalanceAmount = tvReceiptBalanceAmount.getText().toString().trim();
        strInvoiceDate = tvReceiptInvoiceDate.getText().toString().trim();
        strInvoiceAmount = tvReceiptInvoiceAmount.getText().toString().trim();
        strReceivedAmount = etReceiptReceivedAmount.getText().toString().trim();
        strNeftChequeMatureDtae = tvReceiptNeftChequeDate.getText().toString().trim();
        strNeftChequeNo = etReceiptNeftChequeNumber.getText().toString().trim();
        strLat = String.valueOf(tGPSTracker.latitude);
        strLong = String.valueOf(tGPSTracker.longitude);
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        if (strUserType.equalsIgnoreCase("0")||strUserType.equalsIgnoreCase("3")){
            strUserId = strSelectedUserId;

        }else if(strUserType.equalsIgnoreCase("1")){
            strUserId = tSharedPrefManager.getUserId();

        }else if (strUserType.equalsIgnoreCase("2")) {
            strUserId = tSharedPrefManager.getUserId();


        }
        pbSpnReceiptInvNo.setVisibility(View.VISIBLE);
        SetTitle.tbTitle("Receipt", getActivity());
        tViewModel = ViewModelProviders.of(this).get(ViewModelInsertReceipt.class);
        tvReceiptShopName.setText(tModels.get(i).getShopName());
        tvReceiptOutstandingAmount.setText("Rs. "+tModels.get(i).getInvoiceOutstanding());
        tvReceiptBalanceAmount.setText("Rs. "+tModels.get(i).getInvoiceBalance());
        flChequeImage.setVisibility(View.GONE);

            etReceiptReceivedAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    tosAmt = Integer.parseInt(strInvoiceAmntNet);

                    strReceivedAmount = etReceiptReceivedAmount.getText().toString().trim();
                    if (!strReceivedAmount.equalsIgnoreCase("")) {
                        int rcvAmt = Integer.parseInt(strReceivedAmount);
                        if (tosAmt >= rcvAmt) {
                            pendingRcvAmt = tosAmt - rcvAmt;
                            tvReceiptPendingAmount.setText(String.valueOf(pendingRcvAmt));
                            strPendingAmount = tvReceiptPendingAmount.getText().toString().trim();
                        } else {
                            Toast.makeText(tContext, "Invalid amount !!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        tvReceiptPendingAmount.setText(strInvoiceAmntNet);
                        strPendingAmount = strReceivedAmount;
                    }


                }
            });


    }

    @OnClick(R.id.tvReceiptNeftChequeDate)
    public void tvReceiptNeftDateClicked(View view){
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
                        tvReceiptNeftChequeDate.setText(strMyDate);
                    }
                    else {
                        Toast.makeText(tContext, Constant.DATE_DELIVERY, Toast.LENGTH_SHORT).show();
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


    @OnTouch(R.id.signature_view_receipt)
    public boolean onTouchSign(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                svReceipt.requestDisallowInterceptTouchEvent(true);
                return false;
            case MotionEvent.ACTION_UP:
                svReceipt.requestDisallowInterceptTouchEvent(false);
                return true;
            default:
                return true;
        }
    }
    private String signImageToString(){
        Bitmap bitmapSign = signatureViewReceipt.getSignatureBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapSign.compress(Bitmap.CompressFormat.PNG,10,byteArrayOutputStream);
        byte[] imByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imByte,Base64.DEFAULT);
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
                BitmapDrawable drawable = (BitmapDrawable)ivUploadReceipt.getDrawable();
                tBitmap = drawable.getBitmap();
            }
        } else if (requestCode == Constant.CAMERA) {
            SetImage.setCameraImage(ivUploadReceipt, data);
            tBitmap = (Bitmap) data.getExtras().get("data");
            ivUploadReceipt.setImageBitmap(tBitmap);
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
    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strInvoiceNo = tModels.get(i).getInvoiceNo().get(position);
        if (!strInvoiceNo.equalsIgnoreCase("")) {
            spnReceiptPaymentMode.setEnabled(true);
            etReceiptReceivedAmount.setFocusable(true);

            switch (parent.getId()){
            case R.id.spn_receipt_invoiceNumber:

                Api api = ApiClients.getApiClients().create(Api.class);

                    Call<ModelInvoice> call = api.viewInvoice(strShopId, strInvoiceNo);
                    call.enqueue(new Callback<ModelInvoice>() {
                        @Override
                        public void onResponse(Call<ModelInvoice> call, Response<ModelInvoice> response) {
                            ModelInvoice modelInvoice = response.body();
                            pbSpnReceiptInvNo.setVisibility(View.GONE);
                            if (modelInvoice != null) {
                                tvReceiptInvoiceDate.setText(modelInvoice.getInvoiceDate());
                            tvReceiptInvoiceAmount.setText(modelInvoice.getInvoiceAmount());
                            tvReceiptDaysRemain.setText(modelInvoice.getRemainingDays());
                                strInvoiceAmntNet = modelInvoice.getInvoiceAmount();
                                etReceiptReceivedAmount.setText("");
                                strReceivedAmount = "";
                                tvReceiptPendingAmount.setText(strInvoiceAmntNet);
                                strPendingAmount = strReceivedAmount;
                            }else {
                                Toast.makeText(tContext, "Model Invoice data is null", Toast.LENGTH_SHORT).show();

                            }

                        }
                        @Override
                        public void onFailure(Call<ModelInvoice> call, Throwable t) {
                            Toast.makeText(tContext, "Invoice Not Responding : " + t, Toast.LENGTH_SHORT).show();

                        }
                    });

                break;
            case R.id.spn_receipt_paymentMode:
                strPaymentMo = strPaymentMode[position];
                if (strPaymentMode[position].equals("Cheque")){
                    etReceiptReceivedAmount.setText("");
                    strReceivedAmount = "";
                    tvReceiptPendingAmount.setText(strInvoiceAmntNet);
                    strPendingAmount = strInvoiceAmntNet;
                    etReceiptReceivedAmount.setEnabled(false);
                    etReceiptReceivedAmount.setBackgroundResource(R.drawable.bg_btn_disabled);
                    Toast.makeText(tContext, "Kindly fill the information... ", Toast.LENGTH_SHORT).show();
                    flChequeImage.setVisibility(View.VISIBLE);
                    llReceiptNeftChequeDetail.setVisibility(View.VISIBLE);
                }else if (strPaymentMode[position].equalsIgnoreCase("NEFT")){
                    etReceiptReceivedAmount.setText("");
                    strReceivedAmount = "";
                    tvReceiptPendingAmount.setText(strInvoiceAmntNet);
                    strPendingAmount = strInvoiceAmntNet;
                    etReceiptReceivedAmount.setEnabled(false);
                    etReceiptReceivedAmount.setBackgroundResource(R.drawable.bg_btn_disabled);
                    Toast.makeText(tContext, "Kindly fill the information... ", Toast.LENGTH_SHORT).show();
                    llReceiptNeftChequeDetail.setVisibility(View.VISIBLE);
                    flChequeImage.setVisibility(View.GONE);
                } else if (strPaymentMode[position].equalsIgnoreCase("Cash")){
                    etReceiptReceivedAmount.setEnabled(true);
                    etReceiptReceivedAmount.setBackgroundResource(R.drawable.bg_et);
                    flChequeImage.setVisibility(View.GONE);
                    llReceiptNeftChequeDetail.setVisibility(View.GONE);
                }
                break;
        }
    }else {
        etReceiptReceivedAmount.setFocusable(false);
        Toast.makeText(tContext, "Invoice data is not available...", Toast.LENGTH_SHORT).show();
        spnReceiptPaymentMode.setEnabled(false);
    }


    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void insertReceipt(){
        String strImage = imageToString(tBitmap, ivUploadReceipt);
        String strImageSign = signImageToString();
        tViewModel.insertReceipt(strUserId,tModels.get(i).getShopId(),strBankName,strInvoiceNo,strInvoiceDate,strInvoiceAmount,
                strTotalOutStandingAmount,strBalanceAmount,strReceivedAmount,strPendingAmount,strPaymentMo,
                strLat,strLong,strNeftChequeNo,strNeftChequeMatureDtae,strImage,strImageSign,"",strAreaStatus)
                .observe(this, new Observer<ModelInsertReceipt>() {
                    @Override
                    public void onChanged(@Nullable ModelInsertReceipt modelInsertReceipt) {
                        Toast.makeText(tContext, modelInsertReceipt.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }

    @OnClick(R.id.btn_receipt_submit)
    public void btnSubmitClicked(View view){
        if (signatureViewReceipt.isBitmapEmpty()){
            Toast.makeText(tContext, "Can't submit without signature...", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(tContext, "Submit...", Toast.LENGTH_SHORT).show();
            Bitmap bitmapSign = signatureViewReceipt.getSignatureBitmap();
            insertReceipt();
        }

    }


}
