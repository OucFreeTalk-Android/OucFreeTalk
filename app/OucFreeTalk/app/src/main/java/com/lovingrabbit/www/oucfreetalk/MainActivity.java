package com.lovingrabbit.www.oucfreetalk;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;
import com.lovingrabbit.www.oucfreetalk.talkadapter.TalkAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLogin = false;
    NavigationView navigationView;
    TalkAdapter adapter;
    LoaderManager loaderManager;
    private List<Talk> talkList = new ArrayList<Talk>();
    private String GET_POST_URL = "http://47.93.222.179/oucfreetalk/getPosts?pclass=1&index=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View draw = navigationView.inflateHeaderView(R.layout.nav_header);
        setSupportActionBar(toolbar);

        //设置home键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.logo);
        }

        //home键被点击滑出菜单栏,菜单栏默认选中第一个
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false,0,120);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        RelativeLayout rl = (RelativeLayout) draw.findViewById(R.id.header);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin == false){
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                }
            }
        });
        loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(0, null, MainActivity.this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TalkAdapter(talkList);
        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("id","");
        Log.e("username:",username);
        LinearLayout post = (LinearLayout) findViewById(R.id.post);
        LinearLayout find = (LinearLayout) findViewById(R.id.find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.add_post);
        LinearLayout notice = (LinearLayout) findViewById(R.id.notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.set);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                loaderManager.restartLoader(0,null, MainActivity.this);
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPost.class);
                startActivity(intent);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PersonSet.class);
                startActivity(intent);
            }
        });

    }

    public void update(String get_result) throws JSONException {
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i = allpage-1;i>0; i--) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            String user = talk.getString("nikename");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time =talk.getString("createtime");
            Talk talk1 = new Talk(R.drawable.nav_icon,R.drawable.apple,title,user,context,id,owner,time);
            talkList.add(talk1);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    public void refresh(){
        loaderManager.restartLoader(0, null, MainActivity.this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GetPostAysncTaskLoader(this,GET_POST_URL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
//        Log.d("result:", data);
        talkList.clear();
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        talkList.clear();
    }
}
