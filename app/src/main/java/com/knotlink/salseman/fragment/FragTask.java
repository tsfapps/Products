package com.knotlink.salseman.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.AdapterTaskCustomer;
import com.knotlink.salseman.adapter.AdapterTaskProspect;
import com.knotlink.salseman.adapter.baseadapter.AdapterSalesMan;
import com.knotlink.salseman.adapter.spinner.AdapterAreaSalesMan;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragTaskBinding;
import com.knotlink.salseman.model.ModelAsmList;
import com.knotlink.salseman.model.ModelSalesMan;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.model.task.ModelTaskProspect;
import com.knotlink.salseman.services.GPSTracker;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomDialog;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;
import com.knotlink.salseman.viewModel.ViewModelTaskCustomer;
import com.knotlink.salseman.viewModel.ViewModelTaskProspect;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragTask extends Fragment implements AdapterView.OnItemSelectedListener {


    private AdapterTaskCustomer tAdapterTaskCustomer;
    private AdapterTaskProspect  tAdapterTaskProspect;
    private ViewModelTaskCustomer tViewModelCustomer;
    private ViewModelTaskProspect tViewModelProspect;
    private SharedPrefManager tSharedPrefManager;
    private Context tContext;

    private List<ModelSalesMan> tModelsSalesMan;
    private List<ModelAsmList> tModelAsmList;
    private AdapterSalesMan tAdapterSalesMan;
    private AdapterAreaSalesMan tAdapterAreaSalesMan;
    private FragTaskBinding tBinding;

    @BindView(R.id.rlSpnTaskSalesMan)
    protected RelativeLayout rlSpnTaskSalesMan;
    @BindView(R.id.spnTaskSalesMan)
    protected Spinner spnTaskSalesMan;
    @BindView(R.id.pbSpnTaskSales)
    protected ProgressBar pbSpnTaskSales;
    @BindView(R.id.rlSpnTaskAreaSalesMan)
    protected RelativeLayout rlSpnTaskAreaSalesMan;

    @BindView(R.id.spnTaskAreaSalesMan)
    protected Spinner spnTaskAreaSalesMan;
    @BindView(R.id.pbSpnTaskAreaSales)
    protected ProgressBar pbSpnTaskAreaSales;
    @BindView(R.id.tvTaskCustomerLabel)
    protected TextView tvTaskCustomerLabel;
    @BindView(R.id.tvTaskProspectLabel)
    protected TextView tvTaskProspectLabel;

    private RecyclerView rvTask;
    private RecyclerView rvTaskProspect;

    private ProgressBar pbFragTask;

    private String strUserId;
    private String strUserType;
    private String strSelectedUserId;
    public static FragTask newInstance(String strUserType) {

        FragTask fragment = new FragTask();
        fragment.strUserType = strUserType;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tBinding = DataBindingUtil.inflate(inflater, R.layout.frag_task, container, false);
        View view = tBinding.getRoot();
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tViewModelCustomer = ViewModelProviders.of(this).get(ViewModelTaskCustomer.class);
        tViewModelProspect = ViewModelProviders.of(this).get(ViewModelTaskProspect.class);

        FragmentManager tFragmentManager = getFragmentManager();

        //GPS Calling
        GPSTracker tGpsTracker = new GPSTracker(tContext);
        String strLat = String.valueOf(tGpsTracker.latitude);
        String strLong = String.valueOf(tGpsTracker.longitude);

        pbFragTask = tBinding.pbFragTask;

        //Adapter Task Customer Calling
        tAdapterTaskCustomer = new AdapterTaskCustomer(strLat, strLong);
        rvTask = tBinding.rvTask;
        rvTask.setLayoutManager(new LinearLayoutManager(tContext));
        rvTask.setItemAnimator(new DefaultItemAnimator());
        rvTask.setNestedScrollingEnabled(false);
        rvTask.setAdapter(tAdapterTaskCustomer);

        //Adapter Task Prospect Calling
        tAdapterTaskProspect = new AdapterTaskProspect(tFragmentManager, strLat, strLong, strUserType);
        rvTaskProspect = tBinding.rvTaskProspect;
        rvTaskProspect.setLayoutManager(new LinearLayoutManager(tContext));
        rvTaskProspect.setItemAnimator(new DefaultItemAnimator());
        rvTaskProspect.setNestedScrollingEnabled(false);
        rvTaskProspect.setAdapter(tAdapterTaskProspect);

        if (strUserType.equalsIgnoreCase("0")){
            strUserId = strSelectedUserId;

        }else if(strUserType.equalsIgnoreCase("1")){
            strUserId = tSharedPrefManager.getUserId();
            getAllTaskCustomer(strUserId);
            getAllTaskProspect(strUserId);
        }else {
            strUserId = tSharedPrefManager.getUserId();
        }
        SetTitle.tbTitle("Task Assigned", getActivity());
        hideShowContent();
    }
    private void hideShowContent(){
        if (strUserType.equalsIgnoreCase("1")){
            rlSpnTaskSalesMan.setVisibility(View.GONE);
            rlSpnTaskAreaSalesMan.setVisibility(View.GONE);

        }
        else if (strUserType.equalsIgnoreCase("2")){
            rlSpnTaskSalesMan.setVisibility(View.GONE);
            rlSpnTaskAreaSalesMan.setVisibility(View.GONE);


        }
        else if (strUserType.equalsIgnoreCase("3")){
            rlSpnTaskSalesMan.setVisibility(View.VISIBLE);
            rlSpnTaskAreaSalesMan.setVisibility(View.GONE );
            spnTaskSalesMan.setOnItemSelectedListener(this);
            callApiSalesMan(strUserId);

        }
        else if (strUserType.equalsIgnoreCase("0")){
            rlSpnTaskSalesMan.setVisibility(View.VISIBLE);
            rlSpnTaskAreaSalesMan.setVisibility(View.VISIBLE);
            spnTaskAreaSalesMan.setOnItemSelectedListener(this);
            spnTaskSalesMan.setOnItemSelectedListener(this);
            callApiAreaSalesMan();
        }
    }
    private void callApiSalesMan(String StrUserId){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelSalesMan>> call = api.salesAsmManList(StrUserId);
        call.enqueue(new Callback<List<ModelSalesMan>>() {
            @Override
            public void onResponse(Call<List<ModelSalesMan>> call, Response<List<ModelSalesMan>> response) {
                tModelsSalesMan = response.body();
                pbSpnTaskSales.setVisibility(View.GONE);
                tAdapterSalesMan = new AdapterSalesMan(tContext, tModelsSalesMan);
                spnTaskSalesMan.setAdapter(tAdapterSalesMan);
            }
            @Override
            public void onFailure(Call<List<ModelSalesMan>> call, Throwable t) {

            }
        });
    }
    private void callApiAreaSalesMan(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelAsmList>> call = api.asmList(strUserId);
        call.enqueue(new Callback<List<ModelAsmList>>() {
            @Override
            public void onResponse(Call<List<ModelAsmList>> call, Response<List<ModelAsmList>> response) {
                tModelAsmList = response.body();
                pbSpnTaskAreaSales.setVisibility(View.GONE);
                tAdapterAreaSalesMan = new AdapterAreaSalesMan(tContext, tModelAsmList);
                spnTaskAreaSalesMan.setAdapter(tAdapterAreaSalesMan);
            }
            @Override
            public void onFailure(Call<List<ModelAsmList>> call, Throwable t) {
                Log.d(Constant.TAG, "ASM List Failure : "+t);

            }
        });
    }


    private void getAllTaskCustomer(String strUserId){
        tViewModelCustomer.getAllTaskCustomer(strUserId).observe(this, new Observer<List<ModelTaskCustomer>>() {
            @Override
            public void onChanged(@Nullable List<ModelTaskCustomer> tModelTaskCustomer) {
                pbFragTask.setVisibility(View.GONE);
                if (tModelTaskCustomer.size()>0) {
                    tAdapterTaskCustomer.settModels(tModelTaskCustomer);
                    tvTaskCustomerLabel.setVisibility(View.VISIBLE);
                    rvTask.setVisibility(View.VISIBLE);
                }else {
                    tvTaskCustomerLabel.setVisibility(View.GONE);
                    rvTask.setVisibility(View.GONE);

                }
            }
        });
    }

    private void getAllTaskProspect(String strUserId){
        tViewModelProspect.getTaskProspect(strUserId).observe(this, new Observer<List<ModelTaskProspect>>() {
            @Override
            public void onChanged(@Nullable List<ModelTaskProspect> modelTaskProspects) {
                pbFragTask.setVisibility(View.GONE);
                if (modelTaskProspects.size()>0){
                    tAdapterTaskProspect.settModels(modelTaskProspects);
                    tvTaskProspectLabel.setVisibility(View.VISIBLE);
                    rvTaskProspect.setVisibility(View.VISIBLE);

                }else {
                    tvTaskProspectLabel.setVisibility(View.GONE);
                    rvTaskProspect.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnTaskAreaSalesMan:
                String strAsmId = tModelAsmList.get(position).getUserId();
                callApiSalesMan(strAsmId);
                break;
            case R.id.spnTaskSalesMan:
                strSelectedUserId = tModelsSalesMan.get(position).getUserId();
                getAllTaskCustomer(strSelectedUserId);
                getAllTaskProspect(strSelectedUserId);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
