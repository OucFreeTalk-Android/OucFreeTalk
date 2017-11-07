# FriendsController介绍

## 总体介绍

本控制器包含了跟好友功能有关的接口

## 接口总览

|接口|方法|数据|返回|作用|
|:-------|:-------|:-------|:-------|:-------|
|/api/Friends/MyFocus|Get|null|Json|获取我的关注列表|
|/api/Friends/FocusMe|Get|null|Json|获取我的粉丝列表|
|/api/Friends/YouMyFriend|Post|Json|Json|添加关注|
|/api/Friends/YouNotFriend|Post|Json|Json|取消关注|

## 接口详情

### /api/Friends/MyFocus

方法 : Get

返回 : 

1.关注列表 `[{"ifname":false,"name":null,"focus":"14020031094","befocus":"14020031091","createtime":"2017-05-06T12:29:22.34","nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex":false,"exp":0},{"ifname":false,"name":null,"focus":"14020031094","befocus":"14020031092","createtime":"2017-05-06T12:30:42.25","nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex":false,"exp":0}]`

    注意，如果填了保密的情况传回来的结果就为null

2.消息 `{result:代码}`

|代码|原因|
|:-----|:-----|
|0|未登录|
|2|session错误|
|3|服务器错误|
|1|返回成功，关注列表为空|


### /api/Friends/FocusMe

方法 : Get

返回 : 

1.关注列表 `[{"ifname":false,"name":null,"focus":"14020031094","befocus":"14020031091","createtime":"2017-05-06T12:29:22.34","nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex":false,"exp":0},{"ifname":false,"name":null,"focus":"14020031094","befocus":"14020031092","createtime":"2017-05-06T12:30:42.25","nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex":false,"exp":0}]`

    注意，如果填了保密的情况传回来的结果就为null

2.消息 `{result:代码}`

|代码|原因|
|:-----|:-----|
|0|未登录|
|2|session错误|
|3|服务器错误|
|1|返回成功，关注列表为空|

### /api/Friends/YouMyFriend

方法 : Post

数据 : `{"target": 目标id}`

返回 : `{"result": 代码}`

|代码|原因|
|:-----|:-----|
|0|未登录|
|1|成功|
|2|好友已存在|
|3|服务器错误|
|4|目标id不存在|
|5|同名错误|

### /api/Friends/YouNotFriend

方法 : Post

数据 : `{"target": 目标id}`

返回 : `{"result": 代码}`

|代码|原因|
|:-----|:-----|
|0|未登录|
|1|成功|
|2|好友关系不存在|
|3|服务器错误|
|4|目标id不存在|
|5|同名错误|

