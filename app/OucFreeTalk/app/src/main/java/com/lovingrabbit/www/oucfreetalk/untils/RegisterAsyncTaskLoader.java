package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

/**
 * Created by zjk on 2017/10/31.
 */

public class RegisterAsyncTaskLoader extends AsyncTaskLoader<String> {
    String username,password,mUrl,result,userId;
    public RegisterAsyncTaskLoader(Context context,String id,String passwd,String name,String url) {
        super(context);
        userId = id;
        password = passwd;
        username = name;
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        HttpQutils httpQutils = new HttpQutils();
        String user = httpQutils.register(userId,password,username);
        try {
            result = httpQutils.connect_post(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
