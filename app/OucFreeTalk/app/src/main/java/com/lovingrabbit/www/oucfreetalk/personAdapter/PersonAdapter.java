package com.lovingrabbit.www.oucfreetalk.personAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.TalkDetail;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;

import java.util.List;

/**
 * Created by zjk on 2017/11/11.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    List<Person> personList;
    public PersonAdapter(List<Person> persons){
        personList = persons;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        holder.personView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Person person = personList.get(position);
                Intent intent = new Intent(parent.getContext(), TalkDetail.class);
                intent.putExtra("id",person.getId());
                intent.putExtra("owner",person.getOwner());
                intent.putExtra("time",person.getTime());
                intent.putExtra("title",person.getArticle_tile());
                intent.putExtra("tag",person.getNikename());
                intent.putExtra("icon",person.getPeople_icon());
                intent.putExtra("content",person.getArticle_content());
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = personList.get(position);
        if (person.getIsimg() == 0){
            holder.isImg.setVisibility(View.GONE);
        }else {
            holder.isImg.setImageResource(person.getIsimg());
        }
        holder.people_icon_img.setImageResource(person.getPeople_icon());
        holder.article_title_text.setText(person.getArticle_tile());
        holder.article_tag_text.setText(person.getNikename());
        holder.article_content_text.setText(person.getArticle_content());
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View personView;
        ImageView people_icon_img;
        ImageView isImg;
        TextView article_title_text;
        TextView article_tag_text;
        TextView article_content_text;
        public ViewHolder(View itemView) {
            super(itemView);
            personView = itemView;
            people_icon_img = (ImageView) itemView.findViewById(R.id.people_icon);
            isImg = (ImageView) itemView.findViewById(R.id.isImgset);
            article_title_text = (TextView) itemView.findViewById(R.id.airtcle_title);
            article_tag_text = (TextView) itemView.findViewById(R.id.airticle_tag);
            article_content_text = (TextView) itemView.findViewById(R.id.airtcle_content);
        }
    }
}
