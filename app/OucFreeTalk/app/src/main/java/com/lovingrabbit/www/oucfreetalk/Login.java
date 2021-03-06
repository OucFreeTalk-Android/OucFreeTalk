package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.untils.HttpQutils;
import com.lovingrabbit.www.oucfreetalk.untils.LoginAsyncTaskLoader;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    LoaderManager loaderManager;
    EditText username,password;
    String name,passwd;
    CheckBox ifSava;
    List<String> tag = new ArrayList<String>() ;

    private String url = "http://47.93.222.179/oucfreetalk/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_stu);
        Button login = (Button) findViewById(R.id.login_button);
        ifSava = (CheckBox) findViewById(R.id.ifSave);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.input_username);
                name = username.getText().toString();
                password = (EditText) findViewById(R.id.input_password);
                passwd = password.getText().toString();
                if(username.equals("")){
                    loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(0, null, Login.this);
                }else {
                    loaderManager = getLoaderManager();
                    loaderManager.restartLoader(0, null, Login.this);
                }

            }
        });
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, PersonSet.class);
                startActivity(intent);
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


    private int parseJson(String JSONData) {
        int results = 3;
        try {
            Log.e("zjkzjk:",JSONData);

            JSONObject jsonObject = new JSONObject(JSONData);
            results = jsonObject.getInt("result");
            Log.e("result:",results+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new LoginAsyncTaskLoader(Login.this,name,passwd,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        int log_results = parseJson(data);

        if (log_results == 0 ){
            Toast.makeText(Login.this,"用户不存在",Toast.LENGTH_SHORT).show();
        }else if (log_results == 1 ){
            Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
            if (ifSava.isChecked()){
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",name);
                editor.putString("password",passwd);
                editor.commit();
                tag.add(name);
                PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "4lnUKTgqnq5B6Njip7zpmrLW");
                PushManager.setTags(getApplicationContext(),tag);
            }
            else {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",name);
                editor.commit();
            }
            Intent intent = new Intent(Login.this, PersonSet.class);
            startActivity(intent);
            finish();
        }else if (log_results == 2 ){
            Toast.makeText(Login.this,"密码错误",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
