package com.knotlink.salseman.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.knotlink.salseman.R;

public class AdapterReportSpinnerActivity extends BaseAdapter {
    private Context tContext;
    private int flags[];
    private String[] countryNames;
    private LayoutInflater inflter;

    public AdapterReportSpinnerActivity(Context tContext, int[] flags, String[] countryNames) {
        this.tContext = tContext;
        this.flags = flags;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(tContext));
    }

    @Override
    public int getCount() {
        return flags.length;
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
        view = inflter.inflate(R.layout.custom_spn_reports, null);
        ImageView icon =  view.findViewById(R.id.iv_report_spinner_activity);
        TextView names =  view.findViewById(R.id.tv_report_spinner_activity);
        icon.setImageResource(flags[i]);
        names.setText(countryNames[i]);
        return view;
    }

}