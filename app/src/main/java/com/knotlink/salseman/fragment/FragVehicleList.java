package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterVehicleList;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelVehicleList;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragVehicleList extends Fragment {

    private Context tContext;
    private List<ModelVehicleList> tModels;
    private AdapterVehicleList tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    private FragmentManager tFragmentManager;
    @BindView(R.id.rv_vehicleList)
    protected RecyclerView rvVehicleList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_distance_vecicle, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        tLayoutManager = new LinearLayoutManager(tContext);
        rvVehicleList.setLayoutManager(tLayoutManager);
        callApi();
    }
    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelVehicleList>> call = api.vehicleList();
        call.enqueue(new Callback<List<ModelVehicleList>>() {
            @Override
            public void onResponse(Call<List<ModelVehicleList>> call, Response<List<ModelVehicleList>> response) {
                tModels = response.body();
                tAdapter = new AdapterVehicleList(tModels, tContext, tFragmentManager);
                rvVehicleList.setAdapter(tAdapter);
                CustomLog.d(Constant.TAG, "Vehicle List Response : "+response.message());
            }

            @Override
            public void onFailure(Call<List<ModelVehicleList>> call, Throwable t) {

                CustomLog.d(Constant.TAG, "Vehicle List Not Responding : "+t);
            }
        });

    }
}
