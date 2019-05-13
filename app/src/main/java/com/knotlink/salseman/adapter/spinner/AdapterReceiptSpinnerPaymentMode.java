package com.knotlink.salseman.adapter.spinner;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knotlink.salseman.R;

public class AdapterReceiptSpinnerPaymentMode extends BaseAdapter {
    private Context tContext;
    private String[] strPaymentMode;
    private LayoutInflater inflater;

    public AdapterReceiptSpinnerPaymentMode(Context tContext, String[] strPaymentMode) {
        this.tContext = tContext;
        this.strPaymentMode = strPaymentMode;
        inflater = (LayoutInflater.from(tContext));
    }


    @Override
    public int getCount() {
        return strPaymentMode.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spn_receipt_payment_mode, null);
        TextView names =  view.findViewById(R.id.tv_report_spinner_activity);
        names.setText(strPaymentMode[i]);
        return view;
    }
}