package com.lovingrabbit.www.oucfreetalk.untils;

import org.json.JSONArray;
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
            = MediaType.parse("application/json; charset=utf-8");

    public String login(String url, String json) throws IOException {
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

    public String bolwingJson(String username, String password) {
        return "{'username':" + username + "," + "'password':" + password + "}";
    }

    //OKHttp 连接网络


    private void parseJson(String JSONData) {

        try {
            JSONObject jsonObject = new JSONObject(JSONData);
            JSONArray results = jsonObject.getJSONArray("result");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonResult = results.getJSONObject(i);
                int id = jsonResult.getInt("id");
//                    Log.d("MainActivity","id="+id);
                String name = jsonResult.getString("title");
//                    Log.d("MainActivity","name="+name);
                String img = "https://image.tmdb.org/t/p/w185" + jsonResult.getString("backdrop_path");
//                    Log.d("MainActivity","img="+img);
                String title = jsonResult.getString("original_title");
                //  Log.d("MainActivity","original_title="+title);
                String airticl = jsonResult.getString("overview");

                String date = jsonResult.getString("release_date");
                String vote = jsonResult.getString("vote_average");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
