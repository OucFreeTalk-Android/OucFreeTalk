package com.lovingrabbit.www.oucfreetalk;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;

import com.lovingrabbit.www.oucfreetalk.detailAdapter.DetailReplyAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.AddCommentAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.AddReplyAsyncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetReplyAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zjk on 2017/11/11.
 */

public class TalkDetailReply extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    DetailReplyAdapter adapter;
    private List<Detail> detailList = new ArrayList<Detail>();
    String username,time,content;
    EditText editText;
    LoaderManager loaderManager;
    String ADD_REPLY_URL = "http://47.93.222.179/oucfreetalk/addReply";
    String GET_COMMENT_URL;
    String addreply,replyId,result="";
    int person_icon,postlocation,commentid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_detail_reply);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        time = intent.getStringExtra("time");
        content = intent.getStringExtra("content");
        postlocation = intent.getIntExtra("postlocation",0);
        person_icon = intent.getIntExtra("icon",0);
        replyId = intent.getStringExtra("id");
        commentid = intent.getIntExtra("commentid",0);
        GET_COMMENT_URL ="http://47.93.222.179/oucfreetalk/getComment?commentid="+ commentid +"&index=1";
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,TalkDetailReply.this);
        content = content + " "+ commentid + " "+replyId;
        Button button = (Button) findViewById(R.id.detail_reply_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.detail_reply_edit);
                addreply = editText.getText().toString();
                if (addreply.equals("")) {
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(2,null ,TalkDetailReply.this);
                }else {
                    loaderManager = getLoaderManager();
                    loaderManager.restartLoader(2,null ,TalkDetailReply.this);

                }
                editText.setText("");

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detail_reply_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DetailReplyAdapter(detailList);
        recyclerView.setAdapter(adapter);
    }
    public void initreply(){
        Detail detail = new Detail(username,time,content,person_icon);
        detailList.clear();
        detailList.add(detail);
    }
    public void update(String get_result) throws JSONException {
        JSONObject jsonObject = new JSONObject(get_result);
        if(jsonObject.has("result")) {
            result = jsonObject.getString("result");
        }else {
            initreply();
            int allpage = jsonObject.getInt("allpage");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < allpage; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
                String context = talk.getString("commentcontext");
                String user = talk.getString("nikename");
                String time = talk.getString("createtime");
                Detail detail = new Detail(user,time,context, person_icon);
                detailList.add(detail);
            }
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new GetReplyAysncTaskLoader(this,GET_COMMENT_URL);
        }else if(id == 2) {
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String Mid = sharedPreferences.getString("id","");
            if (Mid.equals("")){
                Toast.makeText(this,"请登录！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TalkDetailReply.this,Login.class);
                startActivity(intent);
            }else {
                return new AddReplyAsyncTaskLoader(this,Mid,replyId,commentid,addreply,ADD_REPLY_URL);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.e("reply_result:", data );
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result.equals("")){
            adapter.notifyDataSetChanged();
        }else {
            switch (result){
                case "1":
                    Toast.makeText(this,"回复成功",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String nikename = sharedPreferences.getString("nikename","");
                    Date date =new Date();
                    String createTime = dateToString(date);
                    Detail detail = new Detail(nikename,createTime,addreply,person_icon);
                    detailList.add(detail);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    Toast.makeText(this,"发表失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }


    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        detailList.clear();

    }
    public static String dateToString(Date time){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(time);
        return ctime;
    }
}
