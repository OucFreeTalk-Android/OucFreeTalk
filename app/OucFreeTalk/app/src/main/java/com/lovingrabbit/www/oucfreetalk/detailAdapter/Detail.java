package com.lovingrabbit.www.oucfreetalk.detailAdapter;

/**
 * Created by zjk on 2017/11/11.
 */

public class Detail {
    String username,time,content;
    int people_icon;
    public Detail(String nikename,String createtime,String contenttext,int icon){
        username = nikename;
        time = createtime;
        content =contenttext;
        people_icon = icon;

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

}
