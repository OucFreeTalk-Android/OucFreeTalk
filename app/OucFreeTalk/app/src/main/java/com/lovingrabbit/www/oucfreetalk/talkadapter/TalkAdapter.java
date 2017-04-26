package com.lovingrabbit.www.oucfreetalk.talkadapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetail;

import java.util.List;

/**
 * Created by Zzzzzzzjk on 2017/4/26.
 */

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {
    private List<Talk> talkList;

    public TalkAdapter(List<Talk> talks){
        talkList = talks;
    }
    @Override
    public TalkAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.talkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Talk talk = talkList.get(position);
                Intent intent = new Intent(parent.getContext(), TalkDetail.class);
                intent.putExtra("title",talk.getArticle_tile());
                intent.putExtra("tag",talk.getArticle_tag());
                intent.putExtra("icon",talk.getPeople_icon());
                intent.putExtra("content",talk.getArticle_content());
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
        holder.people_icon_img.setImageResource(talk.getPeople_icon());
        holder.article_title_text.setText(talk.getArticle_tile());
        holder.article_tag_text.setText(talk.getArticle_tag());
        holder.article_content_text.setText(talk.getArticle_content());
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
        TextView article_tag_text;
        TextView article_content_text;
        public ViewHolder(View itemView) {
            super(itemView);
            talkView = itemView;
            people_icon_img = (ImageView) itemView.findViewById(R.id.people_icon);
            isImg = (ImageView) itemView.findViewById(R.id.isImgset);
            article_title_text = (TextView) itemView.findViewById(R.id.airtcle_title);
            article_tag_text = (TextView) itemView.findViewById(R.id.airticle_tag);
            article_content_text = (TextView) itemView.findViewById(R.id.airtcle_content);
        }
    }
}
