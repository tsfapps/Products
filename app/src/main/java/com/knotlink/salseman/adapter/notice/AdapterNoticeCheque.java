package com.knotlink.salseman.adapter.notice;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.knotlink.salseman.R;
import com.knotlink.salseman.databinding.FragNoticeChequeItemBinding;
import com.knotlink.salseman.databinding.FragNoticeComplainItemBinding;
import com.knotlink.salseman.model.notice.ModelNoticeCheque;
import com.knotlink.salseman.model.notice.ModelNoticeComplain;

import java.util.List;

public class AdapterNoticeCheque extends RecyclerView.Adapter<AdapterNoticeCheque.NoticeComplainViewHolder> {

    private List<ModelNoticeCheque> tModels;

    @NonNull
    @Override
    public NoticeComplainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FragNoticeChequeItemBinding tBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.frag_notice_cheque_item, viewGroup, false);
        return new NoticeComplainViewHolder(tBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull NoticeComplainViewHolder noticeComplainViewHolder, int i) {
        ModelNoticeCheque tModel = tModels.get(i);
        noticeComplainViewHolder.tBinding.setNoticeCheque(tModel);

    }
    @Override
    public int getItemCount() {

        if (tModels != null) {
            return tModels.size();

        } else {
            return 0;
        }
    }
    public void settModels(List<ModelNoticeCheque> tModels){
        this.tModels = tModels;
        notifyDataSetChanged();
    }

    public class NoticeComplainViewHolder extends RecyclerView.ViewHolder {
        private final FragNoticeChequeItemBinding tBinding;

        public NoticeComplainViewHolder(final FragNoticeChequeItemBinding tBinding) {
            super(tBinding.getRoot());
            this.tBinding = tBinding;

        }
    }
}
