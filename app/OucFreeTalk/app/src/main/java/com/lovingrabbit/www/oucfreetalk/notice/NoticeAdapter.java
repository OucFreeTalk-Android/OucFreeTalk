package com.lovingrabbit.www.oucfreetalk.notice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetail;
import com.lovingrabbit.www.oucfreetalk.TalkDetailReply;

import java.util.List;

/**
 * Created by zjk on 2017/11/17.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    List<Focus> noticeList;
    Intent intent;
    Context context;
    public NoticeAdapter(List<Focus> notices){
        noticeList = notices;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfocus_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.noticeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Focus focus =noticeList.get(postion);
                if(focus.getNoticeclass() == 1){
                    intent = new Intent(parent.getContext(), TalkDetail.class);
                    intent.putExtra("title",focus.getTitle());
                    intent.putExtra("id",focus.getPostid());
                    intent.putExtra("owner",focus.getId());
                    intent.putExtra("time",focus.getTime());
                    intent.putExtra("tag",focus.getNikename());
                    intent.putExtra("icon",focus.getPic());
                    intent.putExtra("content",focus.getContext());
                }else if(focus.getNoticeclass() ==2){
                    intent = new Intent(parent.getContext(), TalkDetailReply.class);
                    intent.putExtra("username", focus.getNikename());
                    intent.putExtra("time", focus.getTime());
                    intent.putExtra("postlocation", focus.getPostlocation());
                    intent.putExtra("icon", focus.getPic());
                    intent.putExtra("id", focus.getId());
                    intent.putExtra("ownerId",focus.getId());
                    intent.putExtra("commentid", focus.getCommentid());
                    intent.putExtra("content", focus.getContext());
                }

                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Focus notice = noticeList.get(position);
        if(notice.getPic().equals("pic")){
            holder.focusIcon.setImageResource(R.drawable.nav_icon);
        }else {
            Glide.with(context).load(IMG+notice.getPic()).into(holder.focusIcon);
        }
        holder.nikename.setText(notice.getNikename());
        holder.intro.setText("回复了你");
        holder.time.setText(notice.getTime());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View noticeView;
        ImageView focusIcon;
        TextView nikename,time,intro;
        public ViewHolder(View itemView) {
            super(itemView);
            noticeView = itemView;
            focusIcon = (ImageView) itemView.findViewById(R.id.myfocus_icon);
            nikename = (TextView) itemView.findViewById(R.id.myfocus_username);
            time = (TextView) itemView.findViewById(R.id.myfocus_time);
            intro = (TextView) itemView.findViewById(R.id.myfocus_info);
        }
    }
}
