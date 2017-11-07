# VerificationController介绍

## 总体介绍
本控制器包含了跟验证码相关的一些接口

## 接口总览
|接口|方法|数据|返回|作用|
|:-------|:-------|:-------|:-------|:-------|
|/api/Verification/GetVer|get|无|验证码数据|验证码显示|
|/api/Verification/Match|Post|json|json|匹配输入的验证码是否正确|

## 接口详细介绍

### /api/Verification/GetVer
使用时将该接口置于img标签中，如
`<img src="/api/Verification/GetVer">`

### /api/Verification/Match
方法 POST

数据：`data:{"str":验证码}`

返回值:`{"result":代码}`

|代码|含义|
|:------|:------|
|1|匹配成功|
|2|匹配失败|
