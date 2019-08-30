package com.knotlink.salseman.adapter.spinner;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelExpenseList;
import com.knotlink.salseman.model.ModelVehicleList;

import java.util.List;

public class AdapterVehicleSpinner extends BaseAdapter {
    private Context tContext;
    private List<ModelVehicleList> tModels;
    private LayoutInflater inflater;

    public AdapterVehicleSpinner(Context tContext, List<ModelVehicleList> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
        inflater = (LayoutInflater.from(tContext));
    }


    @Override
    public int getCount() {
        return tModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spn_expense_type, null);
        TextView names =  view.findViewById(R.id.tvExpensesSpinner);
        names.setText(tModels.get(i).getVehicleNo());
        return view;
    }
}