package com.knotlink.salseman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterCity;
import com.knotlink.salseman.adapter.baseadapter.AdapterRouteSelect;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelRoute;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragNewCustomer extends Fragment {
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    private String strUserId;

    @BindView(R.id.spnNewCustomerRoute)
    protected Spinner spnNewCustomerRoute;
    @BindView(R.id.spnNewCustomerCity)
    protected Spinner spnNewCustomerCity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_customer, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        SetTitle.tbTitle("New Customer Registration", getActivity());
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        strUserId = tSharedPrefManager.getUserId();

        String[] strCity = getResources().getStringArray(R.array.city_list);
        AdapterCity tAdapterCity = new AdapterCity(tContext, strCity);
        spnNewCustomerCity.setAdapter(tAdapterCity);
        callApiRoute();
    }


    private void callApiRoute(){

        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelRoute>> call = api.userRouteList (strUserId);
        call.enqueue(new Callback<List<ModelRoute>>() {
            @Override
            public void onResponse(Call<List<ModelRoute>> call, Response<List<ModelRoute>> response) {
                List<ModelRoute> tModels = response.body();
                AdapterRouteSelect tAdapterRoute = new AdapterRouteSelect(tContext, tModels);
                spnNewCustomerRoute.setAdapter(tAdapterRoute);

            }

            @Override
            public void onFailure(Call<List<ModelRoute>> call, Throwable t) {

            }
        });

    }
}
