package com.knotlink.salseman.adapter.spinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.ModelShopStatus;

import java.util.List;

public class AdapterSpinnerShopStatus extends BaseAdapter {

    private Context tContext;
    private List<ModelShopStatus> tModels;
    private LayoutInflater tInflater;

    public AdapterSpinnerShopStatus(Context tContext, List<ModelShopStatus> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
        tInflater = (LayoutInflater.from(tContext));
    }
    @Override
    public int getCount() {
        return tModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = tInflater.inflate(R.layout.spn_expense_type, null);
        TextView tvStatus = convertView.findViewById(R.id.tvExpensesSpinner);
        tvStatus.setText(tModels.get(position).getStatus());
        return convertView;
    }


}
