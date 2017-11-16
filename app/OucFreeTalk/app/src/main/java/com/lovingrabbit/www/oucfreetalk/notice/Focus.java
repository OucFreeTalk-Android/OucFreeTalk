package com.lovingrabbit.www.oucfreetalk.notice;

/**
 * Created by zjk on 2017/11/16.
 */

public class Focus {
    String id,nikename,pic,time,intro,content;
    public Focus(String mId,String mNikename,String mPic,String mIntro){
        id = mId;
        nikename = mNikename;
        pic = mPic;
        intro = mIntro;
    }

    public String getId() {
        return id;
    }

    public String getNikename() {
        return nikename;
    }

    public String getPic() {
        return pic;
    }

    public String getTime() {
        return time;
    }

    public String getIntro() {
        return intro;
    }

    public String getContent() {
        return content;
    }
}
