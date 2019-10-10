package com.knotlink.salseman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.knotlink.salseman.R;
import com.knotlink.salseman.fragment.dispatcher.FragRouteActivityDispatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRouteDispatcher extends RecyclerView.Adapter<AdapterRouteDispatcher.RouteViewHolder> {

    private Context context;
    private FragmentManager tFragmentManager;

    public AdapterRouteDispatcher(Context context, FragmentManager tFragmentManager) {
        this.context = context;
        this.tFragmentManager = tFragmentManager;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_route_item_dispatcher, viewGroup,false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder routeViewHolder, int i) {

        routeViewHolder.btnRouteVisitDispatcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFragmentManager.beginTransaction().replace(R.id.container_main, new FragRouteActivityDispatcher()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btn_route_visit_dispatcher)
        protected Button btnRouteVisitDispatcher;
        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
