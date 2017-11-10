package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/10.
 */

public class GetPostAysncTaskLoader extends AsyncTaskLoader<String> {
    String mUrl,result;

    public GetPostAysncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        HttpQutils httpQutils = new HttpQutils();
        try {
            result = httpQutils.connect_get(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
