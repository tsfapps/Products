package com.knotlink.salseman.adapter.spinner;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelShopList;

import java.util.List;

public class AdapterReceiptSpinnerInvoice extends BaseAdapter {
    private Context tContext;
    private String[] strInvoice;
    private List<ModelShopList> tModels;
    private LayoutInflater inflater;
    private int modelIndex;

    public AdapterReceiptSpinnerInvoice(Context tContext, List<ModelShopList> tModels, int modelIndex) {
        this.tContext = tContext;
        this.tModels = tModels;
        this.modelIndex = modelIndex;
        inflater = (LayoutInflater.from(tContext));
    }

    @Override
    public int getCount() {
        return tModels.get(modelIndex).getInvoiceNo().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spn_receipt_payment_mode, null);
//        ImageView icon =  view.findViewById(R.id.iv_report_spinner_activity);
        TextView names =  view.findViewById(R.id.tv_report_spinner_activity);
//        icon.setImageResource(flags[i]);
        names.setText(tModels.get(modelIndex).getInvoiceNo().get(i));
        return view;
    }

}