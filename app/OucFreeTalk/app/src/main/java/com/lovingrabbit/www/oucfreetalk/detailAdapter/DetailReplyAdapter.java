package com.lovingrabbit.www.oucfreetalk.detailAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.OtherPerson;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetailReply;

import java.util.List;

/**
 * Created by zjk on 2017/11/12.
 */

public class DetailReplyAdapter extends RecyclerView.Adapter<DetailReplyAdapter.ViewHolder>{
    List<Detail> detailList;
    String userOwner,userId;
    ButtonInterface buttonInterface;
    public DetailReplyAdapter(List<Detail> details){
        detailList = details;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_reply_list,parent,false);
        final DetailReplyAdapter.ViewHolder viewHolder = new DetailReplyAdapter.ViewHolder(view);
        SharedPreferences sharedPreferences = parent.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId =sharedPreferences.getString("id","");
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Detail detail = detailList.get(position);
                Intent intent = new Intent(parent.getContext(), OtherPerson.class);
                intent.putExtra("owner", detail.getId());
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Detail detail = detailList.get(position);
        holder.nikename.setText(detail.getUsername());
        holder.time.setText(detail.getTime());
        holder.content.setText(detail.getContent());
        if (!userId.equals(detail.getId())){
            holder.del_reply.setVisibility(View.GONE);
        }else {
            holder.del_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                        buttonInterface.onclick(v,position);
                    }

                }
            });
        }
        if (!detail.getOwnerID().equals(detail.getId())){
            holder.owner.setVisibility(View.GONE);
        }
        holder.icon.setImageResource(detail.getPeople_icon());
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View detailReplyView;
        TextView nikename,time,content,owner;
        ImageView icon;
        Button del_reply;
        public ViewHolder(View itemView) {
            super(itemView);
            detailReplyView = itemView;
            nikename = (TextView) itemView.findViewById(R.id.detail_reply_username);
            time = (TextView) itemView.findViewById(R.id.detail_reply_time);
            content = (TextView) itemView.findViewById(R.id.detail_reply_content);
            icon = (ImageView) itemView.findViewById(R.id.detail_reply_icon);
            owner = (TextView) itemView.findViewById(R.id.detail_reply_owner);
            del_reply = (Button) itemView.findViewById(R.id.delete_reply);
        }
    }
}
