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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLogin = false;
    NavigationView navigationView;
    TalkAdapter adapter;
    LoaderManager loaderManager;
    boolean isNet;
    String username,nikename="";
    RecyclerView recyclerView;
    LinearLayout noNet;
    TextView name,focus,befocus;
    private List<Talk> talkList = new ArrayList<Talk>();
    private List<Talk> talks = new ArrayList<Talk>();
    private List<Talk> talks_cache = new ArrayList<Talk>();
    private String GET_POST_URL = "http://47.93.222.179/oucfreetalk/getPosts?pclass=1&index=1&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final EditText main_search = (EditText) findViewById(R.id.main_search);
        ImageView search_btn = (ImageView) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View draw = navigationView.inflateHeaderView(R.layout.nav_header);
        setSupportActionBar(toolbar);

        //设置home键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.logo);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        nikename = sharedPreferences.getString("nikename","");
        GET_POST_URL = GET_POST_URL + username;

        name = (TextView) draw.findViewById(R.id.nvhead_username);
        focus = (TextView) draw.findViewById(R.id.head_FocusMe);
        befocus = (TextView) draw.findViewById(R.id.head_MeFocus);
        name.setText(nikename);
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

        recyclerView = (RecyclerView) findViewById(R.id.main_recyview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TalkAdapter(talkList);
        recyclerView.setAdapter(adapter);

        noNet = (LinearLayout) findViewById(R.id.noNet);
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (isNet) {
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, MainActivity.this);
            noNet.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            noNet.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
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
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FocusPost.class);
                startActivity(intent);
                finish();
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
                finish();
            }
        });

    }
    public static String filterChinese(String chin){
        return chin.replaceAll("[\\u4e00-\\u9fa5]", "");
    }

    public void update(String get_result) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        int mfocus = jsonObject.getInt("focus");
        int mbefocus = jsonObject.getInt("befocus");
        for (int i =0;i<allpage; i++) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            String user = talk.getString("nikename");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time =talk.getString("updatetime");
            Date date = StringToDate(time);
            time = dateToString(date);
            int realbody = talk.getInt("realbody");
            Talk talk1 = new Talk(R.drawable.nav_icon,R.drawable.apple,title,user,context,id,owner,time,realbody);
            talkList.add(talk1);
        }
        focus.setText(String.valueOf(mfocus));
        befocus.setText(String.valueOf(mbefocus));
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
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (isNet) {
            loaderManager.restartLoader(0, null, MainActivity.this);
            noNet.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            noNet.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
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
        talkList.clear();
    }
}
