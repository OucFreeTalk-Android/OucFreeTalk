package com.lovingrabbit.www.oucfreetalk;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.personAdapter.PersonAdapter;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.NetworkConnected;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjk on 2017/11/11.
 */

public class PersonSet extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    PersonAdapter adapter;
    TextView person_user,introd;
    String intro,nikename;
    String username;
    List<Person> personList = new ArrayList<Person>();
    LoaderManager loaderManager;
    LinearLayout noNet,noPerson_Post;
    RecyclerView recyclerView;
    boolean isNet;
    private String GET_PERSON_POST_URL = "http://47.93.222.179/oucfreetalk/getPostPerson?id=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        recyclerView = (RecyclerView) findViewById(R.id.person_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        GET_PERSON_POST_URL = GET_PERSON_POST_URL +username;
        person_user = (TextView) findViewById(R.id.person_username);
        introd = (TextView) findViewById(R.id.person_description);
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
    public void update(String get_result) throws JSONException {
        Log.e("result", get_result );
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        if (allpage == 0){
            noPerson_Post.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        nikename = jsonObject.getString("nikename");
        intro = jsonObject.getString("intro");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i = allpage-1;i>=0; i--) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time =talk.getString("createtime");
            Person person = new Person(R.drawable.nav_icon,R.drawable.apple,title,nikename,context,id,owner,time,intro);
            personList.add(person);
        }
        person_user.setText(nikename);
        introd.setText(intro);
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
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        personList.clear();
    }
}
