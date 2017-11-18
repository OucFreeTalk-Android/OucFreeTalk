package com.lovingrabbit.www.oucfreetalk;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.chatAdapter.ChatAdapter;
import com.lovingrabbit.www.oucfreetalk.chatAdapter.ChatModel;
import com.lovingrabbit.www.oucfreetalk.chatAdapter.ItemModel;
import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;
import com.lovingrabbit.www.oucfreetalk.untils.AddMessegeAsyncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetReplyAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FocusTalk extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText et;
    private Button tvSend;
    private String content;
    LinearLayoutManager linearLayoutManager;
    String postid,recieveid,nikename,addmessage,mePic,result="";
    String GET_MESSAGE_URL;
    boolean IsFirst = true;
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    String ADD_MESSAGE_URL = "http://47.93.222.179/oucfreetalk/addMessage";
    ImageView back;
    LoaderManager loaderManager;
    TextView title;
    ArrayList<ItemModel> models = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_talk);
        recyclerView = (RecyclerView) findViewById(R.id.focus_talk_rcy);
        et = (EditText) findViewById(R.id.focus_talk_edit);
        tvSend = (Button) findViewById(R.id.focus_talk_submit);
        back = (ImageView) findViewById(R.id.focus_talk_back);
        Intent intent = getIntent();
        postid = intent.getStringExtra("postId");
        recieveid = intent.getStringExtra("recieveId");
        nikename = intent.getStringExtra("nikename");
        GET_MESSAGE_URL = "http://47.93.222.179/oucfreetalk/getMessage?postid="+postid+"&receiveid="+recieveid;
        title = (TextView) findViewById(R.id.focus_talk_title);
        title.setText(nikename);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);

        loaderManager =getLoaderManager();
        loaderManager.initLoader(1,null,FocusTalk.this);

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmessage = et.getText().toString();
                if (addmessage.equals("")){
                    Toast.makeText(FocusTalk.this,"发表内容不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (IsFirst) {
                        loaderManager = getLoaderManager();
                        loaderManager.initLoader(2, null, FocusTalk.this);
                        IsFirst = false;
                    } else {
                        loaderManager = getLoaderManager();
                        loaderManager.restartLoader(2, null, FocusTalk.this);
                    }
                    et.setText("");
                }
            }
        });
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable(){
            @Override
            public void run() {

                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                handler.postDelayed(this, 1000);
                loaderManager.restartLoader(1,null,FocusTalk.this);
            }
        };
        handler.postDelayed(runnable, 2000);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                finish();
            }
        });
    }


    public void update(String get_result) throws JSONException, ParseException {

//        Log.e("result_detail", get_result);
        JSONObject jsonObject = new JSONObject(get_result);
        if(jsonObject.has("result")) {
            result = jsonObject.getString("result");
        }else {
            models.clear();
            int count = jsonObject.getInt("count");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < count; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
                ChatModel model = new ChatModel();
                String getPostid = talk.getString("postid");
                String context = talk.getString("content");
                if (getPostid.equals(postid)){
                    String getPostPic = talk.getString("postPic");
//                    Log.e("getPostPic", getPostPic );
                    mePic = getPostPic;
                    model.setContent(context);
                    model.setIcon(IMG+getPostPic);
                    models.add(new ItemModel(ItemModel.CHAT_B,model));
                }else {
                    String getRecievePic = talk.getString("postPic");
//                    Log.e("getRecievePic", getRecievePic );
                    model.setContent(context);
                    model.setIcon(IMG+getRecievePic);
                    models.add(new ItemModel(ItemModel.CHAT_A,model));
                }

            }
            adapter.replaceAll(models);
//            MoveToPosition(linearLayoutManager,models.size());
        }
    }
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
        manager.setStackFromEnd(true);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == 1){
            return new GetReplyAysncTaskLoader(this,GET_MESSAGE_URL);
        }else if (id == 2){
            return new AddMessegeAsyncTaskLoader(this,postid,recieveid,addmessage,ADD_MESSAGE_URL);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
//        Log.e("data:", data );
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter.replaceAll(models);
        if (!result.equals("")) {
            switch (result) {
                case "1":
                    Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
                    ChatModel model = new ChatModel();
                    model.setContent(addmessage);
                    model.setIcon(IMG + mePic);
                    models.add(new ItemModel(ItemModel.CHAT_B, model));
                    result="";
                    break;
                default:
                    Toast.makeText(this, "发表失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {

        }
        MoveToPosition(linearLayoutManager,models.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        models.clear();
    }
}
