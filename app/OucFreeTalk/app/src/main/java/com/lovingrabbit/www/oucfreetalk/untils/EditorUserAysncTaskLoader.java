package com.lovingrabbit.www.oucfreetalk.untils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by zjk on 2017/11/12.
 */

public class EditorUserAysncTaskLoader extends AsyncTaskLoader<String> {
    String birth, year, name, intro,mUrl,result,id;
    boolean sex;

    public EditorUserAysncTaskLoader(Context context,String Id ,String Name, String Year, String Birth, String Intro,String Sex, String url) {
        super(context);
        id = Id;
        name = Name;
        year = Year;
        birth = Birth;
        intro = Intro;
        if (Sex.equals("男")){
            sex = true;
        }else {
            sex = false;
        }
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
            user = httpQutils.EditorJson(id, name, birth, year,intro,sex);
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
