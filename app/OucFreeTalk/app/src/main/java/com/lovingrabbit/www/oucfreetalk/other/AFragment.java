package com.lovingrabbit.www.oucfreetalk.other;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovingrabbit.www.oucfreetalk.R;
import com.lovingrabbit.www.oucfreetalk.notice.Notice;
import com.lovingrabbit.www.oucfreetalk.notice.NoticeAdapter;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.HttpQutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17922 on 2017/4/25.
 */

public class AFragment extends Fragment implements Runnable
{

    private static final String ARG_C = "content";
    private String content,context,title,result;
    private List<Notice> noticeList = new ArrayList<Notice>();
    TalkAdapter adapter;
    private List<Talk> talkList = new ArrayList<Talk>();
    private String GET_POST_URL = "http://222.195.145.152:8811/api/Posts/getPosts";
    private String article_title ="这是一个简洁好用的标题";
    private String article_tag = "#这是一个标签";
    private String article_content = "“钢铁骑士”曾是美泰一款风行全球的男孩人偶玩具，也曾被拍成电视系列动画片，此次真人电影版的故事是《雷神2：黑暗世界》...";

    public static AFragment newInstance(String content) {

        Bundle args = new Bundle();
        args.putString(ARG_C,content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view ;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        content = getArguments().getString(ARG_C);
        Log.v("content:",content);
        switch (content){
            case "0":
                view = inflater.from(getContext()).inflate(R.layout.talk_main, container, false);
                Thread thread = new Thread();
                thread.start();
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recyview);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new TalkAdapter(talkList);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                return view;
            case "1":
                view = inflater.from(getContext()).inflate(R.layout.notice, container, false);
                initNotice();
                RecyclerView noticeRecy = (RecyclerView) view.findViewById(R.id.notice_list);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
                noticeRecy.setLayoutManager(linearLayoutManager1);
                NoticeAdapter noticeAdapter = new NoticeAdapter(noticeList);
                noticeRecy.setAdapter(noticeAdapter);

                return view;
            case "2":
                view = inflater.from(getContext()).inflate(R.layout.addpost, container, false);
                return view;
            default:
                view = inflater.from(getContext()).inflate(R.layout.test, container, false);
                return view;
        }
    }
    @Override
    public void run() {
        HttpQutils httpQutils = new HttpQutils();
        String json = httpQutils.getPostJson();
        try {
            result = httpQutils.connect(GET_POST_URL,json);
            Log.e("return:",result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getActivity() == null)
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("return:",result);
                    update(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void update(String get_result) throws JSONException {
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i = 0 ;i<allpage;i++) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("contenttext");
            Talk talk1 = new Talk(R.drawable.nav_icon,R.drawable.apple,title,article_tag,context);
            talkList.add(talk1);
        }
    }
    public void initNotice(){
        for (int i = 0;i<=20;i++) {
            Notice notice = new Notice("Zzzzzzzjk"+i);
            noticeList.add(notice);
        }
    }


}
