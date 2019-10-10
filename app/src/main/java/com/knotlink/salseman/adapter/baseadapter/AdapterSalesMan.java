package com.knotlink.salseman.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.utils.ListContent;

import java.util.List;

public class AdapterSalesMan extends BaseAdapter {
   private Context context;
    private LayoutInflater mInflater;
    private List<ModelSalesMan> tModels;



    public AdapterSalesMan(Context context, List<ModelSalesMan> tModels) {
        this.context = context;
        this.tModels = tModels;
    }

    @Override
    public int getCount() {
        return tModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListContent holder;
        View v = convertView;
        if (v == null) {
            mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.row_sales_man, null);
            holder = new ListContent();
            holder.text = v.findViewById(R.id.tvEmpName);
            v.setTag(holder);
        } else {
            holder = (ListContent) v.getTag();
        }
        String strEmp = tModels.get(position).getUserId()+"\t\t\t\t\t"+tModels.get(position).getUserName();
        holder.text.setText(strEmp);

        return v;
    }
}

