package com.knotlink.salseman.adapter.notice;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;

import com.knotlink.salseman.databinding.FragNoticeComplainItemBinding;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;

import java.util.List;

public class AdapterNoticeComplain extends RecyclerView.Adapter<AdapterNoticeComplain.NoticeComplainViewHolder> {

    private List<ModelNoticeComplain> tModels;

    @NonNull
    @Override
    public NoticeComplainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragNoticeComplainItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_notice_complain_item, viewGroup, false);
        return new NoticeComplainViewHolder(tBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull NoticeComplainViewHolder noticeComplainViewHolder, int i) {
        ModelNoticeComplain tModel = tModels.get(i);
        noticeComplainViewHolder.fragNoticeComplainItemBinding.setNoticeComplain(tModel);
        if (tModel.getManager().equalsIgnoreCase("")){
            noticeComplainViewHolder.tvManager.setVisibility(View.GONE);
        }else {
            noticeComplainViewHolder.tvManager.setVisibility(View.VISIBLE);

        }
     if (tModel.getSm().equalsIgnoreCase("")){
            noticeComplainViewHolder.tvSm.setVisibility(View.GONE);
        }else {
            noticeComplainViewHolder.tvSm.setVisibility(View.VISIBLE);

        }
     if (tModel.getAsm().equalsIgnoreCase("")){
            noticeComplainViewHolder.tvAsm.setVisibility(View.GONE);
        }else {
            noticeComplainViewHolder.tvAsm.setVisibility(View.VISIBLE);

        }
     if (tModel.getDm().equalsIgnoreCase("")){
            noticeComplainViewHolder.tvDm.setVisibility(View.GONE);
        }else {
            noticeComplainViewHolder.tvDm.setVisibility(View.VISIBLE);

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
    public void settModels(List<ModelNoticeComplain> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class NoticeComplainViewHolder extends RecyclerView.ViewHolder {
        private final FragNoticeComplainItemBinding fragNoticeComplainItemBinding;
        private TextView tvManager;
        private TextView tvSm;
        private TextView tvAsm;
        private TextView tvDm;
        public NoticeComplainViewHolder(final FragNoticeComplainItemBinding fragNoticeComplainItemBinding) {
            super(fragNoticeComplainItemBinding.getRoot());
            this.fragNoticeComplainItemBinding = fragNoticeComplainItemBinding;
            tvManager = fragNoticeComplainItemBinding.tvManagerLabel;
            tvSm = fragNoticeComplainItemBinding.tvSmLabel;
            tvAsm = fragNoticeComplainItemBinding.tvAsmLabel;
            tvDm = fragNoticeComplainItemBinding.tvDmLabel;
        }
    }
}
