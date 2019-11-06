package com.knotlink.salseman.adapter.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.databinding.FragNoticeReturnItemBinding;
import com.knotlink.salseman.model.notice.ModelNoticeReturn;
import com.knotlink.salseman.model.notice.ModelNoticeReturnReject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterNoticeReturn extends RecyclerView.Adapter<AdapterNoticeReturn.ReturnViewHolder> {

    private List<ModelNoticeReturn> tModels;
    private Context tContext;

    @NonNull
    @Override
    public ReturnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragNoticeReturnItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_notice_return_item, viewGroup, false);
        tContext = viewGroup.getContext();
        return new ReturnViewHolder(tBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull final ReturnViewHolder returnViewHolder, int i) {
        final ModelNoticeReturn tModel = tModels.get(i);
        returnViewHolder.tBinding.setNoticeReturn(tModel);
        if (tModel.getStatus().equalsIgnoreCase("0")){
            returnViewHolder.tvStatusReturn.setText("Reject");
            returnViewHolder.tvStatusReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    { AlertDialog.Builder alert = new AlertDialog.Builder(tContext);
                        final EditText edittext = new EditText(tContext);
                        edittext.setBackgroundResource(R.drawable.bg_et);
                        //  edittext.setMinLines(4);
                        edittext.setSingleLine();
                        FrameLayout container = new FrameLayout(tContext);
                        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                        params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                        params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                        params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.dimen_8dp);
                        edittext.setLayoutParams(params);
                        container.addView(edittext);
                        alert.setTitle("Mention the reason");
                        alert.setView(container);
                        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String strRemark = edittext.getText().toString();
                                String strId = tModel.getId();

                                if (!strRemark.equalsIgnoreCase("")) {
                                    Api api = ApiClients.getApiClients().create(Api.class);
                                    Call<ModelNoticeReturnReject> call = api.noticeReturnReject(strId,strRemark);
                                    call.enqueue(new Callback<ModelNoticeReturnReject>() {
                                        @Override
                                        public void onResponse(Call<ModelNoticeReturnReject> call, Response<ModelNoticeReturnReject> response) {
                                            ModelNoticeReturnReject tModel = response.body();
                                            if (!tModel.getError()) {
                                                returnViewHolder.tvStatusReturn.setText("Accepted");
                                                returnViewHolder.tvStatusReturn.setEnabled(false);
                                                returnViewHolder.tvStatusReturn.setBackgroundResource(R.drawable.bg_btn_disabled);
                                                Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(tContext, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ModelNoticeReturnReject> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    Toast.makeText(tContext, "Write the reason of rejection", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // what ever you want to do with No option.
                            }
                        });

                        alert.show();
                    }
                }
            });
        }else {
            returnViewHolder.tvStatusReturn.setText("Accepted");
            returnViewHolder.tvStatusReturn.setEnabled(false);
            returnViewHolder.tvStatusReturn.setBackgroundResource(R.drawable.bg_btn_disabled);
        }
    }
    @Override
    public int getItemCount() {

        if (tModels != null) {
            return tModels.size();

        } else {
            return 0;
        }
    }
    public void settModels(List<ModelNoticeReturn> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class ReturnViewHolder extends RecyclerView.ViewHolder {
        private FragNoticeReturnItemBinding tBinding;
        private TextView tvStatusReturn;

        public ReturnViewHolder(FragNoticeReturnItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
            tvStatusReturn = tBinding.tvStatusReturn;

        }
    }
}
