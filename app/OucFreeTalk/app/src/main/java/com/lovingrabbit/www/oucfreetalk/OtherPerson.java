package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.personAdapter.PersonAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.AddFocusAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;
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

public class OtherPerson extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    String userId,nikename,intro,username;
    RecyclerView recyclerView;
    boolean isfocus;
    LoaderManager loaderManager;
    TextView person_user,introd,mfocus,mbefocus,addFocus;
    LinearLayout noPerson_Post;
    PersonAdapter adapter;
    String result="";
    ImageView other_person_icon;
    String IMG = "http://47.93.222.179/oucfreetalk/img/";
    List<Person> personList = new ArrayList<Person>();
    private String GET_PERSON_POST_URL;
    private String ADD_FOCUS_URL = "http://47.93.222.179/oucfreetalk/addFocus";
    private String DELETE_FOCUS_URL = "http://47.93.222.179/oucfreetalk/deleteMeFocus";;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_person);
        Intent intent =getIntent();
        userId = intent.getStringExtra("owner");

        recyclerView = (RecyclerView) findViewById(R.id.other_person_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);

        mfocus = (TextView) findViewById(R.id.other_person_FocusMe);
        mbefocus = (TextView) findViewById(R.id.other_person_MeFocus);
        addFocus = (TextView) findViewById(R.id.addfocus);
        other_person_icon = (ImageView) findViewById(R.id.other_person_icon_img);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        GET_PERSON_POST_URL = "http://47.93.222.179/oucfreetalk/getPostPerson?id="+username+"&target="+userId;
        if (userId.equals(username)){
            addFocus.setText("无法关注自己");
        }
        else {
            addFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (username.equals("")){
                        Toast.makeText(OtherPerson.this,"请登录！",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OtherPerson.this,Login.class);
                        startActivity(intent);

                    }else {
                        if (isfocus){
                            loaderManager = getLoaderManager();
                            loaderManager.initLoader(3, null, OtherPerson.this);
                        }else {
                            loaderManager = getLoaderManager();
                            loaderManager.initLoader(2, null, OtherPerson.this);
                        }
                    }
                }
            });
        }
        ImageView back = (ImageView) findViewById(R.id.other_person_edit_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        person_user = (TextView) findViewById(R.id.other_person_username);
        introd = (TextView) findViewById(R.id.other_person_description);
        noPerson_Post = (LinearLayout) findViewById(R.id.other_person_noNet);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,OtherPerson.this);

    }
    public void update(String get_result) throws JSONException, ParseException {
        Log.e("result", get_result);

        JSONObject jsonObject = new JSONObject(get_result);
        if (jsonObject.has("result")){
            result = jsonObject.getString("result");
        }
        else {
            int allpage = jsonObject.getInt("allpage");
            if (allpage == 0) {
                noPerson_Post.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            int focus = jsonObject.getInt("focus");
            int befocus = jsonObject.getInt("befocus");
            isfocus = jsonObject.getBoolean("isfocus");
            nikename = jsonObject.getString("nikename");
            intro = jsonObject.getString("intro");
            String pic = jsonObject.getString("pic");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = allpage - 1; i >= 0; i--) {
                JSONObject talk = searchJson.getJSONObject(i);
                String title = talk.getString("title");
                String context = talk.getString("content");
                int id = talk.getInt("id");
                String owner = talk.getString("owner");
                String time = talk.getString("createtime");
                Date date = StringToDate(time);
                time = dateToString(date);
                int realbody = talk.getInt("realbody");
                Person person = new Person(pic, R.drawable.apple, title, nikename, context, id, owner, time, intro, realbody, focus, befocus);
                personList.add(person);
            }
            if (isfocus && !addFocus.getText().toString().equals("无法关注自己")){
                addFocus.setText("取消关注");
            }
            if (pic.equals("pic")){
                other_person_icon.setImageResource(R.drawable.nav_icon);
            }else {
                Glide.with(this).load(IMG + pic).into(other_person_icon);
            }
            mbefocus.setText(String.valueOf(befocus));
            mfocus.setText(String.valueOf(focus));
            person_user.setText(nikename);
            introd.setText(intro);
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id  == 1) {
            return new GetPostAysncTaskLoader(this, GET_PERSON_POST_URL);
        }else if(id == 2){
            return new AddFocusAysncTaskLoader(this,username,userId,ADD_FOCUS_URL);
        }else if(id == 3){
            return new AddFocusAysncTaskLoader(this,username,userId,DELETE_FOCUS_URL);
        }
        return null;
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
        if (!result.equals("")){
            if (isfocus){
                switch (result) {
                    case "1":
                        Toast.makeText(OtherPerson.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                        addFocus.setText("关注");
                        finish();
                        break;
                    default:
                        Toast.makeText(OtherPerson.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                        break;
                }

            } else {
                switch (result) {
                    case "1":
                        Toast.makeText(OtherPerson.this, "关注成功", Toast.LENGTH_SHORT).show();
                        addFocus.setText("取消关注");
                        finish();
                        break;
                    default:
                        Toast.makeText(OtherPerson.this, "关注失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

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
