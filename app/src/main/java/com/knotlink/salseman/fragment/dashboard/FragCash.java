package com.knotlink.salseman.fragment.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.ModelCash;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCash extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context tContext;

    private SharedPrefManager tSharedPrefManager;
    private ModelCash tModels;
    private int count2000;
    private int count500;
    private int count200;
    private int count100;
    private int count50;
    private int count20;
    private int count10;
    private int count5;
    private int count2;
    private int count1;
    private int countCheque;
    private int sum2000;
    private int sum500;
    private int sum200;
    private int sum100;
    private int sum50;
    private int sum20;
    private int sum10;
    private int sum5;
    private int sum2;
    private int sum1;
    private int sumTotal = 0;
    private int cashTotalCount = 0;

    private String stringLatitude;
    private String stringLongitude;
    private String postalCode;
    private String addressLine;

   @BindView(R.id.tv_cash_sum)
   protected TextView tvCashSum;
    @BindView(R.id.tv_total_cash_count)
    protected TextView tvTotalCashCount;
    @BindView(R.id.spn_count_2000)
    protected Spinner spn_count_2000;
    @BindView(R.id.spn_count_500)
    protected Spinner spn_count_500;
    @BindView(R.id.spn_count_200)
    protected Spinner spn_count_200;
    @BindView(R.id.spn_count_100)
    protected Spinner spn_count_100;
    @BindView(R.id.spn_count_50)
    protected Spinner spn_count_50;
    @BindView(R.id.spn_count_20)
    protected Spinner spn_count_20;
    @BindView(R.id.spn_count_10)
    protected Spinner spn_count_10;
    @BindView(R.id.spn_count_5)
    protected Spinner spn_count_5;
    @BindView(R.id.spn_count_2)
    protected Spinner spn_count_2;
    @BindView(R.id.spn_count_1)
    protected Spinner spn_count_1;
    @BindView(R.id.spn_count_cheque)
    protected Spinner spn_count_cheque;

    @BindView(R.id.tvSum2000)
    protected TextView tvSum2000;
    @BindView(R.id.tvSum500)
    protected TextView tvSum500;
    @BindView(R.id.tvSum200)
    protected TextView tvSum200;
    @BindView(R.id.tvSum100)
    protected TextView tvSum100;
    @BindView(R.id.tvSum50)
    protected TextView tvSum50;
    @BindView(R.id.tvSum20)
    protected TextView tvSum20;
    @BindView(R.id.tvSum10)
    protected TextView tvSum10;
    @BindView(R.id.tvSum5)
    protected TextView tvSum5;
    @BindView(R.id.tvSum2)
    protected TextView tvSum2;
    @BindView(R.id.tvSum1)
    protected TextView tvSum1;
    @BindView(R.id.tvSumCheque)
    protected TextView tvSumCheque;

    private String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    public static FragCash newInstance(String strUserType, String strSelectedUserId) {

        FragCash fragment = new FragCash();
        fragment.strUserType = strUserType;
        fragment.strSelectedUserId = strSelectedUserId;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cash, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }

    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Cash Collection", getActivity());
        getLocation();
        spn_count_2000.setOnItemSelectedListener(this);
        spn_count_500.setOnItemSelectedListener(this);
        spn_count_200.setOnItemSelectedListener(this);
        spn_count_100.setOnItemSelectedListener(this);
        spn_count_50.setOnItemSelectedListener(this);
        spn_count_20.setOnItemSelectedListener(this);
        spn_count_10.setOnItemSelectedListener(this);
        spn_count_5.setOnItemSelectedListener(this);
        spn_count_2.setOnItemSelectedListener(this);
        spn_count_1.setOnItemSelectedListener(this);
        spn_count_cheque.setOnItemSelectedListener(this);
    }
    @OnClick(R.id.btn_cash_submit)
    public void cashSubmit(View view){
        if (cashTotalCount!=0||countCheque!=0) {
            callApi();
        }
        else {
            CustomToast.toastTop(getActivity(), "Can't submit empty report.");
        }
    }
    private void getLocation(){
        GPSTracker gpsTracker = new GPSTracker(tContext);
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
             stringLatitude = String.valueOf(gpsTracker.latitude);
             stringLongitude = String.valueOf(gpsTracker.longitude);
             postalCode = gpsTracker.getPostalCode(tContext);
             addressLine = gpsTracker.getAddressLine(tContext);

        }
        else
        {
            gpsTracker.showSettingsAlert();
        }
    }

    private void callApi(){
        if (strUserType.equalsIgnoreCase("3")) {
            strUserId = strSelectedUserId;
        }
        else {
            strUserId = tSharedPrefManager.getUserId();

        }        String str2000 = String.valueOf(count2000);
        String str500 = String.valueOf(count500);
        String str200 = String.valueOf(count200);
        String str100 = String.valueOf(count100);
        String str50 = String.valueOf(count50);
        String str20 = String.valueOf(count20);
        String str10 = String.valueOf(count10);
        String str5 = String.valueOf(count5);
        String str2 = String.valueOf(count2);
        String str1 = String.valueOf(count1);
        String strTotal = String.valueOf(cashTotalCount);
        String strSumTotal = String.valueOf(sumTotal);
        String strNoCheque = String.valueOf(countCheque);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelCash> cashCall = api.uploadCashCollection(strUserId, str2000, str500, str200, str100, str50, str20, str10,
                str5, str2, str1, strTotal, strSumTotal, strNoCheque,stringLatitude,stringLongitude,postalCode,addressLine);
        cashCall.enqueue(new Callback<ModelCash>() {
            @Override
            public void onResponse(Call<ModelCash> call, Response<ModelCash> response) {
                tModels = response.body();
                if (!tModels.getError()) {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragCash.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, FragDashboard.newInstance(strSelectedUserId, strUserType)).commit();
                } else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
        }
            @Override
            public void onFailure(Call<ModelCash> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding Cash Collection : "+t);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){
            case R.id.spn_count_2000:
                count2000 = position;
                sum2000 = 2000*position;
                tvSum2000.setText(String.valueOf(sum2000));
                cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
                tvTotalCashCount.setText(String.valueOf(cashTotalCount));
                sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
                tvCashSum.setText(String.valueOf(sumTotal));
                break;

            case R.id.spn_count_500:
                count500 = position;
                sum500 = 500*position;
                tvSum500.setText(String.valueOf(sum500));
                cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
                tvTotalCashCount.setText(String.valueOf(cashTotalCount));
                sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
                tvCashSum.setText(String.valueOf(sumTotal));

                break;
         case R.id.spn_count_200:
             count200 = position;
             sum200 = 200*position;
             tvSum200.setText(String.valueOf(sum200));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_100:
             count100 = position;
             sum100 = 100*position;
             tvSum100.setText(String.valueOf(sum100));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_50:
             count50 = position;
             sum50 = 50*position;
             tvSum50.setText(String.valueOf(sum50));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));

             break;
         case R.id.spn_count_20:
             count20 = position;
             sum20 = 20*position;
             tvSum20.setText(String.valueOf(sum20));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));

             break;
         case R.id.spn_count_10:
             count10 = position;
             sum10 = 10*position;
             tvSum10.setText(String.valueOf(sum10));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_5:
                count5 = position;
             sum5 = 5*position;
             tvSum5.setText(String.valueOf(sum5));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_2:
             count2 = position;
             sum2 = 2*position;
             tvSum2.setText(String.valueOf(sum2));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_1:
             count1 = position;
             sum1 = position;
             tvSum1.setText(String.valueOf(sum1));
             cashTotalCount = count2000+count500+count200+count100+count50+count20+count10+count5+count2+count1;
             tvTotalCashCount.setText(String.valueOf(cashTotalCount));
             sumTotal = sum2000+sum500+sum200+sum100+sum50+sum20+sum10+sum5+sum2+sum1;
             tvCashSum.setText(String.valueOf(sumTotal));
             break;
         case R.id.spn_count_cheque:
                countCheque = position;
                CustomLog.d(Constant.TAG, "Cheque Count : "+countCheque);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
