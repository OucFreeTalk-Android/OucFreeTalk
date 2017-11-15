package com.lovingrabbit.www.oucfreetalk.detailAdapter;

/**
 * Created by zjk on 2017/11/11.
 */

public class Detail {
    String username,time,content,id,ownerID,replyId,people_icon;
    int postlocation,postcID,realbody;
    public Detail(String nikename,String createtime,String mid,String contenttext,String icon,int position,int postcid,int body){
        username = nikename;
        time = createtime;
        id = mid;
        content =contenttext;
        people_icon = icon;
        postlocation = position;
        postcID = postcid;
        realbody = body;
    }
    public Detail(String nikename,String createtime,String ownerid,String mid,String contenttext,String icon,String replyid){
        username = nikename;
        time = createtime;
        content =contenttext;
        people_icon = icon;
        ownerID = ownerid;
        id = mid;
        replyId =replyid;
    }

    public String getReplyId() {
        return replyId;
    }

    public int getRealbody() {
        return realbody;
    }

    public String getOwnerID() {
        return ownerID;
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

    public String getPeople_icon() {
        return people_icon;
    }

    public int getPostlocation() {
        return postlocation;
    }
}
