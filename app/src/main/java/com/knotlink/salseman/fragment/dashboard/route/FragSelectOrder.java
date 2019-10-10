package com.knotlink.salseman.fragment.dashboard.route;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.act.AdapterArticleNo;
import com.knotlink.salseman.adapter.spinner.order.AdapterBrand;
import com.knotlink.salseman.adapter.spinner.order.AdapterCategory;
import com.knotlink.salseman.adapter.spinner.order.AdapterColor;
import com.knotlink.salseman.adapter.spinner.order.AdapterSize;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.dash.route.order.ModelArticleNo;
import com.knotlink.salseman.model.dash.route.order.ModelBrand;
import com.knotlink.salseman.model.dash.route.order.ModelCategoryArticle;
import com.knotlink.salseman.model.dash.route.order.ModelColorArticle;
import com.knotlink.salseman.model.dash.route.order.ModelSizeArticle;
import com.knotlink.salseman.model.dash.route.order.ModelPriceStock;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.SetTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSelectOrder extends Fragment implements AdapterView.OnItemSelectedListener {

    private String[] strQtyArr;
    private ArrayAdapter<String> strQtyAdapter;
    private Context tContext;
    private List<ModelBrand> tModelsBrand;
    private List<ModelArticleNo> tModelsArticleNo;
    private List<ModelCategoryArticle> tModelsCategory;
    private List<ModelColorArticle> tModelsColor;
    private List<ModelSizeArticle> tModelsSize;
    private List<ModelPriceStock> tModelsStock;
    private ModelPriceStock tModelPriceStock;

    private String strBrand;
    private String strArticleNo;
    private String strCategory;
    private String strColor;
    private String strSize;
    private String strUnitPrice;
    private String strStockQty;
    private int strQuantity;
    private int strTotalPrice;
    private int strStockRem;


    @BindView(R.id.pbSpnSelectOrderBrand)
    protected ProgressBar pbSpnSelectOrderBrand;
    @BindView(R.id.pbSpnSelectOrderCategory)
    protected ProgressBar pbSpnSelectOrderCategory;
    @BindView(R.id.pbSpnSelectOrderColor)
    protected ProgressBar pbSpnSelectOrderColor;
    @BindView(R.id.pbSpnSelectOrderSize)
    protected ProgressBar pbSpnSelectOrderSize;
//    @BindView(R.id.pbSpnSelectOrderQty)
//    protected ProgressBar pbSpnSelectOrderQty;
    @BindView(R.id.spnSelectOrderBrand)
    protected Spinner spnSelectOrderBrand;
    @BindView(R.id.aCtvArticleNo)
    protected AutoCompleteTextView aCtvArticleNo;
    @BindView(R.id.spnSelectOrderCategory)
    protected Spinner spnSelectOrderCategory;
    @BindView(R.id.spnSelectOrderColor)
    protected Spinner spnSelectOrderColor;
    @BindView(R.id.spnSelectOrderSize)
    protected Spinner spnSelectOrderSize;
    @BindView(R.id.spnSelectOrderQty)
    protected Spinner spnSelectOrderQty;

    @BindView(R.id.tvSelectOrderPrice)
    protected TextView tvSelectOrderPrice;
    @BindView(R.id.tvSelectOrderTotalPrice)
    protected TextView tvSelectOrderTotalPrice;

    @BindView(R.id.tvSelectOrderStockQtyRem)
    protected TextView tvSelectOrderStockQtyRem;
    @BindView(R.id.tvSelectOrderStockQty)
    protected TextView tvSelectOrderStockQty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_order, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        callApiBrand();
        return view;
    }
    private void initFrag(){
        SetTitle.tbTitle("Select Order", getActivity());
        tContext = getContext();
        strQtyArr = getResources().getStringArray(R.array.spinner_count);
        strQtyAdapter = new ArrayAdapter<String>(tContext, android.R.layout.simple_spinner_dropdown_item, strQtyArr);
        spnSelectOrderBrand.setOnItemSelectedListener(this);
        spnSelectOrderCategory.setOnItemSelectedListener(this);
        spnSelectOrderColor.setOnItemSelectedListener(this);
        spnSelectOrderSize.setOnItemSelectedListener(this);
        spnSelectOrderQty.setOnItemSelectedListener(this);
        aCtvArticleNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strArticleNo = tModelsArticleNo.get(position).getArticleNo();
                callApiCategory(strArticleNo);
            }
        });
    }

    private void callApiBrand(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelBrand>> call = api.getBrand();
        call.enqueue(new Callback<List<ModelBrand>>() {
            @Override
            public void onResponse(Call<List<ModelBrand>> call, Response<List<ModelBrand>> response) {
                tModelsBrand = response.body();
                pbSpnSelectOrderBrand.setVisibility(View.GONE);
                AdapterBrand tAdapter = new AdapterBrand(tContext, tModelsBrand);
                spnSelectOrderBrand.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelBrand>> call, Throwable t) {

            }
        });

    }

    private void callApiArticleNo(String strBrand){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelArticleNo>> customerListCall = api.getArticleNo(strBrand);
        customerListCall.enqueue(new Callback<List<ModelArticleNo>>() {
            @Override
            public void onResponse(Call<List<ModelArticleNo>> call, Response<List<ModelArticleNo>> response) {
                int i;
                tModelsArticleNo = response.body();
                ArrayList<String> searchArrayList = new ArrayList<String>();

                for (i = 0; i < tModelsArticleNo.size(); i++) {
                    searchArrayList.add(tModelsArticleNo.get(i).getArticleNo());
                }
                AdapterArticleNo adapter = new AdapterArticleNo(tContext, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, searchArrayList);
                aCtvArticleNo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ModelArticleNo>> call, Throwable t) {

            }
        });
    }

    private void callApiCategory(String strArticleNo){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelCategoryArticle>> call = api.getCategoryArticle(strArticleNo);
        call.enqueue(new Callback<List<ModelCategoryArticle>>() {
            @Override
            public void onResponse(Call<List<ModelCategoryArticle>> call, Response<List<ModelCategoryArticle>> response) {
                tModelsCategory = response.body();
                pbSpnSelectOrderCategory.setVisibility(View.GONE);
                AdapterCategory tAdapter = new AdapterCategory(tContext, tModelsCategory);
                spnSelectOrderCategory.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelCategoryArticle>> call, Throwable t) {

            }
        });

    }
    private void callApiColor(String strArticleNo, String strCategory){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelColorArticle>> call = api.getColorArticle(strArticleNo, strCategory);
        call.enqueue(new Callback<List<ModelColorArticle>>() {
            @Override
            public void onResponse(Call<List<ModelColorArticle>> call, Response<List<ModelColorArticle>> response) {
                tModelsColor = response.body();
                pbSpnSelectOrderColor.setVisibility(View.GONE);
                AdapterColor tAdapter = new AdapterColor(tContext, tModelsColor);
                spnSelectOrderColor.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelColorArticle>> call, Throwable t) {

            }
        });

    }
    private void callApiSize(String strArticleNo, String strCategory, String strColor){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSizeArticle>> call = api.getSizeArticle(strArticleNo, strCategory, strColor);
        call.enqueue(new Callback<List<ModelSizeArticle>>() {
            @Override
            public void onResponse(Call<List<ModelSizeArticle>> call, Response<List<ModelSizeArticle>> response) {
                tModelsSize = response.body();
                pbSpnSelectOrderSize.setVisibility(View.GONE);
                AdapterSize tAdapter = new AdapterSize(tContext, tModelsSize);
                spnSelectOrderSize.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelSizeArticle>> call, Throwable t) {

            }
        });

    }
    private void callApiPriceQty(String strArticleNo, String strCategory, String strColor, String strSize){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelPriceStock> call = api.getPriceStockArticle(strArticleNo, strCategory, strColor,strSize);
        call.enqueue(new Callback<ModelPriceStock>() {
            @Override
            public void onResponse(Call<ModelPriceStock> call, Response<ModelPriceStock> response) {
                tModelPriceStock = response.body();
                assert tModelPriceStock != null;
                strUnitPrice = tModelPriceStock.getUnitPrice();
                strStockQty = tModelPriceStock.getInStock();

                tvSelectOrderPrice.setText(strUnitPrice);
                tvSelectOrderStockQty.setText(strStockQty);
            }

            @Override
            public void onFailure(Call<ModelPriceStock> call, Throwable t) {

            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
                case R.id.spnSelectOrderBrand:
                    strBrand = tModelsBrand.get(position).getBrandName();
                    callApiArticleNo(strBrand);

                    break;

                case R.id.spnSelectOrderCategory:
                     strCategory = tModelsCategory.get(position).getCategory();
                     callApiColor(strArticleNo, strCategory);
                     break;

                case R.id.spnSelectOrderColor:
                     strColor = tModelsColor.get(position).getColor();
                     callApiSize(strArticleNo, strCategory, strColor);
                     break;

                case R.id.spnSelectOrderSize:
                    strSize = tModelsSize.get(position).getSize();
                    callApiPriceQty(strArticleNo, strCategory, strColor, strSize);
                    spnSelectOrderQty.setAdapter(strQtyAdapter);
                break;

                case R.id.spnSelectOrderQty:
                    strQuantity = position;
                    Log.d(Constant.TAG,"Unit price : "+strUnitPrice);
//                    int price = Integer.valueOf(strUnitPrice);
//                    strTotalPrice = strUnitPrice*strQuantity;
//                    tvSelectOrderTotalPrice.setText(String.valueOf(strTotalPrice));
//                    int stock = Integer.valueOf(strStockQty);
//                    strStockRem = stock-strQuantity;
//                    tvSelectOrderStockQtyRem.setText(String.valueOf(strStockRem));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
