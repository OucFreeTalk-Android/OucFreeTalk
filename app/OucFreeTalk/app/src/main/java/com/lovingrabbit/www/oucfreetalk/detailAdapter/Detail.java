package com.lovingrabbit.www.oucfreetalk.detailAdapter;

/**
 * Created by zjk on 2017/11/11.
 */

public class Detail {
    String username,time,content,id;
    int people_icon,postlocation,postcID;
    public Detail(String nikename,String createtime,String mid,String contenttext,int icon,int position,int postcid){
        username = nikename;
        time = createtime;
        id = mid;
        content =contenttext;
        people_icon = icon;
        postlocation = position;
        postcID = postcid;
    }
    public Detail(String nikename,String createtime,String contenttext,int icon){
        username = nikename;
        time = createtime;
        content =contenttext;
        people_icon = icon;
    }

    public int getPostcID() {
        return postcID;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getPeople_icon() {
        return people_icon;
    }

    public int getPostlocation() {
        return postlocation;
    }
}
