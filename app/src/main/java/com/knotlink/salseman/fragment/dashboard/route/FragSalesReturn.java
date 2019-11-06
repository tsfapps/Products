package com.knotlink.salseman.fragment.dashboard.route;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.spinner.AdapterReceiptSpinnerInvoice;
import com.knotlink.salseman.adapter.spinner.order.AdapterCategory;
import com.knotlink.salseman.adapter.spinner.route.AdapterSalesRetArticle;
import com.knotlink.salseman.adapter.spinner.route.AdapterSalesRetSize;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnArticle;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnInsertion;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnMrp;
import com.knotlink.salseman.model.dash.route.ModelSalesReturnSize;
import com.knotlink.salseman.model.dash.route.ModelShopList;
import com.knotlink.salseman.model.dash.route.order.ModelCategoryArticle;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetTitle;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSalesReturn extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context tContext;
    private List<ModelSalesReturnArticle> tModelSalesReturnArticle;
    private List<ModelSalesReturnSize> tModelSalesReturnSize;
    private Bitmap tBitmap;
    private String strInvoiceNo;
    private String strArticle;
    private String strSize;
    private String strMrp;

    private String strLat;
    private String strLong;
    private String strAddress;
    private String strUserId;
    private String strShopId;

    final Calendar myCalendar = Calendar.getInstance();



    @BindView(R.id.spnReturnInvoiceNumber)
    protected Spinner spnReturnInvoiceNumber;
    @BindView(R.id.spnReturnArticle)
    protected Spinner spnReturnArticle;
    @BindView(R.id.spnReturnSize)
    protected Spinner spnReturnSize;
    @BindView(R.id.pbSalesRetInvoice)
    protected ProgressBar pbSalesRetInvoice;
    @BindView(R.id.pbSalesRetArticle)
    protected ProgressBar pbSalesRetArticle;
    @BindView(R.id.pbSalesRetSize)
    protected ProgressBar pbSalesRetSize;
    @BindView(R.id.tvReturnShopName)
    protected TextView tvReturnShopName;
    @BindView(R.id.tvReturnDate)
    protected TextView tvReturnDate;
    @BindView(R.id.tvReturnMrp)
    protected TextView tvReturnMrp;
    @BindView(R.id.etReturnQuantity)
    protected EditText etReturnQuantity;
    @BindView(R.id.etReturnRemarks)
    protected EditText etReturnRemarks;
    @BindView(R.id.btnSalesReturnSubmit)
    protected Button btnReturnSubmit;

    private String tTitle;


    private List<ModelShopList> tModels;
    private int i;
    private String strUserType;
    private String strSelectedUserId;

    public static FragSalesReturn newInstance(List<ModelShopList> tModels, int i, String strUserType, String strSelectedUserId) {

        FragSalesReturn fragment = new FragSalesReturn();
        fragment.tModels = tModels;
        fragment.i = i;
        fragment.strSelectedUserId = strSelectedUserId;
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
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId  = tSharedPrefManager.getUserId();
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        strLat = String.valueOf(tGpsTracker.latitude);
        strLong = String.valueOf(tGpsTracker.longitude);
        strAddress = tGpsTracker.getAddressLine(tContext);
        SetTitle.tbTitle("Sales Return", getActivity());
        tvReturnShopName.setText(tModels.get(i).getShopName());
        strShopId = tModels.get(i).getShopId();
        AdapterReceiptSpinnerInvoice adapterReceiptSpinnerInvoice = new AdapterReceiptSpinnerInvoice(tContext, tModels, i);
        spnReturnInvoiceNumber.setAdapter(adapterReceiptSpinnerInvoice);
        pbSalesRetInvoice.setVisibility(View.GONE);
        spnReturnInvoiceNumber.setOnItemSelectedListener(this);
        spnReturnArticle.setOnItemSelectedListener(this);

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
                        btnReturnSubmit.setEnabled(false);
                        btnReturnSubmit.setBackgroundResource(R.drawable.bg_btn_disabled);
                        callApiSalesReturn();
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
    @OnClick(R.id.btnSalesReturnSubmit)
    public void btnReturnSubmitClicked(View view){

              confirmSubmit();


    }

public void callSuccessDialog(String strTitle){
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

    private void callApiSalesReturnArticle(String strInvoiceNo, final int position){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesReturnArticle>> call = api.salesReturnArticle(strInvoiceNo);
        call.enqueue(new Callback<List<ModelSalesReturnArticle>>() {
            @Override
            public void onResponse(Call<List<ModelSalesReturnArticle>> call, Response<List<ModelSalesReturnArticle>> response) {
                tModelSalesReturnArticle = response.body();
                pbSalesRetArticle.setVisibility(View.GONE);
                if (tModelSalesReturnArticle.size()>0){
                    tvReturnDate.setText(tModelSalesReturnArticle.get(position).getInvoiceDate());
                AdapterSalesRetArticle tAdapter = new AdapterSalesRetArticle(tContext, tModelSalesReturnArticle);
                spnReturnArticle.setAdapter(tAdapter);}
                else {
                    Toast.makeText(tContext, "No article no is for this invoice...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelSalesReturnArticle>> call, Throwable t) {

            }
        });

    }
    private void callApiSalesReturnSize(String strInvoiceNo, String strArticle){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesReturnSize>> call = api.salesReturnSize(strInvoiceNo, strArticle);
        call.enqueue(new Callback<List<ModelSalesReturnSize>>() {
            @Override
            public void onResponse(Call<List<ModelSalesReturnSize>> call, Response<List<ModelSalesReturnSize>> response) {
                tModelSalesReturnSize = response.body();
                pbSalesRetSize.setVisibility(View.GONE);
                if (tModelSalesReturnSize.size()>0){
                    tvReturnMrp.setText(tModelSalesReturnSize.get(0).getMrp());
                    AdapterSalesRetSize tAdapter = new AdapterSalesRetSize(tContext, tModelSalesReturnSize);
                    spnReturnSize.setAdapter(tAdapter);}
                else {
                    Toast.makeText(tContext, "No article no is for this invoice...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelSalesReturnSize>> call, @NonNull Throwable t) {

                Log.d(Constant.TAG, "Size Api Failure : "+t);

            }
        });

    }
//    private void callApiSalesReturnMrp(String strInvoiceNo, String strArticle, String strSize){
//        Api api = ApiClients.getApiClients().create(Api.class);
//        Call<ModelSalesReturnMrp> call = api.salesReturnMrp(strInvoiceNo, strArticle, strSize);
//        call.enqueue(new Callback<ModelSalesReturnMrp>() {
//            @Override
//            public void onResponse(Call<ModelSalesReturnMrp> call, Response<ModelSalesReturnMrp> response) {
//                ModelSalesReturnMrp tModelSalesReturnMrp = response.body();
//                if (tModelSalesReturnMrp != null) {
//                    strMrp = tModelSalesReturnMrp.getMrp();
//                    tvReturnMrp.setText(strMrp);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelSalesReturnMrp> call, Throwable t) {
//
//            }
//        });
//
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spnReturnInvoiceNumber:
                strInvoiceNo = tModels.get(i).getInvoiceNo().get(position);
                callApiSalesReturnArticle(strInvoiceNo, position);
                break;
            case R.id.spnReturnArticle:
                strArticle = tModelSalesReturnArticle.get(position).getArticleNo().get(position);
                Log.d(Constant.TAG, "Article No. : "+strArticle);
                callApiSalesReturnSize(strInvoiceNo, strArticle);
                break;
//            case R.id.spnReturnSize:
//                strSize = tModelSalesReturnSize.get(position).getSize();
//                callApiSalesReturnMrp(strInvoiceNo, strArticle, strSize);
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parent.getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void callApiSalesReturn(){
        String strDate = tvReturnDate.getText().toString().trim();
        String strQuantity = etReturnQuantity.getText().toString().trim();
        String strRemarks = etReturnRemarks.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelSalesReturnInsertion> call = api.salesReturn(strShopId, strUserId, strInvoiceNo,strDate,
                strArticle,strSize,strMrp,strQuantity,strRemarks,strLat, strLong, strAddress);
        call.enqueue(new Callback<ModelSalesReturnInsertion>() {
            @Override
            public void onResponse(Call<ModelSalesReturnInsertion> call, Response<ModelSalesReturnInsertion> response) {
                ModelSalesReturnInsertion tModel = response.body();
                if (tModel != null) {
                    if (!tModel.getError()){
                        callSuccessDialog(tModel.getMessage());

                    }
                    else {
                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelSalesReturnInsertion> call, Throwable t) {

            }
        });
    }


}


