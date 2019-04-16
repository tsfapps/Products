package com.knotlink.salseman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.utils.ListContent;

public class AdapterSpecialRequest extends BaseAdapter {
   private Context context;
    private LayoutInflater mInflater;
    private  String[] title;



    public AdapterSpecialRequest(Context context, String[] title) {
        this.context = context;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.length;
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
            v = mInflater.inflate(R.layout.row_special_req_item, null);
            holder = new ListContent();
            holder.text = (TextView) v.findViewById(R.id.tv_spclReq_item);

            v.setTag(holder);
        } else {
            holder = (ListContent) v.getTag();
        }

        holder.text.setText(title[position]);

        return v;
    }
}

