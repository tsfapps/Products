package com.knotlink.salseman.fragment.dispatcher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.fragment.FragOrder;
import com.knotlink.salseman.fragment.FragReceipt;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragRouteActivityDispatcher extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_route_activity_dispatcher, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return view;
    }
    private void initFrag() {

        SetTitle.tbTitle("Dispatch Route Activity", getActivity());

    }

    @OnClick(R.id.iv_route_dispatch_delivery)
    public void routeOrderClicked(View view){
        getFragmentManager().beginTransaction().replace(R.id.container_main, new FragOrder()).addToBackStack(null).commit();
    }
    @OnClick(R.id.iv_route_dispatch_receipt)
    public void receiptClicked(View view){
        getFragmentManager().beginTransaction().replace(R.id.container_main, new FragReceipt()).addToBackStack(null).commit();
    }
}
