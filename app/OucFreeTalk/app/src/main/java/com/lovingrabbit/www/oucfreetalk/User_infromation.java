package com.lovingrabbit.www.oucfreetalk;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;
import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.untils.EditorUserAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User_infromation extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText user_birth,user_sex,user_nikename,user_year,user_intro;
    String mSex,mYear,result="";
    String mid;
    String Usex,Ubirth,Uname,Uyear,Uintro;
    LoaderManager loaderManager;
    String GET_USER_URL = "http://47.93.222.179/oucfreetalk/getUser?id=";
    String EDITOR_URL = "http://47.93.222.179/oucfreetalk/editor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infromation);
        user_birth = (EditText) findViewById(R.id.user_birth);
        user_sex = (EditText) findViewById(R.id.user_sex);
        user_nikename = (EditText) findViewById(R.id.user_nikename);
        user_year = (EditText) findViewById(R.id.user_year);
        user_intro = (EditText) findViewById(R.id.user_intro);
        user_birth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        user_birth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
        user_sex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showSexLog();
                    return true;
                }
                return false;
            }
        });
        user_sex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSexLog();
                }
            }
        });
        user_year.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showYearLog();
                    return true;
                }
                return false;
            }
        });
        user_year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showYearLog();
                }
            }
        });
        ImageView back = (ImageView) findViewById(R.id.user_edit_return_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        mid = sharedPreferences.getString("id","");
        GET_USER_URL = GET_USER_URL + mid;
        Button save = (Button) findViewById(R.id.user_sava_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ubirth = user_birth.getText().toString();
                Uintro = user_intro.getText().toString();
                Uname = user_nikename.getText().toString();
                Usex = user_sex.getText().toString();
                Uyear = user_year.getText().toString();
                loaderManager = getLoaderManager();
                loaderManager.initLoader(2,null,User_infromation.this);
            }
        });
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,User_infromation.this);
    }
    protected void showSexLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(User_infromation.this);
        builder.setTitle("请选择性别");
        final String[] sex = {"男", "女", "未知性别"};
        builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(User_infromation.this, "性别为：" + sex[which], Toast.LENGTH_SHORT).show();
                mSex = sex[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                user_sex.setText(mSex);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
    protected void showYearLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(User_infromation.this);
        builder.setTitle("请选择年份");
        Calendar c = Calendar.getInstance();
        int getyear = c.get(Calendar.YEAR);
        final String[] year = { String.valueOf(getyear-3), String.valueOf(getyear-2),String.valueOf(getyear-1),String.valueOf(getyear)};
        builder.setSingleChoiceItems(year, 1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(User_infromation.this, "年份为：" + year[which], Toast.LENGTH_SHORT).show();
                mYear = year[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                user_year.setText(mYear);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(User_infromation.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                User_infromation.this.user_birth.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    public void update(String get_result) throws JSONException {
        JSONObject jsonObject = new JSONObject(get_result);
        if(jsonObject.has("result")) {
            result = jsonObject.getString("result");
            switch (result){
                case "1":
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            String nikename = jsonObject.getString("nikename");
            String birth = jsonObject.getString("birth");
            String year = jsonObject.getString("year");
            String intro = jsonObject.getString("intro");
            boolean sex = jsonObject.getBoolean("sex");
            user_nikename.setText(nikename);
            user_intro.setText(intro);
            user_year.setText(year);
            user_birth.setText(birth);
            if (sex == true) {
                user_sex.setText("男");
            }else {
                user_sex.setText("女");
            }
        }
    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new GetPostAysncTaskLoader(this, GET_USER_URL);
        }else if(id == 2){
            return new EditorUserAysncTaskLoader(this,mid,Uname,Uyear,Ubirth,Uintro,Usex,EDITOR_URL);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}

