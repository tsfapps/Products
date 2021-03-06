package com.knotlink.salseman.adapter.spinner.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.order.ModelBrand;
import com.knotlink.salseman.model.dash.route.order.ModelColorArticle;
import com.knotlink.salseman.utils.ListContent;

import java.util.List;

public class AdapterColor extends BaseAdapter {

    private Context tContext;
    private List<ModelColorArticle> tModels;

    public AdapterColor(Context tContext, List<ModelColorArticle> tModels) {
        this.tContext = tContext;
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
        final ListContent tListContent;
        View view = convertView;
        if (view == null){
            LayoutInflater tInflater = (LayoutInflater) tContext.getSystemService(tContext.LAYOUT_INFLATER_SERVICE);
            view = tInflater.inflate(R.layout.row_sales_man, null);
            tListContent = new ListContent();
            tListContent.text = view.findViewById(R.id.tvEmpName);
            view.setTag(tListContent);
        }
        else {
            tListContent = (ListContent)view.getTag();
        }
        tListContent.text.setText(tModels.get(position).getColor());
        return view;
    }
}
