package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.notice.Focus;
import com.lovingrabbit.www.oucfreetalk.notice.FocusAdapter;
import com.lovingrabbit.www.oucfreetalk.notice.NoticeAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.GetReplyAysncTaskLoader;
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

public class NoticeView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    TextView title;
    RecyclerView recyclerView;
    PopupWindow mPopupWindow;
    String username;
    LoaderManager loaderManager;
    LinearLayout noNet;
    int IsFocus=0;
    boolean isNet,firstIsNoNet;
    SwipeRefreshLayout swipeRefreshLayout;
    FocusAdapter Fadapter;
    NoticeAdapter Nadapter;
    TextView near_notice,myfocus;
    Boolean IsFirst = true;
    DisplayMetrics metrics = new DisplayMetrics();
    final int sWidth = metrics.widthPixels;
    final int sHeight = metrics.heightPixels;
    String GET_ME_FOCUS_URL = "http://47.93.222.179/oucfreetalk/getMeFocus?id=";
    String GET_NOTICE_URL = "http://47.93.222.179/oucfreetalk/getNotice?id=";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    List<Focus> focusList = new ArrayList<Focus>();
//    List<Notice> noticeList = new ArrayList<Notice>();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LinearLayout post = (LinearLayout) findViewById(R.id.notice_post);
        final LinearLayout find = (LinearLayout) findViewById(R.id.notice_find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.notice_add_post);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice_notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.notice_set);
        View popupView = getLayoutInflater().inflate(R.layout.poplist, null);

        recyclerView = (RecyclerView) findViewById(R.id.notice_recyview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Nadapter = new NoticeAdapter(focusList);
        recyclerView.setAdapter(Nadapter);
        ImageView imageView = (ImageView) findViewById(R.id.notice_img);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.notice_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false,0,120);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        noNet = (LinearLayout) findViewById(R.id.notice_noNet);
        isNet = new NetworkConnected().isNetworkConnected(this);

        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        title = (TextView) findViewById(R.id.notice_near);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(findViewById(R.id.notice_near),-80,0);
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAsDropDown(findViewById(R.id.notice_near),-80,0);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("id","");
        GET_ME_FOCUS_URL = GET_ME_FOCUS_URL + username;
        GET_NOTICE_URL =GET_NOTICE_URL +username;
        near_notice = (TextView) popupView.findViewById(R.id.iv_item1);
        myfocus = (TextView) popupView.findViewById(R.id.iv_item2);

        near_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nadapter.notifyDataSetChanged();
                IsFocus = 0;
                mPopupWindow.dismiss();
                title.setText("最近通知");
                refresh();
                Nadapter = new NoticeAdapter(focusList);
                recyclerView.setAdapter(Nadapter);
            }
        });
        myfocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                title.setText("我的关注");
                IsFocus = 1;
                Fadapter = new FocusAdapter(focusList);
                recyclerView.setAdapter(Fadapter);
                refresh();
            }
        });


        loaderManager =getLoaderManager();
        loaderManager.initLoader(1,null,NoticeView.this);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeView.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeView.this,FocusPost.class);
                startActivity(intent);
                finish();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeView.this,AddPost.class);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeView.this,NoticeView.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeView.this,PersonSet.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void refresh(){
        focusList.clear();
        isNet = new NetworkConnected().isNetworkConnected(this);
        if (IsFocus == 1) {
            if (firstIsNoNet) {
                if (isNet) {
                    noNet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(2, null, NoticeView.this);
                } else {
                    noNet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            } else {
                if (isNet) {
                    noNet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(2, null, NoticeView.this);
                } else {
                    noNet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }else if(IsFocus == 0) {
            if (firstIsNoNet) {
                if (isNet) {
                    noNet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(1, null, NoticeView.this);
                } else {
                    noNet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                if (isNet) {
                    noNet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(1, null, NoticeView.this);
                } else {
                    noNet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        }
    }

    public void update(String data) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(data);
        if (IsFocus == 1) {
            int count = jsonObject.getInt("count");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < count; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
                String id = talk.getString("stuid");
                String intro = talk.getString("intro");
                String nikename = talk.getString("nikename");
                String pic = talk.getString("pic");
                Focus focus = new Focus(id, nikename, pic, intro);
                focusList.add(focus);
            }
            Fadapter.notifyDataSetChanged();
        }else if (IsFocus ==0){
            int count = jsonObject.getInt("count");
            JSONArray searchJson = jsonObject.getJSONArray("search");
            for (int i = 0; i < 20; i++) {
                JSONObject talk = searchJson.getJSONObject(i);
                String id = talk.getString("stuid");
                String time = talk.getString("time");
                Date date = StringToDate(time);
                time = dateToString(date);
                String nikename = talk.getString("nikename");
                int noticeClass = talk.getInt("noticeClass");
                String pic = talk.getString("pic");
                String postTime = talk.getString("postTime");
                String context = talk.getString("context");
                Focus focus;
                if (noticeClass == 1){
                    int postid = talk.getInt("postid");
                    String title = talk.getString("title");
                    focus = new Focus(id,nikename,pic,postid,time,noticeClass,title,postTime,context);
                    focusList.add(focus);
                }else if(noticeClass == 2) {
                    int position = talk.getInt("postlocation");
                    int commentid = talk.getInt("commentid");
                    focus = new Focus(id,nikename,pic,time,commentid,noticeClass,postTime,context,position);
                    focusList.add(focus);
                }
            }
            Nadapter.notifyDataSetChanged();
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == 2){
            return new GetReplyAysncTaskLoader(this,GET_ME_FOCUS_URL);
        }else if (id == 1){
            return new GetReplyAysncTaskLoader(this,GET_NOTICE_URL);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        focusList.clear();
        Log.e("data", data );
        Log.e("IsFocus", String.valueOf(IsFocus) );
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (IsFocus == 0){
            Nadapter.notifyDataSetChanged();
        }else if (IsFocus == 1){
            Fadapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        focusList.clear();
    }

    public static Date StringToDate(String time) throws ParseException {
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(time);
        return date;
    }
    public static String dateToString(Date time){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("MM-dd HH:mm");
        String ctime = formatter.format(time);
        return ctime;
    }
}
