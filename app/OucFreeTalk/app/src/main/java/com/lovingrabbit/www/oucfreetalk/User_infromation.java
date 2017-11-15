package com.lovingrabbit.www.oucfreetalk;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lovingrabbit.www.oucfreetalk.detailAdapter.Detail;
import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.untils.EditorUserAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;
import com.lovingrabbit.www.oucfreetalk.untils.UploadFile;
import com.lovingrabbit.www.oucfreetalk.untils.UploadUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User_infromation extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText user_birth,user_sex,user_nikename,user_year,user_intro;
    String mSex,mYear,result="";
    String mid,pic;
    String Usex,Ubirth,Uname,Uyear,Uintro;
    String path;
    ImageView usrImg;
    Uri originalUri;
    LoaderManager loaderManager;
    String GET_USER_URL = "http://47.93.222.179/oucfreetalk/getUser?id=";
    String EDITOR_URL = "http://47.93.222.179/oucfreetalk/editor";
    String UPLOAD_URL = "http://47.93.222.179/oucfreetalk/upload";
    String IMG = "http://47.93.222.179/oucfreetalk/img/";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        if(data == null)
            return;

        try {
            originalUri = data.getData(); // 获得图片的uri

            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // 显得到bitmap图片

            // 这里开始的第二部分，获取图片的路径：

            String[] proj = { MediaStore.Images.Media.DATA };

            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径

            path = cursor.getString(column_index);

            if (!path.equals("")) {
                loaderManager = getLoaderManager();
                loaderManager.initLoader(3, null, User_infromation.this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 103)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

                //权限被授予
                choosePhoto();

            } else
            {
                // Permission Denied
                Toast.makeText(User_infromation.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPerm() {
        /**1.在AndroidManifest文件中添加需要的权限。
         *
         * 2.检查权限
         *这里涉及到一个API，ContextCompat.checkSelfPermission，
         * 主要用于检测某个权限是否已经被授予，方法返回值为PackageManager.PERMISSION_DENIED
         * 或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
         * */
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){ //权限没有被授予

            /**3.申请授权
             * @param
             *  @param activity The target activity.（Activity|Fragment、）
             * @param permissions The requested permissions.（权限字符串数组）
             * @param requestCode Application specific request code to match with a result（int型申请码）
             *    reported to {@link OnRequestPermissionsResultCallback#onRequestPermissionsResult(
             *    int, String[], int[])}.
             * */
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    103);


        }else{//权限被授予
            choosePhoto();

            //直接操作
        }

    }
    public void choosePhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 103);
    }

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
        usrImg = (ImageView) findViewById(R.id.user_icon_img);
        usrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPerm();
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
        final String[] sex = {"男", "女"};
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
        }else if (jsonObject.has("upload")){
            pic = jsonObject.getString("upload");
            Toast.makeText(this,"上传成功",Toast.LENGTH_SHORT).show();
            Glide.with(this).load(IMG+pic).into(usrImg);
        }
        else {
            String nikename = jsonObject.getString("nikename");
            String birth = jsonObject.getString("birth");
            String year = jsonObject.getString("year");
            String intro = jsonObject.getString("intro");
            boolean sex = jsonObject.getBoolean("sex");
            pic = jsonObject.getString("pic");
            user_nikename.setText(nikename);
            user_intro.setText(intro);
            user_year.setText(year);
            user_birth.setText(birth);
            if (pic.equals("pic")){
                usrImg.setImageResource(R.drawable.nav_icon);
            }else {
                Glide.with(this).load(IMG + pic).into(usrImg);
            }
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
            return new EditorUserAysncTaskLoader(this,mid,Uname,Uyear,Ubirth,Uintro,Usex,pic,EDITOR_URL);
        }else if(id == 3){
            return new UploadFile(this,path,UPLOAD_URL);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.e("upload", data );
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

