package com.lovingrabbit.www.oucfreetalk.notice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;

import java.util.List;

/**
 * Created by zjk on 2017/11/16.
 */

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.ViewHolder> {
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    List<Focus> focusList;
    Context context;
    public FocusAdapter(List<Focus> focus){
        focusList = focus;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfocus_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.focusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Focus focus =focusList.get(postion);
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
