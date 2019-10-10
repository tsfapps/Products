package com.knotlink.salseman.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.dash.route.ModelRoute;
import com.knotlink.salseman.utils.ListContent;

import java.util.List;

public class AdapterRouteSelect extends BaseAdapter {
   private Context context;
    private LayoutInflater mInflater;
    private List<ModelRoute> tModels;



    public AdapterRouteSelect(Context context, List<ModelRoute> tModels) {
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
            v = mInflater.inflate(R.layout.row_task_shedule_item, null);
            holder = new ListContent();
            holder.text = v.findViewById(R.id.tvTaskSch);

            v.setTag(holder);
        } else {
            holder = (ListContent) v.getTag();
        }
        holder.text.setText(tModels.get(position).getRouteName());

        return v;
    }
}

