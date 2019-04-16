package com.knotlink.salseman.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.TaskViewHolder> {

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
