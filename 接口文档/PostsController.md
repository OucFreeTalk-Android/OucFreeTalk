# PostsController介绍

## 总体介绍

本控制器包含了跟帖子功能有关的接口

## 接口总览

|接口|方法|数据|返回|作用|
|:-----:|:-----|:-----|:-----|:-----|
|/api/Posts/addPost|Post|Josn|Json|用户添加一个新的主题贴|
|/api/Posts/addcomments|Post|Json|Json|添加一个楼层|
|/api/Posts/addreply|Post|Json|Json|添加一条回复|
|/api/Posts/getPosts|Get|Json|Json|获取指定页的帖子|
|/api/Posts/getPost|Get|Json|Json|获取帖子信息|
|/api/Posts/getComment|Get|Json|Json|获取回复信息|
|/api/Posts/delPost|Post|Json|Json|删除一个帖子|
|/api/Posts/delComment|Post|Json|Json|删除一个评论|
|/api/Posts/delReply|Post|Json|Json|删除一个评论|
|/api/Posts/addClass|Post|Json|Json|添加一个板块|
|/api/Posts/editorClass|Post|Json|Json|修改一个板块的名字|
|/api/Posts/deleteClass|Post|Json|Json|删除一个板块|
|/api/Posts/getClasses|Get|null|Json|获取板块|


## 接口详情

### /api/Posts/addPost

方法 POST

数据
```
{
	"id":"13020031001",
	"title":"nbhaha",
	"content":"分手大师",
	"pclass":1
}

```
返回
`{result:代码`

|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，服务器未保存|
|3|失败，服务器错误|

### /addcomments

方法:Post

数据
```
{
   "id":"13020031001",
   "postid":1,
   "context":"fdfghfghfghgdfgd"
}
```

返回
`{result:代码`

|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，服务器未保存|
|3|失败，服务器错误|
|4|失败，不存在该帖子|
|6|成功，但是提醒没发出去|

### /addreply

方法 ：POST

数据：
```
{
	"id":"14020031127",
	"commentId":1,
	"replyid":"123123123",
	"context":"hahahaha"
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，服务器未保存|
|3|失败，服务器错误|
|4|失败，不存在该帖子|
|6|成功，但是提醒没发出去|

### /getPosts?pclass=1&index=1

方法：get

数据:
```
{
    "pclass":1 ,//帖子的版块，-1为全部
    "index":1 //帖子的页数
}
```

返回：
```
{
    "search":[{
        "id":1,
        "ownclass":1,
        "owner":"14020031094"
        "relbody":12,
        "title":"惨死",
        "contenttext":"我是一楼",
        "createtime":"2000-02-03T00:00:00",
        "ownername":"黑猫回收者",
        "ownerpic":"\img\ico.png"
        }],
    "allpage":20
}
```

`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，没有该板块|


###
### getPost?postid=1&index=1

方法：get

数据：
```
{
    "postid":1 ,//帖子的id
    "index":1 //帖子的页数
}
```

返回：
```
{
    "search":[{
        "commentid":1,
        "stuid":"14020031094",
        "postlocation":12,              //当前楼数
        "commentcontext":"我是一楼",
        "createtime":"2000-02-03T00:00:00",
        "nikename":"黑猫回收者",
        "ico":"\img\ico.png"
        }],
    "allpage":20
}
```

`{"result":代码}`
|代码|含义|
|:------|:------|
|2|失败，没有该帖子|

### /getComment?commentid=1&index=1

方法：get

数据：
```
{
    "commentid":1 ,//楼层的id
    "index":1 //帖子的页数
}
```

返回：
```
{
    "search":[{
        "id":1,
        "stuid":"14020031094",
        "stunike":"黑猫回收者",
        "contenttext":"我就是要回复你",
        "createtime":"2000-02-03T00:00:00",
        "replyid":"14020031094",
        "replynike":"黑猫回收者", //被回复人
        }],
    "allpage":20
}
```

`{"result":代码}`
|代码|含义|
|:------|:------|
|2|失败，没有该帖子|

### /api/Posts/delPost

方法:post

数据：
```
{
    "postid":1 ,//楼层的id
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，不存在该帖子|
|5|成功，服务器保存错误|

### /api/Posts/delComment

方法:post

数据：
```
{
    "commentid":1 ,//楼层的id
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，不存在该楼层|
|5|成功，服务器保存错误|

### /api/Posts/delReply

方法:post

数据：
```
{
    "replyid":1 ,//回复的id
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，不存在该回复|
|5|成功，服务器保存错误|

### /api/Posts/addClass

方法:post

数据：
```
{
    "classname":"学业" ,//板块名
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，已存在该板块|
|5|成功，服务器保存错误|


### /api/Posts/editorClass

方法:post

数据：
```
{
    "classid":1      //板块id
    "newname":"学业" ,//新板名
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，该板块不存在|
|5|成功，服务器保存错误|

### /api/Posts/deleteClass

方法:post

数据：
```
{
    "classid":1      //板块id
}
```

返回
`{"result":代码}`
|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，权限不够|
|3|失败，服务器错误|
|4|失败，该板块不存在|
|5|成功，服务器保存错误|

### /api/Posts/getClasses

方法:get

返回
```
{
    "classes":[{
        "id":1,
        "name":"asdasd",
        state:true
        }]
}
```

`{"result":代码}`
|代码|含义|
|:------|:------|
|3|失败，服务器错误|
