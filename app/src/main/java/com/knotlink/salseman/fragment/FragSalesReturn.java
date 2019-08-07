package com.knotlink.salseman.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelNewOrder;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;
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

public class FragSalesReturn extends Fragment {

    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private Bitmap tBitmap;
    private GPSTracker tGpsTracker;
    private String strLat;
    private String strLong;
    private String strUserId;
    private String strShopId;

    final Calendar myCalendar = Calendar.getInstance();



    @BindView(R.id.etReturnArticleNumber)
    protected EditText etReturnArticleNumber;
    @BindView(R.id.etReturnQuantity)
    protected EditText etReturnQuantity;
    @BindView(R.id.etReturnRemarks)
    protected EditText etReturnRemarks;

    private String tTitle;


    private List<ModelShopList> tModels;
    private int i;

    public static FragSalesReturn newInstance(List<ModelShopList> tModels, int i) {

        FragSalesReturn fragment = new FragSalesReturn();
        fragment.tModels = tModels;
        fragment.i = i;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sales_return, container, false);
        ButterKnife.bind(this,view);

          initFrag();
        return view;
    }
    public void initFrag() {
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId  = tSharedPrefManager.getUserId();
        tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        SetTitle.tbTitle("Sales Return", getActivity());
//        tvOrderShopName.setText(tModels.get(i).getShopName());
        strShopId = tModels.get(i).getShopId();
    }



    private void confirmSubmit() {
        AlertDialog.Builder submitDialog = new AlertDialog.Builder(
                tContext);
        submitDialog.setTitle("Confirm Submit...");
        submitDialog.setMessage("Are you sure you want submit this order?");
        submitDialog.setIcon(R.drawable.ic_sign_right);
        submitDialog.setPositiveButton("SUBMIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callApi();
                    }
                });

        submitDialog.setNegativeButton("EDIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        submitDialog.show();
    }
    @OnClick(R.id.btnReturnSubmit)
    public void btnReturnSubmitClicked(View view){

              confirmSubmit();


    }

public void callApi(){
    AlertDialog.Builder submitDialog = new AlertDialog.Builder(
            tContext);
    submitDialog.setTitle("Return request submitted...");
    submitDialog.setMessage("After reviewing we contact you.");
    submitDialog.setIcon(R.drawable.ic_sign_right);
    submitDialog.setPositiveButton("OKAY",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

    submitDialog.show();
}
}


