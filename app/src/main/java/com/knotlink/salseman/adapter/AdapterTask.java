package com.knotlink.salseman.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.model.ModelTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.TaskViewHolder> {

    private List<ModelTask> tModelTask;
    private Context tContext;
    public AdapterTask(List<ModelTask> tModelTask, Context tContext) {
        this.tModelTask = tModelTask;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        ModelTask tModel = tModelTask.get(i);
        taskViewHolder.tvTaskAssignedDae.setText(tModel.getTaskAssignDate());
        taskViewHolder.tvTaskDueDate.setText(tModel.getTaskDueDate());
        taskViewHolder.tvTaskCompletionDate.setText(tModel.getTaskCompletionDate());
        taskViewHolder.tvTaskTime.setText(tModel.getTaskTime());
        taskViewHolder.tvTaskContactName.setText(tModel.getCustomerName());
        taskViewHolder.tvTaskContactNumber.setText(tModel.getCustomerContactNo());
        taskViewHolder.tvTaskRemarks.setText(tModel.getRemarks());
        taskViewHolder.tvTaskRemarksDate.setText(tModel.getRemarksDate());
        taskViewHolder.tvTaskStatus.setText(tModel.getStatus());
        taskViewHolder.tvTaskUserRemarks.setText(tModel.getUserRemarks());
        taskViewHolder.tvTaskAddress.setText(tModel.getCustomerAddress());
    }

    @Override
    public int getItemCount() {
        return tModelTask.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_task_assignedDate)
        protected TextView tvTaskAssignedDae;
        @BindView(R.id.tv_task_dueDate)
        protected TextView tvTaskDueDate;
        @BindView(R.id.tv_task_completionDate)
        protected TextView tvTaskCompletionDate;
        @BindView(R.id.tv_task_time)
        protected TextView tvTaskTime;
        @BindView(R.id.tv_task_contactName)
        protected TextView tvTaskContactName;
        @BindView(R.id.tv_task_contactNumber)
        protected TextView tvTaskContactNumber;
        @BindView(R.id.tv_task_remarks)
        protected TextView tvTaskRemarks;
        @BindView(R.id.tv_task_remarksDate)
        protected TextView tvTaskRemarksDate;
        @BindView(R.id.tv_task_status)
        protected TextView tvTaskStatus;
        @BindView(R.id.et_task_userRemarks)
        protected EditText tvTaskUserRemarks;
        @BindView(R.id.tv_task_address)
        protected TextView tvTaskAddress;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
