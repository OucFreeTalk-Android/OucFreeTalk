package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

/**
 * Created by Zzzzzzzjk on 2017/11/2.
 */

public class AddPostAysncTaskLoader extends AsyncTaskLoader<String> {
    String mTitle,mCont,mUrl,result;
    public AddPostAysncTaskLoader(Context context,String title,String cont,String url) {
        super(context);
        mTitle = title;
        mCont = cont;
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        HttpQutils httpQutils = new HttpQutils();
        String user = httpQutils.addPostJson(mTitle,mCont);
        try {
            result = httpQutils.connect_post(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
