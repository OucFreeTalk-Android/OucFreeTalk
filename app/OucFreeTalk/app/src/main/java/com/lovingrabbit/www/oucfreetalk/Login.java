package com.lovingrabbit.www.oucfreetalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.untils.HttpQutils;

import org.json.JSONObject;

import java.io.IOException;

public class Login extends AppCompatActivity implements Runnable {

    String result,name,passwd;

    private String url ="http://47.93.222.179/oucfreetalk/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_stu);
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.input_username);
                name = username.getText().toString();
                EditText password = (EditText) findViewById(R.id.input_password);
                passwd = password.getText().toString();
                new Thread(new Login()).start();
            }
        });
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void run() {

        HttpQutils httpQutils = new HttpQutils();
        String user = httpQutils.bolwingJson(name, passwd);

        try {
            result = httpQutils.login(url,user);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int log_results = parseJson(result);
                    if (log_results == 0 ){
                        Toast.makeText(Login.this,"用户不存在",Toast.LENGTH_SHORT).show();
                    }else if (log_results == 1 ){
                        Toast.makeText(Login.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int parseJson(String JSONData) {
        int results = 0;
        try {
            JSONObject jsonObject = new JSONObject(JSONData);
            results = jsonObject.getInt("result");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
