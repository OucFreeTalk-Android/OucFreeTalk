package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.File;

/**
 * Created by zjk on 2017/11/15.
 */

public class UploadFile extends AsyncTaskLoader<String> {
    String url,path;
    public UploadFile(Context context,String mPath,String mUrl) {
        super(context);
        url = mUrl;
        path = mPath;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        UploadUtil uploadUtil = new UploadUtil();
        String result = uploadUtil.formUpload(url,path);
        return result;
    }
}
