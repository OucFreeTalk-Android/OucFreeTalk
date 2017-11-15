package com.lovingrabbit.www.oucfreetalk.personAdapter;

/**
 * Created by zjk on 2017/11/11.
 */

public class Person {
    int isimg,id,realbody,focus,befocus;

    String people_icon,article_tile,nikename,article_content,owner,time,intro;

    public Person(String People_icon,int Isimg,String Article_title,
                String name,String Article_content,int mId,String mOwner,
                  String mTime,String introd,int Realbody,int Focus,int Befocus){
        people_icon = People_icon;
        isimg = Isimg;
        article_tile = Article_title;
        nikename = name;
        article_content = Article_content;
        id = mId;
        realbody =Realbody;
        owner = mOwner;
        time = mTime;
        intro = introd;
        befocus = Befocus;
        focus =Focus;
    }

    public int getFocus() {
        return focus;
    }

    public int getBefocus() {
        return befocus;
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

    public String getIntro() {
        return intro;
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

    public String getNikename() {
        return nikename;
    }

    public String getArticle_tile() {
        return article_tile;
    }

}
