# API文档总览

## api介绍
|控制器|目录|作用|
|:----|:----:|:-----:|
|Log|/api/Log/|登录注销注册一些事项|
|Users|/api/Users|用户个人信息更改查看|
|Friends|/api/Friends|关注和粉丝列表的查看和增删|
|Posts|/api/Friends|帖子的增删查改|
|Verification|/api/Verification|验证码相关|

## 通用功能提示
传时间给后台请使用这段js代码将时间序列化并写进post表单
`var str = "" + date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay() + "T" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();`