package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/12.
 */

public class AddReplyAsyncTaskLoader extends AsyncTaskLoader<String> {
    String mReplyid, mCont, mUrl, result, mId;
    int mPosition;

    public AddReplyAsyncTaskLoader(Context context, String id, String replyId, int position, String cont, String url) {
        super(context);
        mId = id;
        mReplyid = replyId;
        mPosition = position;
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
            user = httpQutils.addReplyJson(mId, mReplyid, mPosition, mCont);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            result = httpQutils.connect_post(mUrl, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
