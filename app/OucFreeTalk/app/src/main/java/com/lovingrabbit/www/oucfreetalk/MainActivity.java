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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLogin = false;
    NavigationView navigationView;
    TalkAdapter adapter;
    LoaderManager loaderManager;
    boolean isNet,mSex;
    String username,mNikename,mBirth,mYear,mIntro,search_text;
    RecyclerView recyclerView;
    LinearLayout noNet;
    boolean firstIsNoNet;
    EditText main_search;
    ImageView nv_icon;
    TextView nv_name,nv_focus,nv_befocus,nv_sex,nv_birth,nv_year,nv_intro;
    private List<Talk> talkList = new ArrayList<Talk>();
    private List<Talk> talks = new ArrayList<Talk>();
    private List<Talk> talks_cache = new ArrayList<Talk>();

    private String GET_POST_URL = "http://47.93.222.179/oucfreetalk/getPosts?pclass=1&index=1&id=";
    String IMG = "http://47.93.222.179/oucfreetalk/img/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        main_search = (EditText) findViewById(R.id.main_search);
        main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Search_talk();
                    return true;
                }else return false;
            }
        });
        ImageView search_btn = (ImageView) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_talk();
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
        GET_POST_URL = GET_POST_URL + username;

        nv_name = (TextView) draw.findViewById(R.id.nvhead_username);
        nv_focus = (TextView) draw.findViewById(R.id.head_FocusMe);
        nv_befocus = (TextView) draw.findViewById(R.id.head_MeFocus);
        nv_birth = (TextView) draw.findViewById(R.id.nv_user_birth);
        nv_intro = (TextView) draw.findViewById(R.id.nv_user_intro);
        nv_year = (TextView) draw.findViewById(R.id.nv_user_year);
        nv_sex = (TextView) draw.findViewById(R.id.nv_user_sex);
        nv_icon= (ImageView) draw.findViewById(R.id.nv_icon_img);

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
                    Intent intent = new Intent(MainActivity.this,PersonSet.class);
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
            firstIsNoNet = true;
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
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Notice.class);
                startActivity(intent);
                finish();
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

    public void Search_talk(){
        main_search.clearFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);//关闭输入法
        talkList.clear();
        search_text = main_search.getText().toString();
        Log.e("search_text", search_text );
        if (!search_text.equals("")){

            for (int i= 0 ;i<talks.size();i++) {
                Pattern p = Pattern.compile(search_text);
                Matcher mContent = p.matcher(talks.get(i).getArticle_tile()+talks.get(i).getArticle_content());
                while (mContent.find()) {
                    if (!mContent.group().equals("")) {
                        talkList.add(talks.get(i));
                    }
                }
            }
        }else {
            talkList = talks;
        }
        adapter.notifyDataSetChanged();
    }

    public void update(String get_result) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        int mfocus = jsonObject.getInt("focus");
        int mbefocus = jsonObject.getInt("befocus");
        mBirth = jsonObject.getString("mBirth");
        mIntro = jsonObject.getString("mIntro");
        mNikename = jsonObject.getString("mNikename");
        mSex = jsonObject.getBoolean("mSex");
        String mPic = jsonObject.getString("mPic");
        mYear = jsonObject.getString("mYear");
        for (int i =0;i<allpage; i++) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            String user = talk.getString("nikename");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time = talk.getString("updatetime");
            String pic = talk.getString("pic");
            Date date = StringToDate(time);
            time = dateToString(date);
            int realbody = talk.getInt("realbody");
            Talk talk1 = new Talk(pic,R.drawable.apple,title,user,context,id,owner,time,realbody);
            talkList.add(talk1);
        }
        talks.clear();
        for (int i = 0 ;i<talkList.size();i++){
            talks.add(talkList.get(i));
        }
        Log.e("talk_size", String.valueOf(talks.size()));
        if(mNikename.equals("")){
            nv_focus.setText("暂无");
            nv_befocus.setText("暂无");
            nv_sex.setText("暂无");
            nv_name.setText("暂无");
            nv_year.setText("暂无");
            nv_intro.setText("暂无");
            nv_birth.setText("暂无");
            nv_icon.setImageResource(R.drawable.nav_icon);
        }else {
            nv_focus.setText(String.valueOf(mfocus));
            nv_befocus.setText(String.valueOf(mbefocus));
            if (mSex) {
                nv_sex.setText("男");
            } else {
                nv_sex.setText("女");
            }
            nv_name.setText(mNikename);
            nv_year.setText(mYear);
            nv_intro.setText(mIntro);
            nv_birth.setText(mBirth);
            Glide.with(this).load(IMG + mPic).into(nv_icon);
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
        main_search.setText("");
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (firstIsNoNet) {
            if (isNet) {
                noNet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loaderManager = getLoaderManager();
                loaderManager.initLoader(0, null, MainActivity.this);
            } else {
                noNet.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }else {
            if (isNet) {
                noNet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loaderManager.restartLoader(0, null, MainActivity.this);
            } else {
                noNet.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
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
