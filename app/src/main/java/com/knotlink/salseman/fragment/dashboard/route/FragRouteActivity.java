package com.knotlink.salseman.fragment.dashboard.route;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterSpecialRequest;
import com.knotlink.salseman.adapter.spinner.AdapterSpinnerShopStatus;
import com.knotlink.salseman.adapter.spinner.AdapterSpinnerUser;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.ModelFeedback;
import com.knotlink.salseman.model.dash.route.ModelGetLocaion;
import com.knotlink.salseman.model.dash.route.ModelNoActivity;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.model.dash.route.ModelShopStatus;
import com.knotlink.salseman.model.dash.route.ModelSpecialRequest;
import com.knotlink.salseman.model.spn.ModelUserSpn;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DistanceLatLong;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static com.knotlink.salseman.utils.ImageConverter.imageToString;

public class FragRouteActivity extends Fragment {
    private FragmentManager tFragmentManager;
    final String[] strSelectUser = {
            "Select User","Manager", "Sales Manager", "Area Sales Manager", "Dispatch Manager"};
    private Context tContext;
    private List<ModelShopList> tModels;
    List<ModelShopStatus> tModelShopStatus;
    private ModelSpecialRequest tSpecialModels;
    private ModelFeedback tModelsFeedback;

    private String[] title;
    private String spinner_item;
    private String strShopId;
    private String strShopStatus;
    private String strUserId;
    private String strManager;
    private String strSm;
    private String strDm;
    private String strAsm;
    private String strShopName;
    private String strExistingAddress;
    private String strUpdateAddress;
    private String strUpdateAddress2;
    private String strUpdateAddress3;
    private String strUpdateAddress4;
    private String strUpdateAddress5;
    private String strUpdateAddress6;
    private String strUpdateCity;
    private String strUpdateState;
    private String strUpdateCountry;
    private String strUpdatePinCode;
    private int imgStatus = 0;
    private ImageView ivUpdateImage;

    // private String spinner_item_feedback;
    private AdapterSpecialRequest tAdapterSpecialRequest;
    private EditText edittext;
    private EditText etFeedback;
    private Bitmap tBitmap;
    private ImageView ivFeedback;
   // private Spinner spnFeedback;

    private String strLat;
    private String strLong;
    private String strPinCode;
    private String strCity;
    private String strState;
    private String strGeoAdderss;
    private String strAreaStatus;

    @BindView(R.id.btnGetLocation)
    protected Button btnGetLocation;
    @BindView(R.id.ll_RouteNoActivity)
    protected LinearLayout ll_RouteNoActivity;
    @BindView(R.id.tvStatusNoActivity)
    protected TextView tvStatusNoActivity;
    @BindView(R.id.tvRouteActivityOrder)
    protected TextView tvRouteActivityOrder;
    @BindView(R.id.tvStatusOrder)
    protected TextView tvStatusOrder;
    @BindView(R.id.tvStatusReceipt)
    protected TextView tvStatusReceipt;
    @BindView(R.id.tvStatusRequest)
    protected TextView tvStatusRequest;
    @BindView(R.id.tvStatusFeedback)
    protected TextView tvStatusFeedback;
    @BindView(R.id.tvRouteActivityShopName)
    protected TextView tvRouteActivityShopName;
    @BindView(R.id.tvAddressRouteActivity)
    protected TextView tvAddressRouteActivity;
    private int i;
    private String strUserType;
    private String strSelectedUserId;
    private String strAttDate;

    public static FragRouteActivity newInstance(String strAttDate, String strSelectedUserId, List<ModelShopList> tModels, int i, String strUserType) {
        FragRouteActivity fragment = new FragRouteActivity();
        fragment.tModels = tModels;
        fragment.strAttDate = strAttDate;
        fragment.strSelectedUserId = strSelectedUserId;
        fragment.i = i;
        fragment.strUserType = strUserType;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_route_activity, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void initFrag() {
        tContext = getContext();
        SharedPrefManager tSharedPrefManger = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManger.getUserId();

        tFragmentManager = getFragmentManager();
        strShopName = tModels.get(i).getShopName();
        strExistingAddress = tModels.get(i).getShopAddress();

        tvRouteActivityShopName.setText(strShopName);
        //Status
        String strStatusNoActivity = tModels.get(i).getNoActivityStatus();
        String strStatusOrder = tModels.get(i).getNewOrderStatus();
        String strStatusReceipt = tModels.get(i).getReceiptStatus();
        String strStatusRequest = tModels.get(i).getSpecialRequestStatus();
        String strStatusFeedback = tModels.get(i).getComplainStatus();

        if (strStatusNoActivity.equalsIgnoreCase("1")){
            tvStatusNoActivity.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusNoActivity.setBackgroundResource(R.drawable.ic_cross);
        } if (strStatusOrder.equalsIgnoreCase("1")){
            tvStatusOrder.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusOrder.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusReceipt.equalsIgnoreCase("1")){
            tvStatusReceipt.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusReceipt.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusRequest.equalsIgnoreCase("1")){
            tvStatusRequest.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusRequest.setBackgroundResource(R.drawable.ic_cross);
        }
        if (strStatusFeedback.equalsIgnoreCase("1")){
            tvStatusFeedback.setBackgroundResource(R.drawable.ic_check);
        }else {
            tvStatusFeedback.setBackgroundResource(R.drawable.ic_cross);
        }
        title = getResources().getStringArray(R.array.special_request);
        tAdapterSpecialRequest =new AdapterSpecialRequest(tContext, title);
//        String[] strFeedback = getResources().getStringArray(R.array.str_arr_feedback);
        SetTitle.tbTitle("Route Activity", getActivity());
        strShopId = tModels.get(i).getShopId();
        String strShopLat = tModels.get(i).getLatitude();
        String strShopLong = tModels.get(i).getLongitude();
        tvAddressRouteActivity.setVisibility(View.VISIBLE);
        tvAddressRouteActivity.setText(tModels.get(i).getLatLongAddress());
        if (!strShopLat.equalsIgnoreCase("0.0")&& !strShopLong.equalsIgnoreCase("0.0")){
            btnGetLocation.setVisibility(View.GONE);
        }
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strCity = tGpsTracker.getCity(tContext);
        strState = "Karnataka";
        strPinCode = tGpsTracker.getPostalCode(tContext);
        strGeoAdderss = tGpsTracker.getAddressLine(tContext);
        if (strUserType.equalsIgnoreCase("2")){
            tvRouteActivityOrder.setText("Orders");
        }
        else{
            tvRouteActivityOrder.setText("New Order");
        }
        double shopLat = Double.parseDouble(strShopLat);
        double shopLong = Double.parseDouble(strShopLong);
        double myLat = tGpsTracker.latitude;
        double myLong = tGpsTracker.longitude;
        double distActivity = DistanceLatLong.distance(shopLat, shopLong, myLat, myLong);
        if (distActivity>0.015){
            strAreaStatus = "0";
        }else {
            strAreaStatus = "1";
        }
    }

    @OnClick(R.id.ll_route_new_order)
    public void routeOrderClicked(){
        if (!strUserType.equalsIgnoreCase("2")) {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragNewOrder.newInstance(strAttDate, strSelectedUserId,
                    tModels, i, strUserType, strAreaStatus)).addToBackStack(null).commit();
        }else {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragOrderList.newInstance(tModels.get(i).getShopId(),
                    tModels.get(i).getShopName(), strAttDate, strUserType, strSelectedUserId)).addToBackStack(null).commit();
        }
    }
     @OnClick(R.id.ll_RouteSalesReturn)
    public void ll_RouteSalesReturnClicked(){
        if (!strUserType.equalsIgnoreCase("2")) {
            tFragmentManager.beginTransaction().replace(R.id.container_main,
                    FragSalesReturn.newInstance(tModels, i, strUserType, strSelectedUserId)).addToBackStack(null).commit();
        }else {
            tFragmentManager.beginTransaction().replace(R.id.container_main,
                    FragSalesReturnFetch.newInstance(tModels, i, strUserType, strSelectedUserId)).addToBackStack(null).commit();
        }
    }
    @OnClick(R.id.ll_dash_receipt)
    public void receiptClicked(){
        assert getFragmentManager() != null;
        tFragmentManager.beginTransaction().replace(R.id.container_main, FragReceipt.newInstance(strUserType, strSelectedUserId, tModels, i, strAreaStatus)).addToBackStack(null).commit();
    }
    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btnGetLocation)
    public void btnGetLocationClicked(){
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_get_location);
        dialog.setTitle("Add Shop Address");
        dialog.setCancelable(false);

        final Spinner spnStatus = dialog.findViewById(R.id.spnShopStatus);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelShopStatus>> call = api.shopStatus();
        call.enqueue(new Callback<List<ModelShopStatus>>() {
            @Override
            public void onResponse(Call<List<ModelShopStatus>> call, Response<List<ModelShopStatus>> response) {
                tModelShopStatus  = response.body();
                AdapterSpinnerShopStatus tAdapter = new AdapterSpinnerShopStatus(tContext, tModelShopStatus);
                spnStatus.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelShopStatus>> call, Throwable t) {

                Log.d(Constant.TAG, "Shop Status Failure : "+t);
            }
        });
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strShopStatus = tModelShopStatus.get(position).getStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final TextView tvShopName =  dialog.findViewById(R.id.tvUpdateShopName);
        tvShopName.setText(strShopName);
        final TextView tvExistingAddress =  dialog.findViewById(R.id.tvExistingAddress);
        tvExistingAddress.setText(strExistingAddress);
        final TextView tvUpdateGeoAddress =  dialog.findViewById(R.id.tvUpdateGeoAddress);
        tvUpdateGeoAddress.setText(strGeoAdderss);
        final EditText etUpdateShopName =  dialog.findViewById(R.id.etUpdateShopName);
        etUpdateShopName.setText(strShopName);
        final EditText etAddress =  dialog.findViewById(R.id.etUpdateAddress);
        final EditText etAddress2 =  dialog.findViewById(R.id.etUpdateAddress2);
        final EditText etAddress3 =  dialog.findViewById(R.id.etUpdateAddress3);
        final EditText etAddress4 =  dialog.findViewById(R.id.etUpdateAddress4);
        final EditText etAddress5 =  dialog.findViewById(R.id.etUpdateAddress5);
        final EditText etAddress6 =  dialog.findViewById(R.id.etUpdateAddress6);
        final EditText etUpdateCity =  dialog.findViewById(R.id.etUpdateCity);
        etUpdateCity.setText(strCity);
        final EditText etUpdateState =  dialog.findViewById(R.id.etUpdateState);
        etUpdateState.setText(strState);
        final EditText etUpdatePinCode =  dialog.findViewById(R.id.etUpdatePinCode);
        etUpdatePinCode.setText(strPinCode);
        final EditText etUpdateCountry =  dialog.findViewById(R.id.etUpdateCountry);
        etUpdatePinCode.setText(strPinCode);
        ivUpdateImage =  dialog.findViewById(R.id.ivUpdateImage);
        ivUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoFromCamera();
                imgStatus = 1;
            }
        });
        Button btSubmitAddress =  dialog.findViewById(R.id.btnUpdateAddressSubmit);
        Button btnCancelAddress =  dialog.findViewById(R.id.btnUpdateAddressCancel);
        btSubmitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUpdateAddress = etAddress.getText().toString().trim();
                strUpdateAddress2 = etAddress2.getText().toString().trim();
                strUpdateAddress3 = etAddress3.getText().toString().trim();
                strUpdateAddress4 = etAddress4.getText().toString().trim();
                strUpdateAddress5 = etAddress5.getText().toString().trim();
                strUpdateAddress6 = etAddress6.getText().toString().trim();
                 strUpdateCity = etUpdateCity.getText().toString().trim();
                 strUpdateState = etUpdateState.getText().toString().trim();
                 strUpdateCountry = etUpdateCountry.getText().toString().trim();
                strUpdatePinCode = etUpdatePinCode.getText().toString().trim();
                if (strUpdateAddress.equalsIgnoreCase("")){
                    etAddress.setError("Address can't be empty");
                }else if (strUpdateCity.equalsIgnoreCase("")) {
                    etUpdateCity.setError("City can't be empty");

                }else if (strUpdateState.equalsIgnoreCase("")) {
                    etUpdateState.setError("State can't be empty");

                }else if (strUpdatePinCode.equalsIgnoreCase("")) {
                    etUpdatePinCode.setError("Zip Code can't be empty");

                }else {
                final SweetAlertDialog alertDialog = new SweetAlertDialog(tContext, SweetAlertDialog.WARNING_TYPE);
                alertDialog.setTitleText("Are you sure, you would like to submit this address ?");
                alertDialog.setConfirmText("Confirm");
                alertDialog.setConfirmClickListener( new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        callLocationApi(strUpdateAddress, dialog, alertDialog);
                    }
                });

                alertDialog.setCancelText("Cancel");
                alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismissWithAnimation();
                    }
                });
                alertDialog.show();
                Button btn =  alertDialog.findViewById(R.id.confirm_button);
                btn.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
                Button btn1 =  alertDialog.findViewById(R.id.cancel_button);
                btn1.setBackgroundColor(ContextCompat.getColor(tContext, R.color.colorPrimary));
                 }

            }
        });
        btnCancelAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void callLocationApi(final String strAddress, final Dialog dialog, final SweetAlertDialog tDialog){
        String strImage = imageToString(tBitmap, ivUpdateImage);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelGetLocaion> call =api.updateLocation(strShopId,strShopName,"","",
                "", "","","","","","",
                strAddress, strUpdateAddress2,strUpdateAddress3, strUpdateAddress4,strUpdateAddress5,strUpdateAddress6,
                strUpdateCity, strUpdateState,strUpdateCountry, strUpdatePinCode, strUserId, strShopStatus,
                strImage,strAttDate, strLat, strLong);
        call.enqueue(new Callback<ModelGetLocaion>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ModelGetLocaion> call, @NonNull Response<ModelGetLocaion> response) {
                ModelGetLocaion tModel = response.body();
                assert tModel != null;
                if (!tModel.getError()){
                    dialog.dismiss();
                    tDialog.dismissWithAnimation();
                    btnGetLocation.setVisibility(View.GONE);
                    CustomToast.toastTop(getActivity(), tModel.getMessage() );
                    tvAddressRouteActivity.setVisibility(View.VISIBLE);
                    tvAddressRouteActivity.setText(strAddress+" "+ strGeoAdderss);

                }
                CustomToast.toastTop(getActivity(), tModel.getMessage() );
            }

            @Override
            public void onFailure(@NonNull Call<ModelGetLocaion> call, @NonNull Throwable t) {

                Log.d(Constant.TAG, "Location Update  Failure : "+t);
            }
        });
    }


    @OnClick(R.id.ll_RouteNoActivity)
    public void ll_RouteNoActivityClicked(){
        AlertDialog.Builder alert = new AlertDialog.Builder(tContext);
        final EditText etRemarks = new EditText(tContext);
        etRemarks.setBackgroundResource(R.drawable.bg_et);
        etRemarks.setSingleLine();
        FrameLayout container = new FrameLayout(tContext);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
        etRemarks.setLayoutParams(params);
        container.addView(etRemarks);
        alert.setTitle("Mention the reason");
        alert.setView(container);
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String strRemark = etRemarks.getText().toString();
                if (!strRemark.equalsIgnoreCase("")) {
                    Api api = ApiClients.getApiClients().create(Api.class);
                    Call<ModelNoActivity> callNoActivity = api.uploadNoActivity(strAttDate, strUserId, strShopId, strRemark, strLat, strLong, strGeoAdderss, strAreaStatus);
                    callNoActivity.enqueue(new Callback<ModelNoActivity>() {
                        @Override
                        public void onResponse(@NonNull Call<ModelNoActivity> call, @NonNull Response<ModelNoActivity> response) {
                            ModelNoActivity tModel = response.body();
                            assert tModel != null;
                            Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(@NonNull Call<ModelNoActivity> call, @NonNull Throwable t) {
                        }
                    });
                }else {
                    CustomDialog.showEmptyDialog(tContext);
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();
    }

    //Special request clicked
    @OnClick(R.id.ll_route_special_request)
    public void requestClicked() {
        if (!strUserType.equalsIgnoreCase("2")) {
            final Dialog dialog = new Dialog(tContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.row_special_req);
            dialog.setTitle("Special Request");
            dialog.setCancelable(true);

            // set the custom dialog components - text, image and button
            Spinner spinner = dialog.findViewById(R.id.spn_specialRequest);
            Spinner spinnerUser = dialog.findViewById(R.id.spnRequestSpnUser);

            final ArrayList<ModelUserSpn> listUserSpn = new ArrayList<>();

            for (String s : strSelectUser) {
                ModelUserSpn tModelUerSpn = new ModelUserSpn();
                tModelUerSpn.setTitle(s);
                tModelUerSpn.setSelected(true);
                listUserSpn.add(tModelUerSpn);
            }
            AdapterSpinnerUser tAdapter = new AdapterSpinnerUser(tContext, 0, listUserSpn);
            spinnerUser.setAdapter(tAdapter);


            edittext = dialog.findViewById(R.id.et_specialRequest);
            Button button = dialog.findViewById(R.id.btn_specialRequest);
            spinner.setAdapter(this.tAdapterSpecialRequest);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        spinner_item = "";
                    } else {
                        spinner_item = title[position];

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listUserSpn.get(1).isSelected()) {
                        strManager = "1";
                    } else {
                        strManager = "0";
                    }
                    if (listUserSpn.get(2).isSelected()) {
                        strSm = "1";
                    } else {
                        strSm = "0";
                    }
                    if (listUserSpn.get(3).isSelected()) {
                        strDm = "1";
                    } else {
                        strDm = "0";
                    }
                    if (listUserSpn.get(4).isSelected()) {
                        strAsm = "1";
                    } else {
                        strAsm = "0";
                    }
                    if (!spinner_item.equalsIgnoreCase("")) {
                        callSpecialRequestApi(dialog, spinner_item, strManager, strSm, strDm, strAsm);
                    } else {
                        Toast.makeText(tContext, "Please select the Request type.", Toast.LENGTH_LONG).show();
                    }
                }
            });
            dialog.show();
        }else {
            tFragmentManager.beginTransaction().replace(R.id.container_main, FragRequestList.newInstance(tModels.get(i)
                    .getShopId(), strAttDate)).addToBackStack(null).commit();

        }

    }

    //Special Request Api Call
    private void callSpecialRequestApi(final Dialog dialog, String strSpnItem, String strManager, String strSm, String strDm, String strAsm){
        String strRemarks = edittext.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelSpecialRequest> requestCall = api.uploadSpecialRequest(strAttDate, strUserId, strSpnItem, strShopId, strRemarks,
                strManager,strSm,strDm,strAsm, strLat, strLong, strGeoAdderss, strAreaStatus);
        requestCall.enqueue(new Callback<ModelSpecialRequest>() {
            @Override
            public void onResponse(@NonNull Call<ModelSpecialRequest> call, @NonNull Response<ModelSpecialRequest> response) {
                tSpecialModels = response.body();
                dialog.dismiss();
                assert tSpecialModels != null;
                if (!tSpecialModels.getError()){
                    CustomToast.toastTop(getActivity(), tSpecialModels.getMessage());
                }
                else {
                    CustomToast.toastTop(getActivity(), tSpecialModels.getMessage());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ModelSpecialRequest> call, @NonNull Throwable t) {
            }
        });
    }
    //Feedback clicked
    @OnClick(R.id.ll_route_complain)
    public void feedbackSubmit() {
        final Dialog dialog = new Dialog(tContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_route_activity_feedback);
        dialog.setTitle("Special Request");
        dialog.setCancelable(true);

        ivFeedback = dialog.findViewById(R.id.iv_feedback_image);
        etFeedback =  dialog.findViewById(R.id.et_feedback);
        Button button =  dialog.findViewById(R.id.btn_feedback);
        ivFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoFromCamera();
                imgStatus = 2;
            }
        });
        Spinner spinnerUser = dialog.findViewById(R.id.spn_feedbackUser);

        final ArrayList<ModelUserSpn> listUserSpn = new ArrayList<>();

        for (String s : strSelectUser) {
            ModelUserSpn tModelUerSpn = new ModelUserSpn();
            tModelUerSpn.setTitle(s);
            tModelUerSpn.setSelected(true);
            listUserSpn.add(tModelUerSpn);
        }
        AdapterSpinnerUser tAdapter = new AdapterSpinnerUser(tContext, 0, listUserSpn);
        spinnerUser.setAdapter(tAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();

                if (listUserSpn.get(1).isSelected()){
                    strManager = "1";
                }else {
                    strManager = "0";
                } if (listUserSpn.get(2).isSelected()){
                    strSm = "1";
                }else {
                    strSm = "0";
                } if (listUserSpn.get(3).isSelected()){
                    strDm = "1";
                }else {
                    strDm = "0";
                } if (listUserSpn.get(4).isSelected()){
                    strAsm = "1";
                }else {
                    strAsm = "0";
                }
                if (!etFeedback.getText().toString().equalsIgnoreCase("")) {
                    callApiFeedback(dialog, strManager, strSm, strDm, strAsm);

//                    callSpecialRequestApi(dialog, spinner_item, strManager, strSm, strDm, strAsm);
                }else {
                    Toast.makeText(tContext, "Write the remarks first", Toast.LENGTH_LONG).show();
                }
            }

        });
        dialog.show();
    }

    //Feedback Api Call
    private void callApiFeedback(final Dialog dialog, String strManager, String strSm, String strDm, String strAsm){
        String strRemarks = etFeedback.getText().toString().trim();
        String strImage = imageToString(tBitmap, ivFeedback);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelFeedback> requestCall = api.uploadFeedback(strAttDate, strShopId, strUserId, strImage, strRemarks,
            strLat, strLong, strGeoAdderss, strAreaStatus,"","",strManager, strSm, strDm,strAsm);
        requestCall.enqueue(new Callback<ModelFeedback>() {
            @Override
            public void onResponse(@NonNull Call<ModelFeedback> call, @NonNull Response<ModelFeedback> response) {
                tModelsFeedback = response.body();
                dialog.dismiss();
                assert tModelsFeedback != null;
                if (!tModelsFeedback.getError()){
                    CustomToast.toastTop(getActivity(), tModelsFeedback.getMessage());
                }
                else {
                    CustomToast.toastTop(getActivity(), tModelsFeedback.getMessage());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ModelFeedback> call, @NonNull Throwable t) {

            }
        });
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
       if (requestCode == Constant.CAMERA) {
            switch (imgStatus){
                case 1:
                    SetImage.setCameraImage(ivUpdateImage, data);
                    tBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    ivUpdateImage.setImageBitmap(tBitmap);
                    break;
                case 2:
                    SetImage.setCameraImage(ivFeedback, data);
                    tBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    ivFeedback.setImageBitmap(tBitmap);
                    break;
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
}
