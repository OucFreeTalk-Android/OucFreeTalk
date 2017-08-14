package com.lovingrabbit.www.oucfreetalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LoginHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_img);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginHomeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,2000);
        finish();
    }



}
