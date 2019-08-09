package com.knotlink.salseman.fragment;

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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterCity;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.task.ModelNewCustomer;
import com.knotlink.salseman.model.ModelRoute;
import com.knotlink.salseman.model.task.ModelTask;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragNewCustomer extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private String strUserId;
    private String strRouteId;
    private List<ModelRoute> tModelsRoute;

    private GPSTracker tGpsTracker;
    private String strLat;
    private String strLong;

    @BindView(R.id.spnNewCustomerRoute)
    protected Spinner spnNewCustomerRoute;
    @BindView(R.id.spnNewCustomerCity)
    protected Spinner spnNewCustomerCity;
    @BindView(R.id.etNewCustomerShopName)
    protected EditText etNewCustomerShopName;
    @BindView(R.id.etNewCustomerContactName)
    protected EditText etNewCustomerContactName;
    @BindView(R.id.etNewCustomerContactNumber)
    protected EditText etNewCustomerContactNumber;
    @BindView(R.id.etNewCustomerEmail)
    protected EditText etNewCustomerEmail;
    @BindView(R.id.etNewCustomerWhatsAppNo)
    protected EditText etNewCustomerWhatsAppNo;
    @BindView(R.id.etNewCustomerGst)
    protected EditText etNewCustomerGst;
    @BindView(R.id.etNewCustomerAddress)
    protected EditText etNewCustomerAddress;

    @BindView(R.id.etNewCustomerLandLine)
    protected EditText etNewCustomerLandLine;
    @BindView(R.id.etNewCustomerPinCode)
    protected EditText etNewCustomerPinCode;
    @BindView(R.id.etNewCustomerRemarks)
    protected EditText etNewCustomerRemarks;
    @BindView(R.id.btnNewCustomerSubmit)
    protected Button btnNewCustomerSubmit;
    @BindView(R.id.pbSpnNewCustomerRoute)
    protected ProgressBar pbSpnNewCustomerRoute;

    private ModelTask tModel;
    public static FragNewCustomer newInstance(ModelTask tModel) {
        FragNewCustomer fragment = new FragNewCustomer();
        fragment.tModel = tModel;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_customer, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        SetTitle.tbTitle("New Customer Registration", getActivity());
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tGpsTracker = new GPSTracker(tContext);
        strUserId = tSharedPrefManager.getUserId();
        etNewCustomerShopName.setText(tModel.getOrgName());
        etNewCustomerContactName.setText(tModel.getCustomerName());
        etNewCustomerEmail.setText(tModel.getEmail());
        etNewCustomerLandLine.setText(tModel.getLandlineNo());
        etNewCustomerWhatsAppNo.setText(tModel.getWhatsappNo());
        etNewCustomerContactNumber.setText(tModel.getCustomerContactNo());
        etNewCustomerAddress.setText(tModel.getCustomerAddress());
        etNewCustomerPinCode.setText(tModel.getPincode());
        String[] strCity = getResources().getStringArray(R.array.city_list);
        AdapterCity tAdapterCity = new AdapterCity(tContext, strCity);
        spnNewCustomerCity.setAdapter(tAdapterCity);
        pbSpnNewCustomerRoute.setVisibility(View.VISIBLE);
        callApiRoute();
        spnNewCustomerRoute.setOnItemSelectedListener(this);
    }
    @OnClick(R.id.tvCurLocNewCustomer)
    public void tvCurLocNewCustomerClicked(View view){
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        etNewCustomerPinCode.setText(tGpsTracker.getPostalCode(tContext));
        etNewCustomerAddress.setText(tGpsTracker.getAddressLine(tContext));
    }
    private void callApiRoute(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.userRouteList (strUserId);
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                tModelsRoute = response.body();
                pbSpnNewCustomerRoute.setVisibility(View.GONE);
                AdapterRouteSelect tAdapterRoute = new AdapterRouteSelect(tContext, tModelsRoute);
                spnNewCustomerRoute.setAdapter(tAdapterRoute);
            }
            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {
            }
        });
    }

    private void callApi(){
        String strTaskId = tModel.getTaskId();
        String strShopName = etNewCustomerShopName.getText().toString().trim();
        String strContactName = etNewCustomerContactName.getText().toString().trim();
        String strContactNo = etNewCustomerContactNumber.getText().toString().trim();
        String strEmail = etNewCustomerEmail.getText().toString().trim();
        String strAddress = etNewCustomerAddress.getText().toString().trim();
        String strCity =     tGpsTracker.getCity(tContext);
        String strPincode = etNewCustomerPinCode.getText().toString().trim();
        String strGstNo = etNewCustomerGst.getText().toString().trim();
        String strLandLine = etNewCustomerLandLine.getText().toString().trim();
        String strWhatsAppNo = etNewCustomerWhatsAppNo.getText().toString().trim();
        String strRemarks = etNewCustomerRemarks.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);


        Call<ModelNewCustomer> customerCall = api.newCustomer(strTaskId,strUserId,strRouteId,strShopName,strContactName,strContactNo,strEmail,strAddress,
                strCity,strPincode,strGstNo,strLandLine,strWhatsAppNo,strRemarks,"","","",strLat,strLong);
        customerCall.enqueue(new Callback<ModelNewCustomer>() {
            @Override
            public void onResponse(Call<ModelNewCustomer> call, Response<ModelNewCustomer> response) {
                ModelNewCustomer tModel = response.body();
                CustomToast.toastTop(getActivity(), tModel.getMessage());
                getFragmentManager().beginTransaction().remove(FragNewCustomer.this).commit();
                getFragmentManager().beginTransaction().replace(R.id.container_main, new FragTask()).commit();
            }
            @Override
            public void onFailure(Call<ModelNewCustomer> call, Throwable t) {

                CustomLog.d(Constant.TAG, "New Customer Failure : "+t);
            }
        });
    }
    @OnClick(R.id.btnNewCustomerSubmit)
    public void btnNewCustomerSubmitClicked(View view){
        callApi();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        strRouteId = tModelsRoute.get(position).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
