package com.knotlink.salseman.adapter.spinner;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelExpenseList;

import java.util.List;

public class AdapterExpenseSpinner extends BaseAdapter {
    private Context tContext;
    private List<ModelExpenseList> tModels;
    private LayoutInflater inflater;

    public AdapterExpenseSpinner(Context tContext, List<ModelExpenseList> tModels) {
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
        names.setText(tModels.get(i).getExpenseType());
        return view;
    }
}