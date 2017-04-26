package com.lovingrabbit.www.oucfreetalk.talkadapter;

/**
 * Created by Zzzzzzzjk on 2017/4/26.
 */

public class Talk {
    int people_icon,isimg;

    String article_tile,article_tag,article_content;

    public Talk(int People_icon,int Isimg,String Article_title,
                String Article_tag,String Article_content){
        people_icon = People_icon;
        isimg = Isimg;
        article_tile = Article_title;
        article_tag = Article_tag;
        article_content = Article_content;
    }
    public Talk(int People_icon,String Article_title,
                String Article_tag,String Article_content){
        people_icon = People_icon;
        isimg = 0;
        article_tile = Article_title;
        article_tag = Article_tag;
        article_content = Article_content;
    }

    public int getIsimg() {
        return isimg;
    }

    public int getPeople_icon() {
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
