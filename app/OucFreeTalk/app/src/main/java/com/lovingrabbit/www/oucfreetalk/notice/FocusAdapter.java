package com.lovingrabbit.www.oucfreetalk.notice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.FocusTalk;
import com.lovingrabbit.www.oucfreetalk.NoticeView;
import com.lovingrabbit.www.oucfreetalk.OtherPerson;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;

import java.util.List;

/**
 * Created by zjk on 2017/11/16.
 */

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.ViewHolder> {
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    List<Focus> focusList;
    Context context;
    String postId;
    public FocusAdapter(List<Focus> focus){
        focusList = focus;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfocus_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        postId = sharedPreferences.getString("id","");
        holder.focusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Focus focus =focusList.get(postion);
                Intent intent = new Intent(parent.getContext(), FocusTalk.class);
                intent.putExtra("recieveId",focus.getId());
                intent.putExtra("postId",postId);
                intent.putExtra("nikename",focus.getNikename());
                context.startActivity(intent);
            }
        });
        holder.focusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Focus focus =focusList.get(position);
                Intent intent = new Intent(parent.getContext(), OtherPerson.class);
                intent.putExtra("owner",focus.getId());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Focus focus = focusList.get(position);
        if(focus.getPic().equals("pic")){
            holder.focusIcon.setImageResource(R.drawable.nav_icon);
        }else {
            Glide.with(context).load(IMG+focus.getPic()).into(holder.focusIcon);
        }
        holder.nikename.setText(focus.getNikename());
        holder.intro.setText(focus.getIntro());
        holder.time.setText(focus.getTime());
    }

    @Override
    public int getItemCount() {
        return focusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View focusView;
        ImageView focusIcon;
        TextView nikename,time,intro;
        public ViewHolder(View itemView) {
            super(itemView);
            focusView = itemView;
            focusIcon = (ImageView) itemView.findViewById(R.id.myfocus_icon);
            nikename = (TextView) itemView.findViewById(R.id.myfocus_username);
            time = (TextView) itemView.findViewById(R.id.myfocus_time);
            intro = (TextView) itemView.findViewById(R.id.myfocus_info);
        }
    }
}
