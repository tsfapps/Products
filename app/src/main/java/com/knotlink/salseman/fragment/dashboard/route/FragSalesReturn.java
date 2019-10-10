package com.knotlink.salseman.fragment.dashboard.route;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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



    @BindView(R.id.spnReturnArticle)
    protected Spinner spnReturnArticle;
    @BindView(R.id.etReturnQuantity)
    protected EditText etReturnQuantity;
    @BindView(R.id.etReturnRemarks)
    protected EditText etReturnRemarks;

    private String tTitle;


    private List<ModelShopList> tModels;
    private int i;
    private String strUserType;
    private String strAreaStatus;

    public static FragSalesReturn newInstance(List<ModelShopList> tModels, int i, String strUserType, String strAreaStatus) {

        FragSalesReturn fragment = new FragSalesReturn();
        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strAreaStatus = strAreaStatus;
        fragment.strUserType = strUserType;
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


