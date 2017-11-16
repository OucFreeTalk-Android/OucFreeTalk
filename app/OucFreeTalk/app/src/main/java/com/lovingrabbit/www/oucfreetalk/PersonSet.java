package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.personAdapter.PersonAdapter;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.NetworkConnected;

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

public class PersonSet extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    PersonAdapter adapter;
    TextView person_user,introd,mfocus,mbefocus;
    String intro,nikename;
    String username;
    List<Person> personList = new ArrayList<Person>();
    LoaderManager loaderManager;
    LinearLayout noNet,noPerson_Post;
    ImageView person_icon;
    RecyclerView recyclerView;
    boolean isNet;
    private String GET_PERSON_POST_URL ;
    String IMG = "http://47.93.222.179/oucfreetalk/upload/";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        recyclerView = (RecyclerView) findViewById(R.id.person_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);
        mfocus = (TextView) findViewById(R.id.FocusMe);
        mbefocus = (TextView) findViewById(R.id.MeFocus);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        Log.e("username:", username );
        GET_PERSON_POST_URL = "http://47.93.222.179/oucfreetalk/getPostPerson?id="+username+"&target="+username;
        person_user = (TextView) findViewById(R.id.person_username);
        introd = (TextView) findViewById(R.id.person_description);
        person_icon = (ImageView) findViewById(R.id.person_icon_img);
        noPerson_Post = (LinearLayout) findViewById(R.id.person_noPersonPost);
        LinearLayout post = (LinearLayout) findViewById(R.id.person_post);
        LinearLayout find = (LinearLayout) findViewById(R.id.person_find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.person_add_post);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.person_notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.person_set);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonSet.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonSet.this,AddPost.class);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonSet.this,Notice.class);
                startActivity(intent);
                finish();
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonSet.this,FocusPost.class);
                startActivity(intent);
                finish();
            }
        });
        final Button edit = (Button) findViewById(R.id.person_edit_btn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                username = sharedPreferences.getString("id","");
                if (username.equals("")){
                    Toast.makeText(PersonSet.this,"请登录！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PersonSet.this,Login.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(PersonSet.this, Person_edit.class);
                    startActivity(intent);
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaderManager = getLoaderManager();
                loaderManager.restartLoader(0,null,PersonSet.this);
            }
        });
        noNet = (LinearLayout) findViewById(R.id.person_noNet);
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (isNet) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, PersonSet.this);
        }else {
            recyclerView.setVisibility(View.GONE);
            noNet.setVisibility(View.VISIBLE);
        }
    }
    public void update(String get_result) throws JSONException, ParseException {
        Log.e("result", get_result );
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        if (allpage == 0){
            noPerson_Post.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        int focus = jsonObject.getInt("focus");
        int befocus = jsonObject.getInt("befocus");
        nikename = jsonObject.getString("nikename");
        intro = jsonObject.getString("intro");
        String pic = jsonObject.getString("pic");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i = allpage-1;i>=0; i--) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time =talk.getString("createtime");
            Date date = StringToDate(time);
            time = dateToString(date);
            int realbody = talk.getInt("realbody");
            Person person = new Person(pic,R.drawable.apple,title,nikename,context,id,owner,time,intro,realbody,focus,befocus);
            personList.add(person);
        }
        mbefocus.setText(String.valueOf(befocus));
        mfocus.setText(String.valueOf(focus));
        person_user.setText(nikename);
        introd.setText(intro);
        if (pic.equals("pic")){
            person_icon.setImageResource(R.drawable.nav_icon);
        }else {
            Glide.with(this).load(IMG + pic).into(person_icon);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nikename",nikename);
        editor.commit();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GetPostAysncTaskLoader(this,GET_PERSON_POST_URL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        personList.clear();
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        personList.clear();
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
