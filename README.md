# 项目介绍

## 简单介绍
基于spring cloud微服务架构，框架使用SSM（spring+spring mvc+mybatis），默认使用数据库为mysql(用户可根据实际情况选择其他数据库)


## 项目结构

### 整体结构图

````
    |-- doc
        |-- db.sql
    |-- src
        |-- main
            |-- java
            |-- resources
        |-- test
    |-- pom.xml
    |-- README.md
````

### 整体目录结构说明

- `doc`：存放项目的文档，如数据库脚本 `db.sql` 或者其他文档
- `src`：存放项目源码
  - `main`： 业务源码
    - `java`：java 源码目录
      - `common`：公共部分
      - `datasources`：数据源部分
      - `generation`：代码生成器部分
      - `sys`：业务逻辑部分
    - `resources`：配置文件目录
      - `mapper`： `mapper xml `目录
      - `application.properties`：基础配置文件，和 `application.yml `作用同等，同二选一或者两者兼容
      - `application.yml`：`spring boot/spring cloud` 格式的基础配置文件
      - `application-XXX.yml`:：不同环境下的配置文件，由基础配置文件`application.yml`通过 `spring.profiles.active `指明不同环境
      - `logback-spring.xml`：`logback` 日志配置文件
  - `test`：测试源码
- `pom.xml`：` maven` 配置文件
- `README.md`：本文件

### 业务目录结构

```
    |-- sys
        |-- controller
        |-- dao
        |-- entity
        |-- model
        |-- service
```

### 业务目录结构说明

- `controller` ：控制器，负责具体的业务模块流程的控制
- `service`：业务层，主要负责业务模块的逻辑应用设计
- `dao`：数据存储对象层，定义数据库操作
- `entity`： 数据库实体类包
- `model`：实体类的模型，一般是用来返回给前端用



## 脚手架 demo 启动

生成的脚手架附有一个简单的 demo ，demo 使用说明：

- 创建数据库，使用`doc/db.sql` 初始化数据，并修改 `application` 的数据库配置

- 如果使用 `eureka` 做服务发现，请先从 `moria平台-->基础设施-->注册中心`中获取 `eureka` 服务器的地址及用户名和密码，并按实际情况修改（或自己搭建一个` eureka server` 测试）

- 如果使用 `zipkin` 做链路追踪，请先从 `moria平台-->基础设施-->zipkin`中获取`zipkin` 的服务地址，并按实际情况修改（或自己搭建一个 `zipkin server` 测试）。

  ​

demo 提供一个增删改查的简单例子，此例子遵循`restful api`规范：

### 添加用户

`POST`    ` <http:>//<ip>:<port>/<项目名>/sys/users`

入参例子：（参数`location`：`body`，`content-type →application/json;charset=UTF-8`）：

```json
{
	"username":"zhangshan",
	"password":"123456",
	"deptId":1,
	"mobile":"13574565466",
	"email":"56775@qq.com",
	"salt":"3474",
	"status":1
}
```

接口返回：

```json
{
    "msg": "保存成功",
    "code": 0
}
```



### 更新用户

`PATCH`    `<http:>//<ip>:<port>/<项目名>/sys/users`

入参例子：（参数`location`：`body`，`content-type →application/json;charset=UTF-8`）：

```json
{
	"userId":2,
	"username":"test"
}
```

接口返回：

```json
{
    "msg": "更新成功",
    "code": 0
}
```



### 删除用户

`DELETE`   `<http:>//<ip>:<port>/<项目名>/sys/users`

入参例子： 删除`userId=2和userId=3`（参数`location`：`body`，`content-type →application/json;charset=UTF-8`）：

```json
[2,3]
```

接口返回：

```json
{
    "msg": "删除成功",
    "code": 0
}
```



### 查询用户列表

`GET`    ` <http:>//<ip>:<port>/<项目名>/sys/users`

入参例子：`localhost:8080/sys/users?limit=10&page=1&username=li&status=1&orderValue=user_id&order=desc`

入参说明：

| ATTRIBUTE    | DESCRIPTION    |
| ------------ | -------------- |
| `limit`      | 每页记录数          |
| `page`       | 当前页数           |
| `orderValue` | 排序的字段          |
| `order`      | 排序方式(asc/desc) |
| `username`   | 模糊查询名字         |
| `status`     | 状态             |
| ...          | ...            |

接口返回：

```json
{
    "code": 0,
    "page": {
        "totalCount": 2,//总记录数
        "pageSize": 10,//每页记录数
        "totalPage": 1,//总页数
        "currPage": 1,//当前页
        "list": [//返回结果集
            {
                "userId": 4,
                "username": "li",
                "password": "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92",
                "salt": "hhq1EWhHBaO8aCaHBj2L",
                "email": "56775@qq.com",
                "mobile": "13574565466",
                "status": 1,
                "roleIdList": null,
                "createUserId": null,
                "createTime": "2018-07-16 15:39:25",
                "deptId": 1,
                "deptName": null
            },
            {
                "userId": 7,
                "username": "liNing",
                "password": "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92",
                "salt": "Mv3pRC48BQvpyUVJLFpm",
                "email": "56775@qq.com",
                "mobile": "13574565466",
                "status": 1,
                "roleIdList": null,
                "createUserId": null,
                "createTime": "2018-07-16 15:57:55",
                "deptId": 1,
                "deptName": null
            }
        ]
    }
}
```





### 查询用户

`GET`    `<http:>//<ip>:<port>/<项目名>/sys/users/{userId}`

入参例子：`http://localhost:8080/sys/users/1`

接口返回：

```json
{
    "code": 0,
    "user": {
        "userId": 1,
        "username": "Aaron",
        "password": "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92",
        "salt": "BmeSM17gUqxMF7lf0utj",
        "email": "3434@qq.com",
        "mobile": "13578965466",
        "status": 1,
        "roleIdList": null,
        "createUserId": null,
        "createTime": "2018-07-07 11:51:38",
        "deptId": 1,
        "deptName": null
    }
}
```

​    

## 代码生成

### 获取数据库所有表数据

`GET`    `<http:>//<ip>:<port>/<项目名>/generator/`

入参例子 `http://localhost:8080/moria/generator/`

接口返回：

```json
{
    "code":0,
    "page":{
        "totalCount":3,
        "pageSize":10,
        "totalPage":1,
        "currPage":1,
        "list":[
            {
                "engine":"InnoDB",
                "createTime":"2018-07-24 11:38:17",
                "tableComment":"商品表",
                "tableName":"goods"
            },
            {
                "engine":"InnoDB",
                "createTime":"2018-07-07 11:46:35",
                "tableComment":"系统用户",
                "tableName":"sys_user"
            },
            {
                "engine":"InnoDB",
                "createTime":"2018-07-06 10:53:00",
                "tableComment":"用户",
                "tableName":"tb_user"
            }
        ]
    }
}
```



### 生成代码

`GET`    `<http:>//<ip>:<port>/<项目名>/generator/code`

入参例子：`http://localhost:8080/moria/generator/code?tables=goods&mainPath=com.my&package=com.my.core&moduleName=goods`

入参说明：




| ATTRIBUTE      | DESCRIPTION                           |
| -------------- | ------------------------------------- |
| `tables`*必填* | 要生成代码的相关表                    |
| `mainPath`     | 主路径/根路径（不填入默认为com.foreveross）      |
| `package`      | 包名（不填入默认为com.foreveross.core）     |
| `moduleName`   | 模块名(不填入默认为sys)               |



