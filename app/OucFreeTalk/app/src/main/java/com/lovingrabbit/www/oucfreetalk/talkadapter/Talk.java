package com.lovingrabbit.www.oucfreetalk.talkadapter;

/**
 * Created by Zzzzzzzjk on 2017/4/26.
 */

public class Talk {
    int isimg,id,realbody;

    String article_tile,article_tag,article_content,owner,time,people_icon;

    public Talk(String People_icon,int Isimg,String Article_title,
                String Article_tag,String Article_content,int mId,String mOwner,String mTime,int mRealbody){
        people_icon = People_icon;
        isimg = Isimg;
        article_tile = Article_title;
        article_tag = Article_tag;
        article_content = Article_content;
        id = mId;
        owner = mOwner;
        time = mTime;
        realbody = mRealbody;
    }


    public int getRealbody() {
        return realbody;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getTime() {
        return time;
    }

    public int getIsimg() {
        return isimg;
    }

    public String getPeople_icon() {
        return people_icon;
    }

    public String getArticle_content() {
        return article_content;
    }

    public String getArticle_tag() {
        return article_tag;
    }

    public String getArticle_tile() {
        return article_tile;
    }

}
