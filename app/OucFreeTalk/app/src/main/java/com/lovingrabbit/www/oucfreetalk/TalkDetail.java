package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.detailAdapter.ButtonInterface;
import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;
import com.lovingrabbit.www.oucfreetalk.detailAdapter.DetailAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.AddCommentAysncTaskLoader;
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

public class TalkDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private String article_title ;
    private String article_tag,result ="",del_result="";
    private String article_content,owner,time;
    int postid,postion;
    String addreply;
    LoaderManager loaderManager;
    private String people_icon;
    EditText editText;
    DetailAdapter adapter;
    Button delete_post;
    String GET_POST_URL ;
    String DELETE_POST_URL;
    String DELETE_COMMENT_URL;
    String ADD_COMMENT_URL = "http://47.93.222.179/oucfreetalk/addComments";
    private List<Detail> detailList = new ArrayList<Detail>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
        //接受intent传递的信息
        Intent intent = getIntent();
        TextView titleView = (TextView) findViewById(R.id.airtcle_detail_title);
        article_title = intent.getStringExtra("title");
        article_tag = intent.getStringExtra("tag");
        owner = intent.getStringExtra("owner");
        time = intent.getStringExtra("time");
        postid =intent.getIntExtra("id",0);
        article_content = intent.getStringExtra("content");
        people_icon = intent.getStringExtra("icon");
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("id","");
        delete_post = (Button) findViewById(R.id.delete_post);
        if (username.equals(owner)){
            delete_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DELETE_POST_URL = "http://47.93.222.179/oucfreetalk/deletePost?postid="+postid;
                    showAlertDialog();
                }
            });
        }else {
            delete_post.setVisibility(View.GONE);
        }
        titleView.setText(article_title);
        GET_POST_URL = "http://47.93.222.179/oucfreetalk/getPost?postid="+ postid +"&index=1";

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detail_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//        initDetail();
        adapter = new DetailAdapter(detailList);
        recyclerView.setAdapter(adapter);
        adapter.buttonSetOnclick(new ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                DELETE_COMMENT_URL = "http://47.93.222.179/oucfreetalk/deleteComment?commentid="+detailList.get(position).getPostcID();
                showCommentAlertDialog();
            }
        });

        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null ,TalkDetail.this);

        ImageView back = (ImageView) findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button = (Button) findViewById(R.id.detail_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.detail_edit);
                addreply = editText.getText().toString();
                if (addreply.equals("")) {
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(2,null ,TalkDetail.this);
                }else {
                    loaderManager = getLoaderManager();
                    loaderManager.restartLoader(2,null ,TalkDetail.this);

                }
                editText.setText("");

            }
        });

    }
    public void showCommentAlertDialog(){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this)
                .setTitle("确定要删除该回复？")
                .setMessage("删除后无法恢复");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaderManager = getLoaderManager();
                loaderManager.initLoader(4,null,TalkDetail.this);
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
    public void showAlertDialog(){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this)
                .setTitle("确定要删除该帖子？")
                .setMessage("删除后无法恢复");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loaderManager = getLoaderManager();
                loaderManager.initLoader(3,null,TalkDetail.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void initDetail(){
        Detail detail = new Detail(article_tag,time,owner,article_content,people_icon,1,0,0);
        detailList.clear();
        detailList.add(detail);
    }
    public void update(String get_result) throws JSONException, ParseException {
        Log.e("result_detail", get_result);
        JSONObject jsonObject = new JSONObject(get_result);
        if(jsonObject.has("result")) {
            result = jsonObject.getString("result");
        }else if(jsonObject.has("del_result")){
            del_result = jsonObject.getString("del_result");
        } else {
            initDetail();
            int allpage = jsonObject.getInt("allpage");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < allpage; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
//            String title = talk.getString("title");
                String context = talk.getString("commentcontext");
                String user = talk.getString("nikename");
                postion = talk.getInt("postlocation");
                String stuid = talk.getString("id");
                String time = talk.getString("createtime");
                Date date = StringToDate(time);
                time = dateToString(date);
                int commentid = talk.getInt("commentid");
                int replybody = talk.getInt("replybody");
                String pic = talk.getString("pic");
                Detail detail = new Detail(user, time,stuid, context, pic,postion,commentid,replybody);
                detailList.add(detail);
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == 1){
            return new GetReplyAysncTaskLoader(this,GET_POST_URL);
        }else if(id == 2){
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("id","");
            if (username.equals("")){
                Toast.makeText(this,"请登录！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TalkDetail.this,Login.class);
                startActivity(intent);
            }else {
                return new AddCommentAysncTaskLoader(this,username,postid,addreply,ADD_COMMENT_URL);
            }
        }else if(id == 3){
            return new GetReplyAysncTaskLoader(this,DELETE_POST_URL);
        }else if (id == 4){
            return new GetReplyAysncTaskLoader(this,DELETE_COMMENT_URL);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
//        Log.d("result:", data);
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!result.equals("")){
            switch (result) {
                case "1":
                    Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("id", "");
                    String nikename = sharedPreferences.getString("nikename", "");
                    Date date = new Date();
                    String createTime = dateToString(date);
                    Detail detail = new Detail(nikename, createTime, username, addreply, people_icon, postion + 1, 0, 0);
                    detailList.add(detail);
                    adapter.notifyDataSetChanged();
                    finish();
                    break;
                default:
                    Toast.makeText(this, "发表失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else if (!del_result.equals("")){
            switch (del_result){
                case "1":
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TalkDetail.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            adapter.notifyDataSetChanged();
        }

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
    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
