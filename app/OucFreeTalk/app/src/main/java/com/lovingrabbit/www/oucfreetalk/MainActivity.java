package com.lovingrabbit.www.oucfreetalk;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.lovingrabbit.www.oucfreetalk.other.AFragment;
import com.lovingrabbit.www.oucfreetalk.other.MyViewPagerAdapter;
import com.lovingrabbit.www.oucfreetalk.talkadapter.Talk;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    //
    int[] testColors = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
//    int[] testColors = {0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688};

    NavigationController mNavigationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        //底部标签栏
        PageBottomTabLayout pageBottomTabLayout = (PageBottomTabLayout) findViewById(R.id.tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.ic_ondemand_video_black_24dp,"Movies & TV",testColors[0])
                .addItem(R.drawable.ic_audiotrack_black_24dp, "Music",testColors[1])
                .addItem(R.drawable.ic_book_black_24dp, "Books",testColors[2])
                .addItem(R.drawable.ic_news_black_24dp, "Newsstand",testColors[3])
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),mNavigationController.getItemCount()));

        //自动适配ViewPager页面切换
        mNavigationController.setupWithViewPager(viewPager);

        //也可以设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd","selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                Log.i("asd","onRepeat selected: " + index);
            }
        });

        //设置消息圆点
//        mNavigationController.setMessageNumber(1,12);
//        mNavigationController.setHasMessage(1,true);


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar,menu);
//        return true;
//    }

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AFragment aFragment = new AFragment();
                        aFragment.initTalk();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
