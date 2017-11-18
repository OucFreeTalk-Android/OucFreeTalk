package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.untils.AddPostAysncTaskLoader;

import org.json.JSONObject;

public class AddPost extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    String title,content;
    String ADD_POST_URL = "http://47.93.222.179/oucfreetalk/addPost";
    LoaderManager loaderManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);
        LinearLayout post = (LinearLayout) findViewById(R.id.addpost_post);
        LinearLayout find = (LinearLayout) findViewById(R.id.addpost_find);
        LinearLayout addPost = (LinearLayout) findViewById(R.id.addpost_add_post);
        LinearLayout notice = (LinearLayout) findViewById(R.id.addpost_notice);
        LinearLayout set = (LinearLayout) findViewById(R.id.addpost_set);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loaderManager.restartLoader(0,null, MainActivity.this);
                Intent intent = new Intent(AddPost.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPost.this,FocusPost.class);
                startActivity(intent);
                finish();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPost.this,NoticeView.class);
                startActivity(intent);
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPost.this,PersonSet.class);
                startActivity(intent);
                finish();
            }
        });
        Button addpostBtn = (Button) findViewById(R.id.add);
        addpostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEd = (EditText) findViewById(R.id.addpost_title);

                EditText contentEd = (EditText) findViewById(R.id.addpost_content);
                try {
                    Typeface typeFace = Typeface.createFromAsset(getResources().getAssets(), "fonts/AndroidEmoji.ttf");
                    titleEd.setTypeface(typeFace);
                    contentEd.setTypeface(typeFace);
                } catch (Exception e) {

                }
                title = titleEd.getText().toString();
                if (title.equals("")){
                    Toast.makeText(AddPost.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    content = contentEd.getText().toString();
                    if (content.equals("")){
                        Toast.makeText(AddPost.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        loaderManager = getLoaderManager();

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.initLoader(0, null, AddPost.this);
                    }
                }
            }
        });
        ImageView re_btn = (ImageView) findViewById(R.id.return_btn);
        re_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String getEmojiStringByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("id","");
        if (username == ""){
            Toast.makeText(this,"请登录！",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddPost.this,Login.class);
            startActivity(intent);
        }else {
            return new AddPostAysncTaskLoader(this,username,title,content,ADD_POST_URL);
        }
        return null;
    }
    private int parseJson(String JSONData) {
        int results = 100;
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
    public void onLoadFinished(Loader<String> loader, String data) {
        int log_results = parseJson(data);

        if (log_results == 0 ){
            Toast.makeText(AddPost.this,"发表失败",Toast.LENGTH_SHORT).show();
        }else if (log_results == 1 ){
            Toast.makeText(AddPost.this,"发表成功",Toast.LENGTH_SHORT).show();
            finish();
        }else if (log_results == 2 ){
            Toast.makeText(AddPost.this,"服务器故障",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
