package com.knotlink.salseman.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterProductList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    private AdapterProductList tAdapter;
    @BindView(R.id.rv_product_list)
    protected RecyclerView rvProductList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        initApi();
    }

    private void initApi(){
        RecyclerView.LayoutManager tLayoutManager = new LinearLayoutManager(this);
        rvProductList.setLayoutManager(tLayoutManager);
        tAdapter = new AdapterProductList();
        rvProductList.setAdapter(tAdapter);
    }
}
