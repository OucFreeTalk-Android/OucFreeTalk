package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/18.
 */

public class AddMessegeAsyncTaskLoader extends AsyncTaskLoader<String> {
    String mCont,mUrl,result,mId,mReceiveid;
    public AddMessegeAsyncTaskLoader(Context context, String id, String receiveid, String cont, String url) {
        super(context);
        mId = id;
        mReceiveid = receiveid;
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
        String user = null;
        try {
            user = httpQutils.AddMessageJson(mId,mReceiveid,mCont);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            result = httpQutils.connect_post(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
