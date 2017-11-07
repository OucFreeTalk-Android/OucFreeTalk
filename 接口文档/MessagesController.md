# MessagesController介绍

## 总体介绍

本控制器包含了跟帖子功能有关的接口

## 接口总览

|接口|方法|数据|返回|作用|
|:-----:|:-----|:-----|:-----|:-----|
|/api/Messages/getMyNotice|Get|Josn|Json|获取我的通知|

## 接口详情

### /api/Messages/getMyNotice

方法：Get

数据
```
{
    "index":20, //页码
    "nclass": 1, // 1-帖子被人评论  2-评论被人回复 3-回复被人回复 -1-所有
}
```

返回
```
{
    "search":[{
        "id": 1 ,   //提醒表的id
        "noticeclass": 1 ,  //提醒分类，见上面除了-1
        "postid":1,     //被回复的帖子id
        "createtime":"2000-02-03T00:00:00",
        "posttitle":"ad损失达",    //被回复的帖子标题
        "commentsid":1, //被回复的楼层id class=1无
        "commenttext":"回复你",    //被回复楼层的内容
        "replyid":1,    //被回复的回复id class=1，2 无
        "replytext"："回复你",  //回复内容
        "replystuid":"14020031094", //回复人id
        "nikename":"黑猫回收者", //回复人昵称
        "pic":"/img/1.png"  //回复人头像
        }],
    "allpage":1
}
```