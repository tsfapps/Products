package com.knotlink.salseman.adapter.notify;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
        public NoticeComplainViewHolder(final FragNoticeComplainItemBinding fragNoticeComplainItemBinding) {
            super(fragNoticeComplainItemBinding.getRoot());
            this.fragNoticeComplainItemBinding = fragNoticeComplainItemBinding;
        }
    }
}
