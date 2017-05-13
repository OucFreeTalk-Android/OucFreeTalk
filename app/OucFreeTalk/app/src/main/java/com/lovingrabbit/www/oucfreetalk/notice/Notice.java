package com.lovingrabbit.www.oucfreetalk.notice;

/**
 * Created by zjk on 2017/5/13.
 */

public class Notice {
    String del = "删除";
    String top = "置顶";
    String text;
    public Notice(String Text){
        text = Text;
    }

    public String getDel() {
        return del;
    }

    public String getText() {
        return text;
    }

    public String getTop() {
        return top;
    }
}
