package com.lovingrabbit.www.oucfreetalk.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17922 on 2017/4/25.
 */

public class TalkFragment extends Fragment
{
    private static final String ARG_C = "content";
    private List<Talk> talkList = new ArrayList<Talk>();
    private String article_title ="这是一个简洁好用的标题";
    private String article_tag = "#这是一个标签";
    private String article_content = "“钢铁骑士”曾是美泰一款风行全球的男孩人偶玩具，也曾被拍成电视系列动画片，此次真人电影版的故事是《雷神2：黑暗世界》...";

    public static TalkFragment newInstance() {
        return new TalkFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.from(getContext()).inflate(R.layout.talk_main, container, false);
        /*
         * recyclerView 填充'
         */
        initTalk();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recyview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        TalkAdapter adapter = new TalkAdapter(talkList);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        return view;

    }
    //初始化帖子主页列表
    public void initTalk(){
        for (int i = 0;i<=20;i++){
            Talk talk1 = new Talk(R.drawable.nav_icon,R.drawable.apple,article_title,article_tag,article_content);
            talkList.add(talk1);
//            Talk talk2 = new Talk(R.drawable.nav_icon,article_title,article_tag,article_content);
//            talkList.add(talk2);
        }

    }
}
