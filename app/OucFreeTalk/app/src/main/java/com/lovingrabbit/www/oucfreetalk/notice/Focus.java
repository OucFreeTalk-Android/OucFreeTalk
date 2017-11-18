package com.lovingrabbit.www.oucfreetalk.notice;

/**
 * Created by zjk on 2017/11/16.
 */

public class Focus {
    int postid,commentid,noticeclass,postlocation,replyid;
    String id,nikename,pic,time,intro,title,postTime,context;
    public Focus(String mId,String mNikename,String mPic,String mIntro,String mTime){
        id = mId;
        nikename = mNikename;
        pic = mPic;
        intro = mIntro;
        time = mTime;
    }
    public Focus(String mId,String mNikename,String mPic,int mPostid,String mTime,int mclass,String mtitle,String pTime,String mContext){
        id = mId;
        nikename = mNikename;
        pic = mPic;
        postid = mPostid;
        time = mTime;
        noticeclass = mclass;
        title = mtitle;
        postTime = pTime;
        context = mContext;
    }
    public Focus(String mId,String mNikename,String mPic,String mTime,int mCommentid,int mclass,String pTime,String mContext,int position){
        id = mId;
        nikename = mNikename;
        pic = mPic;
        commentid = mCommentid;
        time = mTime;
        noticeclass = mclass;
        postTime = pTime;
        context = mContext;
        postlocation= position;
    }
    public Focus(String mId,String mNikename,String mPic,String mTime,int mReplyid,int mclass){
        id = mId;
        nikename = mNikename;
        pic = mPic;
        time = mTime;
        noticeclass = mclass;
        replyid = mReplyid;
    }

    public int getReplyid() {
        return replyid;
    }

    public String getTitle() {
        return title;
    }

    public String getPostTime() {
        return postTime;
    }

    public String getContext() {
        return context;
    }

    public int getNoticeclass() {
        return noticeclass;
    }

    public int getPostlocation() {
        return postlocation;
    }

    public int getPostid() {
        return postid;
    }

    public int getCommentid() {
        return commentid;
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

}
