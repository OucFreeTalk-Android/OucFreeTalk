package com.lovingrabbit.www.oucfreetalk.notice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.R;

import java.util.List;

/**
 * Created by zjk on 2017/5/13.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private List<Notice> noticeList;
    public NoticeAdapter(List<Notice> notices){
        noticeList = notices;
    }

    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeAdapter.ViewHolder holder, int position) {
        Notice notice = noticeList.get(position);
        holder.textView.setText(notice.getText());
        holder.topView.setText(notice.getTop());
        holder.deleteView.setText(notice.getDel());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deleteView,topView,textView;
        public ViewHolder(View itemView) {
            super(itemView);
            deleteView = (TextView) itemView.findViewById(R.id.tv_delete);
            topView = (TextView) itemView.findViewById(R.id.tv_top);
            textView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
