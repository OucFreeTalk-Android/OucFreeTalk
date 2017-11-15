package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

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

public class FocusPost extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    RecyclerView recyclerView;
    TalkAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String username;
    boolean isNet;
    LinearLayout noNet;
    LoaderManager loaderManager;
    boolean firstIsNoNet;
    List<Talk> talkList = new ArrayList<Talk>();
    String GET_FOCUS_POST = "http://47.93.222.179/oucfreetalk/getMeFocusPost?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_post);
        LinearLayout post = (LinearLayout) findViewById(R.id.focus_post);
        LinearLayout find = (LinearLayout) findViewById(R.id.focus_find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.focus_add_post);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.focus_notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.focus_set);

        recyclerView = (RecyclerView) findViewById(R.id.focus_recyview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TalkAdapter(talkList);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.focus_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false,0,120);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        noNet = (LinearLayout) findViewById(R.id.focus_noNet);
        isNet = new NetworkConnected().isNetworkConnected(this);

        recyclerView.setAdapter(adapter);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        GET_FOCUS_POST = GET_FOCUS_POST +username;
        if (isNet) {
            loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(0, null, FocusPost.this);
            noNet.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            noNet.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            firstIsNoNet = true;
        }
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusPost.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusPost.this,AddPost.class);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusPost.this,Notice.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FocusPost.this,PersonSet.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void refresh(){
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (firstIsNoNet) {
            if (isNet) {
                noNet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loaderManager = getLoaderManager();
                loaderManager.initLoader(0, null, FocusPost.this);
            } else {
                noNet.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }else {
            if (isNet) {
                noNet.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                loaderManager.restartLoader(0, null, FocusPost.this);
            } else {
                noNet.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public void update(String get_result) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i =0;i<allpage; i++) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            String user = talk.getString("nikename");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time =talk.getString("updatetime");
            String pic = talk.getString("pic");
            Date date = StringToDate(time);
            time = dateToString(date);
            int realbody = talk.getInt("realbody");
            Talk talk1 = new Talk(pic,R.drawable.apple,title,user,context,id,owner,time,realbody);
            talkList.add(talk1);
        }
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GetPostAysncTaskLoader(this,GET_FOCUS_POST);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
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

    @Override
    public void onLoaderReset(Loader<String> loader) {
        talkList.clear();
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
