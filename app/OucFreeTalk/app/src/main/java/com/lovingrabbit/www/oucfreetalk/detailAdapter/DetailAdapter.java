package com.lovingrabbit.www.oucfreetalk.detailAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lovingrabbit.www.oucfreetalk.R;

import java.util.List;

/**
 * Created by zjk on 2017/11/11.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>{
    private List<Detail> detailList;
    public DetailAdapter(List<Detail> details){
        detailList = details;
    }
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_list,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.detailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        Detail detail = detailList.get(position);
        holder.nikename.setText(detail.getUsername());
        holder.time.setText(detail.getTime());
        holder.content.setText(detail.getContent());
        holder.icon.setImageResource(detail.getPeople_icon());
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View detailView;
        TextView nikename,time,content;
        ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            detailView = itemView;
            nikename = (TextView) itemView.findViewById(R.id.detail_username);
            time = (TextView) itemView.findViewById(R.id.detail_time);
            content = (TextView) itemView.findViewById(R.id.detail_content);
            icon = (ImageView) itemView.findViewById(R.id.detail_icon);
        }
    }
}
