package com.lovingrabbit.www.oucfreetalk;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.lovingrabbit.www.oucfreetalk.detailAdapter.ButtonInterface;
import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;

import com.lovingrabbit.www.oucfreetalk.detailAdapter.DetailReplyAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.AddCommentAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.AddReplyAsyncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetReplyAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
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
    String addreply,replyId,ownerId,result="",del_result="",person_icon;
    int postlocation,commentid;
    String DELETE_REPLY_URL;
    boolean IsFirst = true;
    String replyid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_detail_reply);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        time = intent.getStringExtra("time");
        content = intent.getStringExtra("content");
        postlocation = intent.getIntExtra("postlocation",0);
        person_icon = intent.getStringExtra("icon");
        replyId = intent.getStringExtra("id");
        ownerId = intent.getStringExtra("ownerId");
        Log.e("ownerID", ownerId );
        commentid = intent.getIntExtra("commentid",0);
        GET_COMMENT_URL ="http://47.93.222.179/oucfreetalk/getComment?commentid="+ commentid +"&index=1";
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,TalkDetailReply.this);
        Button button = (Button) findViewById(R.id.detail_reply_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.detail_reply_edit);
                addreply = editText.getText().toString();
                if (addreply.equals("")){
                    Toast.makeText(TalkDetailReply.this,"回复不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (IsFirst) {
                        loaderManager = getLoaderManager();
                        loaderManager.initLoader(2, null, TalkDetailReply.this);
                    } else {
                        loaderManager = getLoaderManager();
                        loaderManager.restartLoader(2, null, TalkDetailReply.this);

                    }
                    editText.setText("");
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detail_reply_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DetailReplyAdapter(detailList);
        recyclerView.setAdapter(adapter);
        adapter.buttonSetOnclick(new ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                DELETE_REPLY_URL = "http://47.93.222.179/oucfreetalk/deleteReply?replyid="+detailList.get(position).getReplyId();
                showReplyAlertDialog();
            }
        });
    }
    public void showReplyAlertDialog(){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this)
                .setTitle("确定要删除该回复？")
                .setMessage("删除后无法恢复");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaderManager = getLoaderManager();
                loaderManager.initLoader(3,null,TalkDetailReply.this);
            }
        });
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();//将dialog显示出来
    }
    public void initreply(){
        Detail detail = new Detail(username,time,ownerId,replyId,content,person_icon,"");
        detailList.clear();
        detailList.add(detail);
    }
    public void update(String get_result) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(get_result);
        if(jsonObject.has("result")) {
            result = jsonObject.getString("result");
        }else if(jsonObject.has("del_result")){
            del_result = jsonObject.getString("del_result");
        } else {
            initreply();
            int allpage = jsonObject.getInt("allpage");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < allpage; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
                String context = talk.getString("commentcontext");
                String user = talk.getString("nikename");
                String time = talk.getString("createtime");
                Date date = StringToDate(time);
                time = dateToString(date);
                String id = talk.getString("stuid");
                replyid = talk.getString("id");
                String pic = talk.getString("pic");
                Detail detail = new Detail(user,time,ownerId,id,context, pic,replyid);
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
        }else if (id == 3){
            return new GetReplyAysncTaskLoader(this,DELETE_REPLY_URL);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!result.equals("")){
            switch (result){
                case "1":
                    Toast.makeText(this,"回复成功",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String nikename = sharedPreferences.getString("nikename","");
                    String userid = sharedPreferences.getString("id","");
                    Date date =new Date();
                    String createTime = dateToString(date);
                    Detail detail = new Detail(nikename,createTime,ownerId,userid,addreply,person_icon,"");
                    detailList.add(detail);
                    adapter.notifyDataSetChanged();
                    finish();
                    break;
                default:
                    Toast.makeText(this,"发表失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }else if (!del_result.equals("")){
            switch (del_result) {
                case "1":
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(TalkDetailReply.this, TalkDetail.class);
//                    startActivity(intent);
                    finish();
                    break;
                default:
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else{
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        detailList.clear();

    }
    public static Date StringToDate(String time) throws ParseException {
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(time);
        return date;
    }
    public static String dateToString(Date time){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        String ctime = formatter.format(time);
        return ctime;
    }
}
