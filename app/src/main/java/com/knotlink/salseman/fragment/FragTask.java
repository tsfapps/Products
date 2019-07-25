package com.knotlink.salseman.fragment;

import android.app.Activity;
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
import com.knotlink.salseman.adapter.AdapterTask;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.task.ModelTask;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.SetTitle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragTask extends Fragment {
    private RecyclerView.LayoutManager tLayoutManager;
    private AdapterTask tAdapterTask;
    private SharedPrefManager tSharedPrefManager;
    private FragmentManager tFragmentManager;
    private Context tContext;
    private Activity tActivity;
    private List<ModelTask> tModelTask;
    @BindView(R.id.rvTask)
    protected RecyclerView rvTask;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_task, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag(){
        tContext = getContext();
        tActivity = getActivity();
        tSharedPrefManager = new SharedPrefManager(tContext);
        tFragmentManager = getFragmentManager();
        SetTitle.tbTitle("Task Assigned", getActivity());
        tLayoutManager = new LinearLayoutManager(tContext);
        rvTask.setLayoutManager(tLayoutManager);
        callApiColdCall();
    }
    private  void callApiColdCall(){
        String strUserId = tSharedPrefManager.getUserId();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<List<ModelTask>> call = api.assignedTask(strUserId);
        call.enqueue(new Callback<List<ModelTask>>() {
            @Override
            public void onResponse(Call<List<ModelTask>> call, Response<List<ModelTask>> response) {
                tModelTask =response.body();
                tAdapterTask = new AdapterTask(tModelTask, tContext, tActivity, tFragmentManager);
                rvTask.setAdapter(tAdapterTask);
            }
            @Override
            public void onFailure(Call<List<ModelTask>> call, Throwable t) {
                CustomLog.d(Constant.TAG, " Task Not Responding : "+t);
            }
        });
    }
}
