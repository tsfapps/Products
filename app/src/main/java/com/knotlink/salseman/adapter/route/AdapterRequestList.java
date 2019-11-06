package com.knotlink.salseman.adapter.route;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragRequestListBinding;
import com.knotlink.salseman.databinding.FragRequestListItemBinding;
import com.knotlink.salseman.databinding.FragTaskItemBinding;
import com.knotlink.salseman.model.dash.route.ModelRequestList;
import com.knotlink.salseman.model.task.ModelTaskCustomer;
import com.knotlink.salseman.model.task.ModelTaskDecline;
import com.knotlink.salseman.model.task.ModelTaskReschedule;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.MyColor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRequestList extends RecyclerView.Adapter<AdapterRequestList.RequestViewHolder> {

    private List<ModelRequestList> tModelRequestList;
    private Context tContext;
    private EditText etTaskSchRem;
    private EditText etTaskCompleted;
    private TextView tvTaskSchDate;
    private TextView tvTaskCompletedDate;
    private String strLat;
    private String strLong;

    public AdapterRequestList(String strLat, String strLong) {
        this.strLat = strLat;
        this.strLong = strLong;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragRequestListItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_request_list_item, viewGroup, false);
        tContext = viewGroup.getContext();
        return new RequestViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder requestViewHolder, int i) {
        final ModelRequestList tModel = tModelRequestList.get(i);
        requestViewHolder.tBinding.setRequestList(tModel);

        switch (Objects.requireNonNull(tModel.getTaskStatus())) {
            case "1":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.red);
                requestViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                break;
            case "2":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.tan_yellow);
                requestViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                break;
            case "3":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.pink);
                requestViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                break;
            case "4":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.cyan);
                requestViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                break;
            case "5":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.green);
                requestViewHolder.btn_task_reschedule.setVisibility(View.GONE);
                requestViewHolder.btn_task_completed.setEnabled(false);
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_GREEN));
                requestViewHolder.btn_task_completed.setText("Completed");
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_GREEN));
                requestViewHolder.tvTaskDueDate.setText("Task Completion Date");
                break;
            case "6":
                requestViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.darkPink);
                requestViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                requestViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                requestViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                break;
        }

        requestViewHolder.btn_task_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_reshcedule);
                dialog.setTitle("Reschedule Task");
                dialog.setCancelable(true);
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
                                        Toast.makeText(tContext, "Chose right date...", Toast.LENGTH_SHORT).show();
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
                        String strNextMeetingDate = DateUtils.convertDdToYyyy(tvTaskSchDate.getText().toString().trim());
                        Api api = ApiClients.getApiClients().create(Api.class);
                        Call<ModelTaskReschedule> call = api.assignedTaskReschedule(strTaskId, strRemarks,"1", strLat, strLong, strNextMeetingDate);
                        call.enqueue(new Callback<ModelTaskReschedule>() {
                            @Override
                            public void onResponse(Call<ModelTaskReschedule> call, Response<ModelTaskReschedule> response) {
                                ModelTaskReschedule tModelReschedule = response.body();
                                if (!tModelReschedule.getError()) {
                                    Toast.makeText(tContext, tModelReschedule.getMessage(), Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(tContext, tModelReschedule.getMessage(), Toast.LENGTH_SHORT).show();
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
        requestViewHolder.btn_task_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_completed);
                dialog.setTitle("Special Request");
                dialog.setCancelable(true);

                etTaskCompleted = dialog.findViewById(R.id.et_taskCompletedRemarks);
                tvTaskCompletedDate = dialog.findViewById(R.id.tv_taskCompletedDate);
                tvTaskCompletedDate.setText(DateUtils.getTodayDate());


                Button btnCompleted =  dialog.findViewById(R.id.btn_taskCompletedSubmit);
                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            String strTaskId = tModel.getTaskId();
                            String strRemarks = etTaskCompleted.getText().toString().trim();
                            Api api = ApiClients.getApiClients().create(Api.class);
                            Call<ModelTaskDecline> call = api.completeTask(strTaskId, strRemarks, "1", strLat, strLong);
                            call.enqueue(new Callback<ModelTaskDecline>() {
                                @Override
                                public void onResponse(Call<ModelTaskDecline> call, Response<ModelTaskDecline> response) {
                                    ModelTaskDecline tModel = response.body();

                                    if (!tModel.getError()){
                                        dialog.dismiss();
                                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ModelTaskDecline> call, Throwable t) {

                                }
                            });



                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (tModelRequestList != null) {
            return tModelRequestList.size();
        } else {
            return 0;
        }
    }

    public void settModels(List<ModelRequestList> tModels){
        this.tModelRequestList = tModels;
        notifyDataSetChanged();
    }
    public class RequestViewHolder extends RecyclerView.ViewHolder{
        private View viewTaskStatusColor;
        private Button btn_task_reschedule;
        private Button btn_task_completed;
        private TextView tvTaskDueDate;
        private FragRequestListItemBinding tBinding;
        public RequestViewHolder(FragRequestListItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            viewTaskStatusColor = tBinding.llRequestStatusColor;
            btn_task_reschedule = tBinding.btnRequestReschedule;
            btn_task_completed = tBinding.btnRequestCompleted;
            tvTaskDueDate = tBinding.tvRequestDueDateLabel;
            ButterKnife.bind(this, itemView);
        }
    }
}
