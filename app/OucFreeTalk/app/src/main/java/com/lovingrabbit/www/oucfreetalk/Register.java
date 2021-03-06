package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lovingrabbit.www.oucfreetalk.untils.LoginAsyncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.RegisterAsyncTaskLoader;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class Register extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    LoaderManager loaderManager;
    String userID,username,password,passwordAgain;
    EditText id,passwd,passwdAgain,name;

    private String url ="http://47.93.222.179/oucfreetalk/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        id = (EditText) findViewById(R.id.setID);
        name = (EditText) findViewById(R.id.setUserName);
        passwd = (EditText) findViewById(R.id.setPasswd);
        passwdAgain = (EditText) findViewById(R.id.setPasswdAgain);
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = id.getText().toString();
                if (isStuNO(userID)){
                    username = name.getText().toString();
                    if (username.equals("")){
                        Toast.makeText(Register.this,"昵称不允许为空",Toast.LENGTH_SHORT).show();
                    }else {
                        password = passwd.getText().toString();
                        passwordAgain = passwdAgain.getText().toString();
                        if (password.equals(passwordAgain)) {
                            loaderManager = getLoaderManager();

                            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                            // because this activity implements the LoaderCallbacks interface).
                            loaderManager.initLoader(0, null, Register.this);
                        } else {
                            Toast.makeText(Register.this, "密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(Register.this,"账号不合规则",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public static boolean isStuNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((1[3-8]0[0-9]00[0-4][1-4]0[0-9][0-9])|(1[3-8]0[0-8]00[0-4][1-4]1[0-8][0-9]))");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new RegisterAsyncTaskLoader(Register.this,userID,password,username,url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        int log_results = parseJson(data);
        if (log_results == 0 ){
            Toast.makeText(Register.this,"用户已存在",Toast.LENGTH_SHORT).show();
        }else if (log_results == 1 ){
            Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        }else if (log_results == 2 ){
            Toast.makeText(Register.this,"注册失败",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    private int parseJson(String JSONData) {
        int results = 3;
        try {
            Log.e("zjkzjk:",JSONData);
            JSONObject jsonObject = new JSONObject(JSONData);
            results = jsonObject.getInt("result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
