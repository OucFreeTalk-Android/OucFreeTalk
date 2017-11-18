package com.lovingrabbit.www.oucfreetalk.untils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Zzzzzzzjk on 2017/8/11.
 */

public class HttpQutils {
    OkHttpClient okHttpClient;
    public HttpQutils()  {
        okHttpClient = new OkHttpClient();
    }

    public static final MediaType JSON
            = MediaType.parse("application/json;charset=utf-8");

    public String connect_post(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String result = response.body().string();
        return result;
    }
    public String connect_get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String result = response.body().string();
        return result;
    }
    public String loginJson(String username, String password) {
        return "{\"account\": \"" + username + "\"," + "\"password\": \"" + password + "\"}";
    }
    public String register(String username,String password,String nikename){
        boolean sex = false;
        String introduce = "个人介绍";
        return "{\"id\": \"" + username + "\"," + "\"password\": \"" + password + "\","  + "\"nikename\": \"" + nikename + "\","
                + "\"introduce\": \"" + introduce + "\","+ "\"sex\": " + sex +"}";
    }
    public String addPostJson(String id,String title, String context) {
        int pclass = 1;
        return "{\"id\":\"" + id + "\",\"title\": \"" + title + "\"," + "\"content\": \"" + context + "\","+ "\"pclass\": " + pclass +"}";
    }
    public String addCommentJson(String id,int postid,String context) {
        Log.e("json:", "{\"id\":\"" + id + "\",\"context\": \"" + context + "\","+ "\"postid\": " + postid +"}");
        return "{\"id\":\"" + id + "\",\"context\": \"" + context + "\","+ "\"postid\": " + postid +"}";
    }
    public String addReplyJson(String id,String replyId,int position,String content) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("commentId",position);
        jsonObject.put("replyid",replyId);
        jsonObject.put("context",content);

        return jsonObject.toString();
    }
    public String EditorJson(String id,String name ,String birth ,String year,String intro ,boolean sex,String pic) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("nikename",name);
        jsonObject.put("sex",sex);
        jsonObject.put("birth",birth);
        jsonObject.put("year",year);
        jsonObject.put("intro",intro);
        jsonObject.put("pic",pic);
        jsonObject.put("family","");
        jsonObject.put("ifname",true);
        jsonObject.put("ifsex",true);
        jsonObject.put("ifmobile",true);
        jsonObject.put("ifemail",true);
        Log.e("Json",jsonObject.toString() );
        return jsonObject.toString();
    }
    public String AddFocusJson(String id,String target) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("target",target);
        Log.e("Json",jsonObject.toString() );
        return jsonObject.toString();
    }
    public String AddMessageJson(String id,String recieveid,String content) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("receiveid",recieveid);
        jsonObject.put("content",content);
        return jsonObject.toString();
    }
}
