package com.lovingrabbit.www.oucfreetalk.detailAdapter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.OtherPerson;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetail;
import com.lovingrabbit.www.oucfreetalk.TalkDetailReply;

import java.util.List;

/**
 * Created by zjk on 2017/11/11.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<Detail> detailList;
    String userOwner,userId;
    private ButtonInterface buttonInterface;
    public DetailAdapter(List<Detail> details){
        detailList = details;
    }
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.detailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position != 0) {
                    Detail detail = detailList.get(position);
                    Intent intent = new Intent(parent.getContext(), TalkDetailReply.class);
                    intent.putExtra("username", detail.getUsername());
                    intent.putExtra("time", detail.getTime());
                    intent.putExtra("postlocation", detail.getPostlocation());
                    intent.putExtra("icon", detail.getPeople_icon());
                    intent.putExtra("id", detail.getId());
                    intent.putExtra("ownerId", userOwner);
                    intent.putExtra("commentid", detail.getPostcID());
                    intent.putExtra("content", detail.getContent());
                    parent.getContext().startActivity(intent);
                }
            }
        });
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
        SharedPreferences sharedPreferences = parent.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId =sharedPreferences.getString("id","");
        return viewHolder;
    }

    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, final int position) {
        Detail detail = detailList.get(position);
        holder.nikename.setText(detail.getUsername());
        holder.time.setText(detail.getTime());
        holder.content.setText(detail.getContent());
        if (detail.getPostlocation()-1 == 0){
            holder.position.setText("顶楼");
            userOwner = detail.getId();
            holder.del_comment.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
            holder.realbody.setVisibility(View.GONE);
        }
        else {
            holder.position.setText("第"+(detail.getPostlocation()-1)+"楼");
            if (!userId.equals(detail.getId())){
                holder.del_comment.setVisibility(View.GONE);
            }else {
                holder.del_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                            buttonInterface.onclick(v,position);
                        }

                    }
                });
                holder.realbody.setText(String.valueOf(detail.getRealbody()));
            }
        }
        if (!detail.getId().equals(userOwner)){
            holder.owner.setVisibility(View.GONE);
        }
        holder.icon.setImageResource(detail.getPeople_icon());
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        View detailView;
        TextView nikename,time,content,position,owner,realbody;
        ImageView icon,img;
        Button del_comment;
        public ViewHolder(View itemView) {
            super(itemView);
            detailView = itemView;
            nikename = (TextView) itemView.findViewById(R.id.detail_username);
            time = (TextView) itemView.findViewById(R.id.detail_time);
            position = (TextView) itemView.findViewById(R.id.detail_position_info);
            content = (TextView) itemView.findViewById(R.id.detail_content);
            icon = (ImageView) itemView.findViewById(R.id.detail_icon);
            owner = (TextView) itemView.findViewById(R.id.detail_owner);
            realbody = (TextView) itemView.findViewById(R.id.post_reply_num);
            img = (ImageView) itemView.findViewById(R.id.post_reply_img);
            del_comment = (Button) itemView.findViewById(R.id.delete_comment);
        }


    }
}
