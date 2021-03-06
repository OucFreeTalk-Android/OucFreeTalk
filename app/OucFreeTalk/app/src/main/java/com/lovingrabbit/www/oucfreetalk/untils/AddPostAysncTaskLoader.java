package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

/**
 * Created by Zzzzzzzjk on 2017/11/2.
 */

public class AddPostAysncTaskLoader extends AsyncTaskLoader<String> {
    String mTitle,mCont,mUrl,result,mId;
    public AddPostAysncTaskLoader(Context context,String id,String title,String cont,String url) {
        super(context);
        mId = id;
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
        String user = httpQutils.addPostJson(mId,mTitle,mCont);
        try {
            result = httpQutils.connect_post(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
