package com.lovingrabbit.www.oucfreetalk;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class TalkDetail extends AppCompatActivity {
    private String article_title ;
    private String article_tag ;
    private String article_content;
    private int people_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
        //接受intent传递的信息
        Intent intent = getIntent();
        TextView titleView = (TextView) findViewById(R.id.airtcle_detail_title);
        TextView tagView = (TextView) findViewById(R.id.airticle_datail_tag);
        TextView contentView = (TextView) findViewById(R.id.airtcle_detail_content);
        ImageView iconView = (ImageView) findViewById(R.id.people_detail_icon);
        article_title = intent.getStringExtra("title");
        article_tag = intent.getStringExtra("tag");
        article_content = intent.getStringExtra("content");
        people_icon = intent.getIntExtra("icon",0);
        iconView.setImageResource(people_icon);
        tagView.setText(article_tag);
        titleView.setText(article_title);
        for (int i = 0;i<5;i++){
            article_content = article_content+"  "+article_content;
        }
        contentView.setText(article_content);

        //设置自定义标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //设置home键
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.logo);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
