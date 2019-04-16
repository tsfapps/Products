package com.knotlink.salseman.fragment.dispatcher;

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
import com.knotlink.salseman.adapter.AdapterRoute;
import com.knotlink.salseman.adapter.AdapterRouteDispatcher;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragRouteDispatcher extends Fragment {
    @BindView(R.id.rv_route_dispatcher)
    protected RecyclerView rvRouteDispatcher;
    private Context tContext;
    private FragmentManager tFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_route_dispatcher, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        SetTitle.tbTitle("Dispatch Shop List", getActivity());
        initRvRoute();
    }
    private void initRvRoute(){
        RecyclerView.LayoutManager tManager = new LinearLayoutManager(tContext);
        rvRouteDispatcher.setLayoutManager(tManager);
        AdapterRouteDispatcher tAdapter = new AdapterRouteDispatcher(tContext,tFragmentManager);
        rvRouteDispatcher.setAdapter(tAdapter);
    }
}
