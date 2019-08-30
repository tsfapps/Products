package com.knotlink.salseman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.fragment.FragDistance;
import com.knotlink.salseman.fragment.FragVehicleList;
import com.knotlink.salseman.model.ModelVehicleList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterVehicleList extends RecyclerView.Adapter<AdapterVehicleList.VehicleViewHolder> {

    private List<ModelVehicleList> tLists;
    private Context tContext;
    private FragmentManager tFragmentManager;
    private String strUserType;

    public AdapterVehicleList(List<ModelVehicleList> tLists, Context tContext, FragmentManager tFragmentManager, String strUserType) {
        this.tLists = tLists;
        this.tContext = tContext;
        this.tFragmentManager = tFragmentManager;
        this.strUserType = strUserType;

    }

    public AdapterVehicleList(List<ModelVehicleList> tLists, Context tContext) {
        this.tLists = tLists;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_distance_vecicle_item, viewGroup, false);
        return new VehicleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder vehicleViewHolder, final int i) {
        ModelVehicleList tModel = tLists.get(i);
        vehicleViewHolder.tv_vehicleList_vehicleBrand.setText(tModel.getBrand());
        vehicleViewHolder.tv_vehicleList_vehicleNumber.setText(tModel.getVehicleNo());
        vehicleViewHolder.llVehicleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragDistance.newInstance(tLists, i, strUserType)).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tLists.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_vehicleList)
        protected LinearLayout llVehicleList;
        @BindView(R.id.tv_vehicleList_vehicleNumber)
        protected TextView tv_vehicleList_vehicleNumber;
        @BindView(R.id.tv_vehicleList_vehicleBrand)
        protected TextView tv_vehicleList_vehicleBrand;
       public VehicleViewHolder(@NonNull View itemView) {
           super(itemView);
           ButterKnife.bind(this, itemView);
       }
   }

}
