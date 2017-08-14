package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.io.IOException;

/**
 * Created by Zzzzzzzjk on 2017/8/13.
 */

public class FreeTalkAsyncTaskLoader extends AsyncTaskLoader<String> {
    String username,password,mUrl,result;
    public FreeTalkAsyncTaskLoader(Context context,String name,String passwd,String url) {
        super(context);
        username = name;
        password = passwd;
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        HttpQutils httpQutils = new HttpQutils();
        String user = httpQutils.bolwingJson(username,password);
        try {
            result = httpQutils.login(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
