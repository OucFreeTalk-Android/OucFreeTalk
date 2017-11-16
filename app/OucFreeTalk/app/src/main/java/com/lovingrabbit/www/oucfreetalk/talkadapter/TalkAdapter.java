package com.lovingrabbit.www.oucfreetalk.talkadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.MainActivity;
import com.lovingrabbit.www.oucfreetalk.OtherPerson;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetail;

import java.util.List;

/**
 * Created by Zzzzzzzjk on 2017/4/26.
 */

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    Context context;
    private List<Talk> talkList;

    public TalkAdapter(List<Talk> talks){
        talkList = talks;
    }
    @Override
    public TalkAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.talkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Talk talk = talkList.get(position);
                Intent intent = new Intent(parent.getContext(), TalkDetail.class);
                intent.putExtra("id",talk.getId());
                intent.putExtra("owner",talk.getOwner());
                intent.putExtra("time",talk.getTime());
                intent.putExtra("title",talk.getArticle_tile());
                intent.putExtra("tag",talk.getArticle_tag());
                intent.putExtra("icon",talk.getPeople_icon());
                intent.putExtra("content",talk.getArticle_content());
                parent.getContext().startActivity(intent);
            }
        });
        holder.people_icon_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Talk talk = talkList.get(position);
                Intent intent = new Intent(parent.getContext(), OtherPerson.class);
                intent.putExtra("owner",talk.getOwner());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final TalkAdapter.ViewHolder holder, int position) {
        Talk talk = talkList.get(position);
        if (talk.getIsimg() == 0){
            holder.isImg.setVisibility(View.GONE);
        }else {
            holder.isImg.setImageResource(talk.getIsimg());
        }
        if (!talk.getPeople_icon().equals("pic")){
            Glide.with(context).load(IMG+talk.getPeople_icon()).into(holder.people_icon_img);
        }
        else {
            holder.people_icon_img.setImageResource(R.drawable.nav_icon);
        }
        holder.article_title_text.setText(talk.getArticle_tile());
        holder.article_tag_text.setText(talk.getArticle_tag());
        holder.article_content_text.setText(talk.getArticle_content());
        holder.realbody.setText(String.valueOf(talk.getRealbody()-1));
        holder.updateTime.setText(talk.getTime());
    }

    @Override
    public int getItemCount() {
        return talkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View talkView;
        ImageView people_icon_img;
        ImageView isImg;
        TextView article_title_text;
        TextView article_tag_text,realbody;
        TextView article_content_text,updateTime;
        public ViewHolder(View itemView) {
            super(itemView);
            talkView = itemView;
            people_icon_img = (ImageView) itemView.findViewById(R.id.people_icon);
            isImg = (ImageView) itemView.findViewById(R.id.isImgset);
            article_title_text = (TextView) itemView.findViewById(R.id.airtcle_title);
            article_tag_text = (TextView) itemView.findViewById(R.id.airticle_tag);
            article_content_text = (TextView) itemView.findViewById(R.id.airtcle_content);
            realbody = (TextView) itemView.findViewById(R.id.post_num);
            updateTime = (TextView) itemView.findViewById(R.id.updatetime);
        }
    }
}
