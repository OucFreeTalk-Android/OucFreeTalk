package com.lovingrabbit.www.oucfreetalk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindViews;

public class Person_edit extends AppCompatActivity {
    @BindViews({R.id.information,R.id.power,R.id.jwc,R.id.score,R.id.stu_number})
    RelativeLayout mInformation,mPower,mJwc,mScore,mStu_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_edit);
        mInformation = (RelativeLayout) findViewById(R.id.information);
        mPower = (RelativeLayout) findViewById(R.id.power);
        mJwc = (RelativeLayout) findViewById(R.id.jwc);
        mScore = (RelativeLayout) findViewById(R.id.score);
        mStu_number = (RelativeLayout) findViewById(R.id.stu_number);
        ImageView back = (ImageView) findViewById(R.id.person_edit_return_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent  = new Intent(Person_edit.this,PersonSet.class);
//                startActivity(intent);
                finish();
            }
        });
        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(Person_edit.this,"注销成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Person_edit.this,PersonSet.class);
                startActivity(intent);
            }
        });
        clickListen();

    }
    public void clickListen(){
        mInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Person_edit.this,User_infromation.class);
                startActivity(intent);

            }
        });
        mPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mJwc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mStu_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Person_edit.this,stu_number.class);
                startActivity(intent);
            }
        });
    }
}
