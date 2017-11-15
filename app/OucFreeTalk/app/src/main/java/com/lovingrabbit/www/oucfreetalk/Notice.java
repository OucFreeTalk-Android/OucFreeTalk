package com.lovingrabbit.www.oucfreetalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Notice extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LinearLayout post = (LinearLayout) findViewById(R.id.notice_post);
        LinearLayout find = (LinearLayout) findViewById(R.id.notice_find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.notice_add_post);
        LinearLayout notice = (LinearLayout) findViewById(R.id.notice_notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.notice_set);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this,FocusPost.class);
                startActivity(intent);
                finish();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this,AddPost.class);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this,Notice.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this,PersonSet.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
