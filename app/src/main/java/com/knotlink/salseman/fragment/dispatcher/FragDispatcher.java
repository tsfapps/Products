package com.knotlink.salseman.fragment.dispatcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.fragment.FragCash;
import com.knotlink.salseman.fragment.FragDistance;
import com.knotlink.salseman.fragment.FragExpenses;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragDispatcher extends Fragment {
    private FragmentManager tFragmentManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dispatcher, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        tFragmentManager = getFragmentManager();
        return view;
    }

    private void initFrag(){
        SetTitle.tbTitle("Dashboard", getActivity());
    }


    @OnClick(R.id.iv_dispatch_distance)
    public void dispatchDisClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragDistance()).addToBackStack(null).commit();
    }
     @OnClick(R.id.iv_dispatch_route)
    public void dispatchRouteClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragRouteDispatcher()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_dispatch_cash_collection)
    public void dispatchCashClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragCash()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_dispatch_expenses)
    public void dispatchExpensesClicked(View view){
        tFragmentManager.beginTransaction().replace(R.id.container_main, new FragExpenses()).addToBackStack(null).commit();
    }
}
