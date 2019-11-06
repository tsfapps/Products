package com.knotlink.salseman.adapter.notice;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.knotlink.salseman.R;
import com.knotlink.salseman.databinding.FragNoticeRequestItemBinding;
import com.knotlink.salseman.model.notice.ModelNoticeRequest;

import java.util.List;

public class AdapterNoticeRequest extends RecyclerView.Adapter<AdapterNoticeRequest.NoticeRequestViewHolder> {

    private List<ModelNoticeRequest> tModels;

    @NonNull
    @Override
    public NoticeRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragNoticeRequestItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_notice_request_item, viewGroup, false);

        return new NoticeRequestViewHolder(tBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeRequestViewHolder noticeRequestViewHolder, int i) {

        ModelNoticeRequest tModel = tModels.get(i);
        noticeRequestViewHolder.tBinding.setNoticeRequest(tModel);
    }

    @Override
    public int getItemCount() {
        if (tModels != null){
            return tModels.size();
        }else {
            return 0;
        }
    }

    public void setRequest(List<ModelNoticeRequest> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    class NoticeRequestViewHolder extends RecyclerView.ViewHolder{
        private FragNoticeRequestItemBinding tBinding;
        public NoticeRequestViewHolder(FragNoticeRequestItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;
        }
    }
}
