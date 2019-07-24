package com.knotlink.salseman.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterTaskReSchedule;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.task.ModelTask;
import com.knotlink.salseman.model.task.ModelTaskReschedule;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.TaskViewHolder> {

    private List<ModelTask> tModelTask;
    private Context tContext;
    private Activity tActivity;
    private Spinner spnTaskReSchStatus;
    private EditText etTaskSchRem;
    private EditText etTaskCompleted;
    private AdapterTaskReSchedule tAdapter;
    private String[] taskStatus;
    private String spnTaskStatusItem;
    private TextView tvTaskSchDate;
    private TextView tvTaskCompletedDate;
    private int modelIndex;

    public AdapterTask(List<ModelTask> tModelTask, Context tContext, Activity tActivity) {
        this.tModelTask = tModelTask;
        this.tContext = tContext;
        this.tActivity = tActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        final ModelTask tModel = tModelTask.get(i);


        modelIndex = taskViewHolder.getAdapterPosition();
        taskStatus = tContext.getResources().getStringArray(R.array.task_status);
        tAdapter = new AdapterTaskReSchedule(tContext, taskStatus);

        taskViewHolder.tv_task_type.setText(tModel.getTaskType());
        taskViewHolder.tvTaskAssignedDae.setText(tModel.getTaskAssignDate());
        taskViewHolder.tvTaskDueDate.setText(tModel.getTaskDueDate());
        String strTodayDate = DateUtils.getTodayDate();
        String strDueDate = tModel.getTaskDueDate();

        long todayDate = DateUtils.dateToMiliSeconds(strTodayDate);
        long dueDate = DateUtils.dateToMiliSeconds(strDueDate);


        if (todayDate < dueDate){
            taskViewHolder.ll_taskStatusColor.setBackgroundResource(R.color.tan_yellow);
            taskViewHolder.btn_task_reschedule.setBackgroundResource(R.color.tan_yellow);
            taskViewHolder.btn_task_completed.setBackgroundResource(R.color.tan_yellow);
            taskViewHolder.tvTaskDueDate.setBackground(ContextCompat.getDrawable(tContext, R.drawable.bg_et_red));
//            layout.setBackground(ContextCompat.getDrawable(context, R.drawable.ready));
        }
        else if (todayDate == dueDate && !tModel.getStatus().equals("Completed")){
            taskViewHolder.ll_taskStatusColor.setBackgroundResource(R.color.red);
            taskViewHolder.btn_task_reschedule.setBackgroundResource(R.color.red);
            taskViewHolder.btn_task_completed.setBackgroundResource(R.color.red);
            taskViewHolder.tvTaskDueDate.setBackground(ContextCompat.getDrawable(tContext, R.drawable.bg_et_yellow));

        }
        else if (todayDate== dueDate && tModel.getStatus().equals("Completed") ){
            taskViewHolder.ll_taskStatusColor.setBackgroundResource(R.color.green);
            taskViewHolder.btn_task_reschedule.setVisibility(View.GONE);
            taskViewHolder.btn_task_completed.setEnabled(false);
            taskViewHolder.btn_task_completed.setBackgroundResource(R.color.green);
            taskViewHolder.btn_task_completed.setText("Completed");
            taskViewHolder.tvTaskDueDate.setBackground(ContextCompat.getDrawable(tContext, R.drawable.bg_et_green));
            taskViewHolder.tv_task_dueDateLabel.setText("Task Completion Date");

        }

        taskViewHolder.tvTaskContactName.setText(tModel.getCustomerName());
        taskViewHolder.tvTaskContactNumber.setText(tModel.getCustomerContactNo());
        taskViewHolder.tvTaskRemarks.setText(tModel.getRemarks());
        taskViewHolder.tvTaskStatus.setText(tModel.getStatus());
        taskViewHolder.tvTaskAddress.setText(tModel.getCustomerAddress());
        taskViewHolder.btn_task_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_reshcedule);
                dialog.setTitle("Special Request");
                dialog.setCancelable(true);

                // set the custom dialog components - text, image and button
//                spnTaskReSchStatus =  dialog.findViewById(R.id.spn_taskRescheduleStatus);
                etTaskSchRem =  dialog.findViewById(R.id.et_taskRescheduleRemarks);
               tvTaskSchDate =  dialog.findViewById(R.id.tv_taskRescheduleDate);
                tvTaskSchDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                String strCurrentDate = DateUtils.getTodayDate();
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
                                try {
                                    Date myDate = sdf.parse(strCurrentDate);
                                    // long millis = myDate.getTime();
                                    String strMyDate = sdf.format(myCalendar.getTime());
                                    Date selDate = sdf.parse(strMyDate);
                                    if (selDate.compareTo(myDate)>0) {
                                        tvTaskSchDate.setText(strMyDate);
                                    }
                                    else {
                                        CustomToast.toastTop(tActivity, "Select the date after today...");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        new DatePickerDialog(tContext, date,
                                myCalendar.get(Calendar.YEAR),
                                myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });


                Button button =  dialog.findViewById(R.id.btn_taskRescheduleSubmit);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String strTaskId = tModel.getTaskId();
                        String strRemarks = etTaskSchRem.getText().toString().trim();
                        String strNextMeetingDate = tvTaskSchDate.getText().toString().trim();
                        Api api = ApiClients.getApiClients().create(Api.class);
                        Call<ModelTaskReschedule> call = api.assignedTaskReschedule(strTaskId, strRemarks, strNextMeetingDate);
                        call.enqueue(new Callback<ModelTaskReschedule>() {
                            @Override
                            public void onResponse(Call<ModelTaskReschedule> call, Response<ModelTaskReschedule> response) {
                                ModelTaskReschedule tModelReschedule = response.body();
                                if (!tModelReschedule.getError()) {
                                    CustomToast.toastTop(tActivity, tModelReschedule.getMessage());
                                    CustomLog.d(Constant.TAG, "TaskReschedule Response: "+ response.message());

                                }else {
                                    CustomToast.toastTop(tActivity, tModelReschedule.getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelTaskReschedule> call, Throwable t) {
                                CustomLog.d(Constant.TAG, "TaskReschedule not responding: "+ t);

                            }
                        });


                    }
                });
                dialog.show();
            }
        });
        taskViewHolder.btn_task_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_completed);
                dialog.setTitle("Special Request");
                dialog.setCancelable(true);

                // set the custom dialog components - text, image and button
//                spnTaskReSchStatus =  dialog.findViewById(R.id.spn_taskRescheduleStatus);
                etTaskCompleted =  dialog.findViewById(R.id.et_taskCompletedRemarks);
                tvTaskCompletedDate =  dialog.findViewById(R.id.tv_taskCompletedDate);
                tvTaskCompletedDate.setText(DateUtils.getTodayDate());

                Button btnCompleted =  dialog.findViewById(R.id.btn_taskCompletedSubmit);

                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String strTaskId = tModel.getTaskId();
                        String strRemarks = etTaskCompleted.getText().toString().trim();
                        Api api = ApiClients.getApiClients().create(Api.class);

                        CustomToast.toastTop(tActivity, "Task completed successfully...");

                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return tModelTask.size();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_taskStatusColor)
        protected LinearLayout ll_taskStatusColor;
        @BindView(R.id.tv_task_type)
        protected TextView tv_task_type;
        @BindView(R.id.tv_task_assignedDate)
        protected TextView tvTaskAssignedDae;
        @BindView(R.id.tv_task_dueDate)
        protected TextView tvTaskDueDate;
        @BindView(R.id.tv_task_dueDateLabel)
        protected TextView tv_task_dueDateLabel;
        @BindView(R.id.tv_task_contactName)
        protected TextView tvTaskContactName;
        @BindView(R.id.tv_task_contactNumber)
        protected TextView tvTaskContactNumber;
        @BindView(R.id.tv_task_remarks)
        protected TextView tvTaskRemarks;

        @BindView(R.id.tv_task_status)
        protected TextView tvTaskStatus;

        @BindView(R.id.tv_task_address)
        protected TextView tvTaskAddress;
        @BindView(R.id.btn_task_reschedule)
        protected Button btn_task_reschedule;
        @BindView(R.id.btn_task_completed)
        protected Button btn_task_completed;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
