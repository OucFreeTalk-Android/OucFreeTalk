package com.lovingrabbit.www.oucfreetalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindViews;

public class Person_edit extends AppCompatActivity {
    @BindViews({R.id.information,R.id.power,R.id.jwc,R.id.score,R.id.stu_number})
    LinearLayout mInformation,mPower,mJwc,mScore,mStu_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_edit);
        clickListen();

    }
    public void clickListen(){
        mInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
