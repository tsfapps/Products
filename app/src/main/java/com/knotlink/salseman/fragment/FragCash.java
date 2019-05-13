package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelCash;
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

public class FragCash extends Fragment {

    private Context tContext;

    private SharedPrefManager tSharedPrefManager;
    private ModelCash tModels;
    private int countTwoTh = 0;
    private int countFiveH = 0;
    private int countTwoHundred = 0;
    private int countHundred = 0;
    private int countFifty = 0;
    private int countTwenty = 0;
    private int countTen = 0;
    private int countFive = 0;
    private int countTwo = 0;
    private int countOne = 0;
    private int sumTwoTh;
    private int sumFiveH;
    private int sumTwoHundred;
    private int sumOneHundred;
    private int sumFifty;
    private int sumTwenty;
    private int sumTen;
    private int sumFive;
    private int sumTwo;
    private int sumOne;
    private int sumTotal = 0;
    private int cashTotalCount = 0;

   @BindView(R.id.tv_cash_sum)
   protected TextView tvCashSum;
    @BindView(R.id.tv_total_cash_count)
    protected TextView tvTotalCashCount;
    @BindView(R.id.tv_count_2000)
    protected TextView tvCountTwoK;
   @BindView(R.id.tv_sum_2k)
    protected TextView tvSumTwoK;
    @BindView(R.id.tv_count_500)
    protected TextView tvCount500;
   @BindView(R.id.tv_sum_500)
    protected TextView tvSum500;
 @BindView(R.id.tv_cash_count_twoHundred)
    protected TextView tvCountTwoHundred;
   @BindView(R.id.tv_sum_twoHundred)
    protected TextView tvSumTwoHundred;
 @BindView(R.id.tv_cash_count_hundred)
    protected TextView tvCountHundred;
   @BindView(R.id.tv_sum_hundred)
    protected TextView tvSumHundred;
 @BindView(R.id.tv_cash_count_fifty)
    protected TextView tvCountFifty;
   @BindView(R.id.tv_sum_fifty)
    protected TextView tvSumtFifty;
 @BindView(R.id.tv_cash_count_twenty)
    protected TextView tvCountTwenty;
   @BindView(R.id.tv_sum_twenty)
    protected TextView tvSumTwenty;
 @BindView(R.id.tv_cash_count_ten)
    protected TextView tvCountTen;
   @BindView(R.id.tv_sum_ten)
    protected TextView tvSumTen;
 @BindView(R.id.tv_cash_count_five)
    protected TextView tvCountFive;
   @BindView(R.id.tv_sum_five)
    protected TextView tvSumFive;
 @BindView(R.id.tv_cash_count_two)
    protected TextView tvCountTwo;
   @BindView(R.id.tv_sum_two)
    protected TextView tvSumTwo;
 @BindView(R.id.tv_cash_count_one)
    protected TextView tvCountOne;
   @BindView(R.id.tv_sum_one)
    protected TextView tvSumOne;

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
    }

   @OnClick(R.id.btn_add_2000)
    public void add2KClicked(View view){
        countTwoTh++;
        tvCountTwoK.setText(String.valueOf(countTwoTh));
        sumTwoTh = countTwoTh*2000;
        tvSumTwoK.setText(String.valueOf(sumTwoTh));
        sumTotal = sumTotal+2000;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_2000)
    public void less2KClicked(View view){
        if (countTwoTh>0) {
            countTwoTh--;
            tvCountTwoK.setText(String.valueOf(countTwoTh));
            sumTwoTh = countTwoTh*2000;
            tvSumTwoK.setText(String.valueOf(sumTwoTh));
            sumTotal = sumTotal-2000;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
    @OnClick(R.id.btn_add_500)
    public void add5HClicked(View view){
        countFiveH++;
        tvCount500.setText(String.valueOf(countFiveH));
        sumFiveH = countFiveH *500;
        tvSum500.setText(String.valueOf(sumFiveH));
        sumTotal = sumTotal+500;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_500)
    public void less5HClicked(View view){
        if (countFiveH>0) {
            countFiveH--;
            tvCount500.setText(String.valueOf(countFiveH));
            sumFiveH = countFiveH *500;
            tvSum500.setText(String.valueOf(sumFiveH));
            sumTotal = sumTotal-500;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }

 @OnClick(R.id.btn_add_twoHundred)
    public void addTwoHundredClicked(View view){
        countTwoHundred++;
        tvCountTwoHundred.setText(String.valueOf(countTwoHundred));
        sumTwoHundred = countTwoHundred *200;
        tvSumTwoHundred.setText(String.valueOf(sumTwoHundred));
        sumTotal = sumTotal+200;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_twoHundred)
    public void lessTwoHundredClicked(View view){
        if (countTwoHundred>0) {
            countTwoHundred--;
            tvCountTwoHundred.setText(String.valueOf(countTwoHundred));
            sumTwoHundred = countTwoHundred *200;
            tvSumTwoHundred.setText(String.valueOf(sumTwoHundred));
            sumTotal = sumTotal-200;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_hundred)
    public void addHundredClicked(View view){
        countHundred++;
        tvCountHundred.setText(String.valueOf(countHundred));
        sumOneHundred = countHundred *100;
        tvSumHundred.setText(String.valueOf(sumOneHundred));
        sumTotal = sumTotal+100;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_hundred)
    public void lessHundredClicked(View view){
        if (countHundred>0) {
            countHundred--;
            tvCountHundred.setText(String.valueOf(countHundred));
            sumOneHundred = countHundred *100;
            tvSumHundred.setText(String.valueOf(sumOneHundred));
            sumTotal = sumTotal-100;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
    @OnClick(R.id.btn_add_fifty)
    public void addFiftyClicked(View view){
        countFifty++;
        tvCountFifty.setText(String.valueOf(countFifty));
        sumFifty = countFifty *50;
        tvSumtFifty.setText(String.valueOf(sumFifty));
        sumTotal = sumTotal+50;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
    }

    @OnClick(R.id.btn_less_fifty)
    public void lessFiftyClicked(View view){
        if (countFifty>0) {
            countFifty--;
            tvCountFifty.setText(String.valueOf(countFifty));
            sumFifty = countFifty *50;
            tvSumtFifty.setText(String.valueOf(sumFifty));
            sumTotal = sumTotal-50;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_twenty)
    public void addTwentyClicked(View view){
        countTwenty++;
        tvCountTwenty.setText(String.valueOf(countTwenty));
        sumTwenty = countTwenty *20;
        tvSumTwenty.setText(String.valueOf(sumTwenty));
        sumTotal = sumTotal+20;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_twenty)
    public void less5TwentyClicked(View view){
        if (countTwenty>0) {
            countTwenty--;
            tvCountTwenty.setText(String.valueOf(countTwenty));
            sumTwenty = countTwenty *20;
            tvSumTwenty.setText(String.valueOf(sumTwenty));
            sumTotal = sumTotal-20;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_ten)
    public void addTenClicked(View view){
        countTen++;
     tvCountTen.setText(String.valueOf(countTen));
        sumTen = countTen *10;
     tvSumTen.setText(String.valueOf(sumTen));
        sumTotal = sumTotal+10;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_ten)
    public void lessTenClicked(View view){
        if (countTen>0) {
            countTen--;
            tvCountTen.setText(String.valueOf(countTen));
            sumTen = countTen * 10;
            tvSumTen.setText(String.valueOf(sumTen));
            sumTotal = sumTotal-10;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_five)
    public void addFiveClicked(View view){
        countFive++;
     tvCountFive.setText(String.valueOf(countFive));
        sumFive = countFive *5;
     tvSumFive.setText(String.valueOf(sumFive));
        sumTotal = sumTotal+5;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_five)
    public void lessFiveClicked(View view){
        if (countFive>0) {
            countFive--;
            tvCountFive.setText(String.valueOf(countFive));
            sumFive = countFive *5;
            tvSumFive.setText(String.valueOf(sumFive));
            sumTotal = sumTotal-5;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_two)
    public void addTwoClicked(View view){
        countTwo++;
     tvCountTwo.setText(String.valueOf(countTwo));
     sumTwo = countTwo *2;
     tvSumTwo.setText(String.valueOf(sumTwo));
        sumTotal = sumTotal+2;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_two)
    public void lessTwoClicked(View view){
        if (countTwo>0) {
            countTwo--;
            tvCountTwo.setText(String.valueOf(countTwo));
            sumTwo = countTwo *2;
            tvSumTwo.setText(String.valueOf(sumTwo));
            sumTotal = sumTotal-2;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
 @OnClick(R.id.btn_add_one)
    public void addOneClicked(View view){
        countOne++;
     tvCountOne.setText(String.valueOf(countOne));
     sumOne = countOne *1;
     tvSumOne.setText(String.valueOf(sumOne));
        sumTotal = sumTotal+1;
        tvCashSum.setText(String.valueOf(sumTotal));
        cashTotalCount = cashTotalCount+1;
        tvTotalCashCount.setText(String.valueOf(cashTotalCount));
   }

    @OnClick(R.id.btn_less_one)
    public void lessOneClicked(View view){
        if (countOne>0) {
            countOne--;
            tvCountOne.setText(String.valueOf(countOne));
            sumOne = countOne *1;
            tvSumOne.setText(String.valueOf(sumOne));
            sumTotal = sumTotal-1;
            tvCashSum.setText(String.valueOf(sumTotal));
            cashTotalCount = cashTotalCount-1;
            tvTotalCashCount.setText(String.valueOf(cashTotalCount));
        }
    }
    @OnClick(R.id.btn_cash_submit)
    public void cashSubmit(View view){
        callApi();
    }
    private void callApi(){

        String strUserId = tSharedPrefManager.getUserId();
        String str2000 = String.valueOf(sumTwoTh);
        String str500 = String.valueOf(sumFiveH);
        String str200 = String.valueOf(sumTwoHundred);
        String str100 = String.valueOf(sumOneHundred);
        String str50 = String.valueOf(sumFifty);
        String str20 = String.valueOf(sumTwenty);
        String str10 = String.valueOf(sumTen);
        String str5 = String.valueOf(sumFive);
        String str2 = String.valueOf(sumTwo);
        String str1 = String.valueOf(sumOne);
        String strTotal = String.valueOf(sumTotal);
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelCash> cashCall = api.uploadCashCollection(strUserId, str2000, str500, str200, str100, str50, str20, str10,
                str5, str2, str1, strTotal);
        cashCall.enqueue(new Callback<ModelCash>() {
            @Override
            public void onResponse(Call<ModelCash> call, Response<ModelCash> response) {
                tModels = response.body();
                if (!tModels.getError()) {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragCash.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
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
}
