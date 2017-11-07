#srdp接口文档

## 开篇通用功能提示
传时间给后台请使用这段js代码将时间序列化并写进post表单
`var str = "" + date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay() + "T" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();`

##功能划分

###验证码功能(VerificationController)

#### 接口
|接口|方法|数据|返回|作用|
|:-------|:-------|:-------|:-------|:-------|
|/api/Verification/GetVer|get|无|验证码数据|验证码显示|
|/api/Verification/Match|Post|json|json|匹配输入的验证码是否正确|

#### 详细
##### /api/Verification/GetVer
使用时将该接口置于img标签中，如
`<img src="/api/Verification/GetVer">`

##### /api/Verification/Match
方法 POST

数据：`data:{"str":验证码}`

返回值:`{"result":代码}`

|代码|含义|
|:------|:------|
|1|匹配成功|
|2|匹配失败|

### 用户功能(UsersController)

|接口|方法|数据|返回|作用|
|:-------|:-------|:-------|:-------|:-------|
|/api/Users/Register|post|json|json|用户注册|
|/api/Users/Editor|post|json|json|用户数据更新|
|/api/Users/Myinformation|Get|无|json|获取我的各种信息(全获取)|
|/api/Users/Othersinformation|Get|json|json|获取指定id的用户信息(根据筛选值自动过滤)|



#### 详细

##### /api/Users/Register

方法： POST

数据样例 `data: {
                        "id" : "14020031094",
                        "password": "140200031094",
                        "nikename": "黑猫回收者",
                        "name":"王晓瑞",
                        "sex": false,
                        "birth": date.valueOf(),
                        "year": "2014",
                        "family": "哇哇哇哇啊哇",
                        "pic":"\example\"
                    }`

返回 `{ "result" : 代码}`

|代码|含义|
|:------|:------|
|0|失败，原因可能是网络问题或者服务器出错|
|1|成功|
|2|失败，该账户已存在（学号存在）|

##### /api/Users/Editor

方法 : Post

数据样例 `data: {
                        "id" : "14020031094",
                        "nikename": "黑猫回收者",
                        "sex": false,
                        "birth": str,
                        "year": "2014",
                        "family": "哇哇哇哇啊哇",
                        "pic": "1111111111",
                        "ifname":true,
                        "ifsex": true,
                        "ifbirth": true,
                        "ifmobile": true,
                        "ifemail":true
                    }`

返回 `{ "result" : 代码}`

|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|1|成功|
|2|失败，post表单中id与session中id不同,多见于装逼修改js文件|
|3|失败，没有此用户|
|4|失败，服务器或数据库故障|

##### /api/Users/Myinformation

方法 ： GET

数据： 无，由服务器自动检查session中存储的id值进行查询

返回：
成功情况 `{"id":"14020031094","name":"王晓瑞","ifname":false,"nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex"
:true,"birth":"2000-02-03T00:00:00","year":"2014","email":null,"ifemail":true,"mobile":null,"ifmobile"
:true,"exp":0,"family":"哇哇哇哇啊哇","ifbirth":true,"accountlevel":1}`

失败情况 `{"result":代码}` 

|代码|含义|
|:------|:------|
|0|失败，该用户未登录|
|2|失败，服务器或数据库出问题|

##### /api/Users/Othersinformation

方法 ： GET

数据： `data:{
                    "id": "14020031094"
                }`

返回：
成功情况 `{"id":"14020031094","name":null,"ifname":false,"nikename":"黑猫回收者","pic":"1111111111","sex":false,"ifsex"
:true,"birth":"2000-02-03T00:00:00","year":"2014","email":null,"ifemail":true,"mobile":null,"ifmobile"
:true,"exp":0,"family":"哇哇哇哇啊哇","ifbirth":true,"accountlevel":1}`

>>说明：和上面一样的数据，但是因为ifname为false所以把name直接过滤掉不显示

失败情况 `{"result":代码}`

|代码|含义|
|:------|:------|
|0|失败，数据库或服务器错误|


### 登录功能(UsersController)

|接口|方法|数据|返回|作用|
|:-------|:-------|:-------|:-------|:-------|
|/api/Log/Login|post|json|json|用户登录|
|/api/Log/Logout|get|无|json|用户注销|

#### 详情

##### /api/Log/Login

方法 : Post

数据样例 `data: {
                    "account": "14020031094",
                    "password": "140200031094",
                }`

返回 `{ "result" : 代码}`

|代码|含义|
|:------|:------|
|0|失败，该用户名不存在|
|1|成功|
|2|失败，密码错误|

##### /api/Log/Logout

方法 : GET

返回 `{ "result" : 代码}`

|代码|含义|
|:------|:------|
|1|注销成功|

