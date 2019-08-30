package com.knotlink.salseman.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelColdCall;
import com.knotlink.salseman.model.ModelRoute;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class FragColdCall extends Fragment implements AdapterView.OnItemSelectedListener {


    private ModelColdCall tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    final Calendar myCalendar = Calendar.getInstance();

    private GPSTracker tGpsTracker;
    private String strRouteId;
    private String strLat;
    private String strLong;
    private List<ModelRoute> tModelsRoute;
    private AdapterRouteSelect tAdapterRoute;

    @BindView(R.id.et_cold_orgName)
    protected EditText etColdOrgName;
     @BindView(R.id.et_cold_contactName)
    protected EditText etColdContactName;
     @BindView(R.id.et_cold_contactNumber)
    protected EditText etColdContactNumber;
     @BindView(R.id.et_cold_landLine)
    protected EditText et_cold_landLine;
     @BindView(R.id.et_cold_city)
    protected EditText et_cold_city;
     @BindView(R.id.et_cold_address)
    protected EditText etColdAddress;
     @BindView(R.id.et_cold_email)
    protected EditText etColdEmail;
     @BindView(R.id.tv_coldCall_nextMeetingDate)
    protected TextView tvColdCallNextMeetingDate;
     @BindView(R.id.et_cold_remarks)
    protected EditText etColdRemarks;
     @BindView(R.id.btn_cold_submit)
    protected Button btnColdSubmit;
    @BindView(R.id.spnColdCallRoute)
    protected Spinner spnColdCallRoute;
    @BindView(R.id.pbSpnColdCallRoute)
    protected ProgressBar pbSpnColdCallRoute;

    private String strUserId;
    private String strUserType;
    private String strSalesId;
    public static FragColdCall newInstance(String strUserType, String strSalesId) {

        FragColdCall fragment = new FragColdCall();
        fragment.strUserType = strUserType;
        fragment.strSalesId = strSalesId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cold_call, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        pbSpnColdCallRoute.setVisibility(View.VISIBLE);
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Cold Call", getActivity());
        tvColdCallNextMeetingDate.setText(DateUtils.getTodayDate());
        callApiRoute();
        spnColdCallRoute.setOnItemSelectedListener(this);
        tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);

    }

    private void callApiRoute(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.allRouteList();
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                tModelsRoute = response.body();
                pbSpnColdCallRoute.setVisibility(View.GONE);
                tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnColdCallRoute.setAdapter(tAdapterRoute);
            }

            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.tv_coldCall_nextMeetingDate)
    public void coldCallMeetingDate(View view){
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
                        tvColdCallNextMeetingDate.setText(strMyDate);
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

    @OnClick(R.id.btn_cold_submit)
    public void coldSubmit(View view){
        callApi();
    }

    private void callApi() {
        if (strUserType.equalsIgnoreCase("3")) {
            strUserId = strSalesId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();

        }
        String strOrgName = etColdOrgName.getText().toString().trim();
        String strContactName = etColdContactName.getText().toString().trim();
        String strContactNumber = etColdContactNumber.getText().toString().trim();
        String strLandLineNo = et_cold_landLine.getText().toString().trim();
        String strAddress = etColdAddress.getText().toString().trim();
        String strCity = et_cold_city.getText().toString().trim();
        String strEmail = etColdEmail.getText().toString().trim();
        String strMeetingDate = tvColdCallNextMeetingDate.getText().toString().trim();
        String strMeetingDateReal = DateUtils.convertDdToYyyy(strMeetingDate);
        String strRemarks = etColdRemarks.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelColdCall> call = api.uploadColdCall(strUserId, strRouteId, strOrgName, strContactName, strContactNumber,strLandLineNo, strAddress,
                strEmail, strCity, strMeetingDateReal, strRemarks,strLat,strLong);
        call.enqueue(new Callback<ModelColdCall>() {
            @Override
            public void onResponse(Call<ModelColdCall> call, Response<ModelColdCall> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragColdCall.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strUserType)).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelColdCall> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding ColdCall : "+t);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strRouteId = tModelsRoute.get(position).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}