package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/14.
 */

public class AddFocusAysncTaskLoader extends AsyncTaskLoader<String> {
    String mTarget,mUrl,result,mId;
    public AddFocusAysncTaskLoader(Context context, String id, String target, String url) {
        super(context);
        mId = id;
        mTarget = target;
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
            user = httpQutils.AddFocusJson(mId,mTarget);
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
