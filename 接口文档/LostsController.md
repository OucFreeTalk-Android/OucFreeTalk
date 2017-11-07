# LostsController介绍

## 总体介绍

本控制器包含了跟找回功能有关的接口

## 接口总览

|接口|方法|数据|返回|作用|
|:-----:|:-----|:-----|:-----|:-----|
|/api/Losts/addLost|Post|Josn|Json|添加新的失物招领|
|/api/Losts/getMyLost|get|Josn|Json|获取我的失物招领|
|/api/Losts/IGotIt|Post|Josn|Json|找到确定|

## 接口详情

### /api/Losts/addLost

方法：Post

数据
```
{
    "sid": "14020031094",//丢失人id
    "area":"北区",
    "SecArea":"你自己看着填吧",
    "name":"鬼知道你要什么名字"
}
```

返回`{"result":代码}`

|代码|含义|
|:------|:------|
|1|成功|
|5|失败，服务器未保存|
|3|失败，服务器错误|

### /api/Losts/getMyLost

方法：get

数据
```
{
    "index": 1,//页码

}
```

返回
```
{
    "search":[{
        "stuid":"xxx",
        "area":"xxx",
        "secarea":"xxxx",
        "name":"xxxx",
        "createtime":"2000-02-03T00:00:00",
        "state":true
        }],
    "allpage":20
}
```

`{"result":代码}`

|代码|含义|
|:------|:------|
|0|失败,未登录|

### /api/Losts/IGotIt

方法:post

数据
```
{
    "lostid": 1,//失物招领表的id

}
```

返回`{"result":代码}`

|代码|含义|
|:------|:------|
|0|失败,未登录|
|1|成功|
|2|失败，失物招领不存在|
|3|失败，服务器错误|
|4|失败，失物招领不是你的|
|5|失败，服务器未保存|
