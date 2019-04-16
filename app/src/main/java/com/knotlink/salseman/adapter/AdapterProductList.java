package com.knotlink.salseman.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelProductList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ProductViewHolder> {

    private List<ModelProductList> tLists = new ArrayList<>();
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_product_item, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {

//        final ModelProductList tList = tLists.get(i);
//        if (tList.isChecked()){
//            productViewHolder.cbProductList.setChecked(true);
//        }
//        else {
//            productViewHolder.cbProductList.setChecked(false);
//        }
//        productViewHolder.cbProductList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tList.isChecked()){
//                    tList.setChecked(true);
//                }else {
//                    tList.setChecked(false);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.et_product_list_black)
        protected EditText etProListBlack;
        @BindView(R.id.et_product_list_blue)
        protected EditText etProListBlue;
        @BindView(R.id.et_product_list_white)
        protected EditText etProListWhite;
        @BindView(R.id.cb_product_list)
        protected CheckBox cbProductList;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
