package com.knotlink.salseman.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.adapter.baseadapter.AdapterConvertCustomer;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragTaskProspectItemBinding;
import com.knotlink.salseman.fragment.dashboard.route.FragNewCustomer;
import com.knotlink.salseman.model.task.ModelTaskDecline;
import com.knotlink.salseman.model.task.ModelTaskProspect;
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

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTaskProspect extends RecyclerView.Adapter<AdapterTaskProspect.TaskViewHolder> {

    private List<ModelTaskProspect> tModelTaskProspect;
    private Context tContext;
    private Spinner spnProspectConvert;
    private EditText etTaskSchRem;
    private EditText etTaskCompleted;
    private AdapterConvertCustomer tAdapter;
    private String[] statusCustomer;
    private String strCustomerStatus;
    private TextView tvTaskSchDate;
    private TextView tvTaskCompletedDate;
    private FragmentManager tFragmentManager;
    private String strLat;
    private String strLong;
    private String strUserType;

    public AdapterTaskProspect(FragmentManager tFragmentManager, String strLat, String strLong, String strUserType) {
        this.tFragmentManager = tFragmentManager;
        this.strLat = strLat;
        this.strLong = strLong;
        this.strUserType = strUserType;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragTaskProspectItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_task_prospect_item, viewGroup, false);
        tContext = viewGroup.getContext();
        return new TaskViewHolder(tBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        final ModelTaskProspect tModel = tModelTaskProspect.get(i);
        taskViewHolder.tBinding.setProspectTask(tModel);
        statusCustomer = tContext.getResources().getStringArray(R.array.str_customer_status);
        tAdapter = new AdapterConvertCustomer(tContext, statusCustomer);


        switch (tModel.getDayStatus()) {
            case "1":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.red);
                taskViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_RED));
                break;
            case "2":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.tan_yellow);
                taskViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_TAN_YELLOW));
                break;
            case "3":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.pink);
                taskViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_PINK));
                break;
            case "4":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.cyan);
                taskViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_CYAN));
                break;
            case "5":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.green);
                taskViewHolder.btn_task_reschedule.setVisibility(View.GONE);
                taskViewHolder.btn_task_completed.setEnabled(false);
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_GREEN));
                taskViewHolder.btn_task_completed.setText("Completed");
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_GREEN));
                taskViewHolder.tvTaskDueDate.setText("Task Completion Date");
                break;
            case "6":
                taskViewHolder.viewTaskStatusColor.setBackgroundResource(R.color.darkPink);
                taskViewHolder.btn_task_reschedule.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                taskViewHolder.btn_task_completed.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                taskViewHolder.tvTaskDueDate.setTextColor(Color.parseColor(MyColor.COLOR_PINK_DARK));
                break;
        }

        taskViewHolder.btn_task_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_reshcedule);
                dialog.setTitle("Special Request");
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
                                        Toast.makeText(tContext, "Select the date after today...", Toast.LENGTH_SHORT).show();

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
                        Log.d(Constant.TAG, "Schedule Date : "+strNextMeetingDate);
                        Api api = ApiClients.getApiClients().create(Api.class);
                        Call<ModelTaskReschedule> call = api.assignedTaskReschedule(strTaskId, strRemarks,"0", strLat, strLong, strNextMeetingDate);
                        call.enqueue(new Callback<ModelTaskReschedule>() {
                            @Override
                            public void onResponse(Call<ModelTaskReschedule> call, Response<ModelTaskReschedule> response) {
                                ModelTaskReschedule tModelReschedule = response.body();
                                if (!tModelReschedule.getError()) {
                                    Toast.makeText(tContext, tModelReschedule.getMessage(), Toast.LENGTH_SHORT).show();

                                    CustomLog.d(Constant.TAG, "TaskReschedule Response: "+ response.message());

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
        taskViewHolder.btn_task_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(tContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_task_completed_prospect);
                dialog.setTitle("Reschedule Task");
                dialog.setCancelable(true);

                // set the custom dialog components - text, image and button
//                spnTaskReSchStatus =  dialog.findViewById(R.id.spn_taskRescheduleStatus);
                spnProspectConvert = dialog.findViewById(R.id.spnProspectConvert);
                LinearLayout  llCustomerConvertOption = dialog.findViewById(R.id.llCustomerConvertOption);
                spnProspectConvert.setAdapter(tAdapter);
                etTaskCompleted = dialog.findViewById(R.id.et_taskCompletedRemarks);
                tvTaskCompletedDate = dialog.findViewById(R.id.tv_taskCompletedDate);
                tvTaskCompletedDate.setText(DateUtils.getTodayDate());

                spnProspectConvert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Log.d(Constant.TAG, "You Clicked : " + position);
                            switch (position) {
                                case 0:
                                    strCustomerStatus = "";
                                    Toast.makeText(tContext, "Select the status", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    tFragmentManager.beginTransaction().replace(R.id.container_main, FragNewCustomer.newInstance(tModel, strUserType)).addToBackStack(null).commit();
                                    dialog.dismiss();
                                    break;
                                case 2:
                                    strCustomerStatus = statusCustomer[2];

                                    break;
                                default:
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                Button btnCompleted =  dialog.findViewById(R.id.btn_taskCompletedSubmit);
                btnCompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (strCustomerStatus.equalsIgnoreCase("Declined")) {
                            dialog.dismiss();
                            String strTaskId = tModel.getTaskId();
                            String strRemarks = etTaskCompleted.getText().toString().trim();
                            Api api = ApiClients.getApiClients().create(Api.class);
                            Call<ModelTaskDecline> call = api.completeTask(strTaskId, strRemarks, "0", strLat, strLong);
                            call.enqueue(new Callback<ModelTaskDecline>() {
                                @Override
                                public void onResponse(Call<ModelTaskDecline> call, Response<ModelTaskDecline> response) {
                                    ModelTaskDecline tModel = response.body();
                                    if (!tModel.getError()){
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
                        else {
                            Toast.makeText(tContext, "Select the status first.", Toast.LENGTH_SHORT).show();                        }

                    }
                });
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (tModelTaskProspect != null) {
            return tModelTaskProspect.size();
        } else {
            return 0;
        }
    }
    public void settModels(List<ModelTaskProspect> tModels){
        this.tModelTaskProspect = tModels;
        notifyDataSetChanged();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder{



        private View viewTaskStatusColor;
        private Button btn_task_reschedule;
        private Button btn_task_completed;
        private TextView tvTaskDueDate;
        private FragTaskProspectItemBinding tBinding;
        public TaskViewHolder(FragTaskProspectItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            viewTaskStatusColor = tBinding.llTaskStatusColor;
            btn_task_reschedule = tBinding.btnTaskReschedule;
            btn_task_completed = tBinding.btnTaskCompleted;
            tvTaskDueDate = tBinding.tvTaskDueDateLabel;
            ButterKnife.bind(this, itemView);
        }
    }
}
