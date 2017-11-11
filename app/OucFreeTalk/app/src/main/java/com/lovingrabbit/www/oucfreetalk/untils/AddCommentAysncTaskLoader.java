package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/11.
 */

public class AddCommentAysncTaskLoader extends AsyncTaskLoader<String> {
    String mCont,mUrl,result,mId;
    int postId;
    public AddCommentAysncTaskLoader(Context context, String id, int postid, String cont, String url) {
        super(context);
        mId = id;
        postId = postid;
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
        String user = httpQutils.addCommentJson(mId,postId,mCont);
        try {
            result = httpQutils.connect_post(mUrl,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
