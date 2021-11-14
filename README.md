# techtrainingcamp-security-4-设计文档

---

# 问题描述


设计并开发一个登录注册系统，可以支持注册、登录和登出或注销的基本功能，另外也需要对一些黑灰产的用户，例如薅羊毛的这种异常用户，进行识别和制定风控策略进行不同程度的拦截和处罚。

# 需求


由于小组内成员没有后端基础(仅一人有JAVA后端基础)、学习时间不充分(课程和考试等)，因此我们仅仅实现了以下几个方面的需求。

-   用户通过网页前端进行注册、登录、登出、注销
-   用户在注册成功或登录成功后，一定时间内维持登录状态；在注销或登出或一定时间后后，结束登录状态。
-   服务端对于对于用户的行为进行评估，并返回风控评估结果。

# 项目概况


## 3.1 语言及框架

语言：Java

主要使用框架：Spring Boot、Spring MVC、MyBatis等

数据存储：Redis缓存，MySQL数据库。

## 3.2 项目目录

```
├─src
│  ├─main
│  │  ├─java
│  │  │  └─org
│  │  │      └─study
│  │  │          └─grabyou
│  │  │              │  GrabyouApplication.java —— Spring Boot 启动
│  │  │              │
│  │  │              ├─config
│  │  │              │      RiskControllConfig.java -- 风控配置类
│  │  │              │
│  │  │              ├─controller
│  │  │              │      ApplyController.java  --- 验证码controller
│  │  │              │      DeregisterController.java --- 注销controller
│  │  │              │      IndexController.java  --- 主页面+用户信息controller
│  │  │              │      LoginController.java  --- 登录controller
│  │  │              │      LogoutController.java  --- 登出controller
│  │  │              │      RegisterController.java  --- 注册controller
│  │  │              │
│  │  │              ├─dao
│  │  │              │      RedisDao.java  --- Redis 存取
│  │  │              │
│  │  │              ├─entity --- 实体包
│  │  │              │  │  ApplyBean.java --- （废除）验证码实体
│  │  │              │  │  BlackRecord.java  --- 黑名单实体
│  │  │              │  │  Event.java   --- 事件实体
│  │  │              │  │  Status.java  --- 状态接口
│  │  │              │  │  User.java   --- 用户接口
│  │  │              │  │
│  │  │              │  └─impl
│  │  │              │          ApplyStatus.java   --- 验证码状态实体
│  │  │              │          DeregisterStatus.java --- 注销状态实体
│  │  │              │          LoginStatus.java --- 登录状态实体
│  │  │              │          LogoutStatus.java --- 登出状态实体
│  │  │              │          RegisterStatus.java --- 注册状态实体
│  │  │              │
│  │  │              ├─enums
│  │  │              │      DimensionType.java  --- 维度列举
│  │  │              │      EventType.java  --- 事件列举
│  │  │              │      MessageEnum.java --- 消息列举
│  │  │              │      TimeRange.java --- 时间范围列举
│  │  │              │
│  │  │              ├─mapper
│  │  │              │      ApplyMapper.java ---（废除）验证码
│  │  │              │      BlackListMapper.java --- 黑名单
│  │  │              │      UserDao.java  --- 用户名
│  │  │              │
│  │  │              ├─service
│  │  │              │  │  ApplyService.java  --- （废除）验证码
│  │  │              │  │  IRiskControllService.java  --- 风险控制接口
│  │  │              │  │  UserAccessService.java --- 用户访问类
│  │  │              │  │
│  │  │              │  └─Impl
│  │  │              │          ApplyServiceImp.java  --- （废除）验证码
│  │  │              │          BlackListService.java --- 黑名单服务
│  │  │              │          DimensionService.java --- 维度服务
│  │  │              │          RiskControllService.java --- 风控服务
│  │  │              │          UserEventLogService.java --- 用户记录Log服务
│  │  │              │
│  │  │              └─utils
│  │  │                      BlackRecordFactory.java --- 黑名单工厂
│  │  │                      EventFactory.java --- 事件工厂，风控用
│  │  │                      ServletUtil.java --- Http工具类
│  │  │
│  │  └─resources
│  │      │  application-dev.yml --- 各自配置文件
│  │      │  application-wsc.yml  --- 各自配置文件
│  │      │  application.yml --- 总配置文件
│  │      │  logback.xml --- log 配置文件
│  │      │
│  │      ├─mapper
│  │      │      ApplyMapper.xml --- （废除）
│  │      │      BlackListMapper.xml --- 黑名单
│  │      │      UserDao.xml --- 用户
│  │      │
│  │      ├─rules
│  │      │      0_all_addEvent.drl --- 风控规则
│  │      │      999_all_blackList.drl --- 风控规则
│  │      │      99_filter.drl --- 风控规则
│  │      │
│  │      ├─sql
│  │      │      accounts.sql --- 用户信息表
│  │      │      black_list.sql --- 黑名单表
│  │      │
│  │      └─templates
│  │              apply.html  --- 验证码测试页面
│  │              error.html  --- 测试时错误页面
│  │              index.html  --- 主页面
│  │              login.html  --- 登录页面
│  │              register.html   --- 注册页面
│  │              user.html  --- 用户信息页面
```

# 系统详细设计


## 4.1. 系统功能模块划分

系统功能分为基本功能和风控功能，基本功能分为验证码模块、用户访问模块。

## 4.2 验证码模块

验证码模块包含获取验证码和验证验证码两个部分。

![](https://bytedancecampus1.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjE1NzM1YTRkMjA2ZmVhYzYxYWNkMzM2ZTkwMmY0ODBfeWswMW1PaVBCUGlPUEQzWnNyekhDbzdTMk1yZ2c2YVNfVG9rZW46Ym94Y25ubmo0WHg2dlN6YzE1akgxb3A1NlBjXzE2MzY4NzI4MjQ6MTYzNjg3NjQyNF9WNA)

### 4.2.1 基础业务逻辑

发送验证码：用户在输入手机点击发送验证码后，后端进行验证码生成、存储、将验证码发送到手机上。

验证验证码：用户输入手机和验证码点击验证验证码后，后端从缓存中获取验证码，并进行匹配，返回匹配结果。

### 4.2.2 后端三层结构的实现类

Controller 层：ApplyController.java

Service 层：ApplyServiceImp.java

Dao 层：RedisDao.java

### 4.2.3 发送验证码业务

无法复制加载中的内容

### 4.2.4 验证验证码

无法复制加载中的内容

### 4.2.5 存储设计

考虑到验证码的获取和验证是分步进行的，而且还有时间有效性，因此我们采用Redis缓存来进行验证码存储。

Redis Key：phone + "---" + ip + "---" + device。

手机号可以保证不同手机账户之间的验证码互不干扰；

ip和设备可以保证同一用户不同设备之间的验证码互不干扰。

## 4.3 用户访问模块

### 4.3.1 基础业务逻辑

注册业务：用户申请注册，输入用户名、密码、手机号、验证码后，发起申请注册请求；数据均没有问题并且能够存储到数据库中时，注册成功。

![](https://bytedancecampus1.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDdiNTI4OGM5ZGVlZTZjNDQyODM0NWVmNmIwZmQzODRfN0VYVTRoY2hPTzlXRHhEOEpHUHY2R3hJTHFIM2RvZkxfVG9rZW46Ym94Y25icVNneUVQRThFbzM3eFdnVVRSczNQXzE2MzY4NzI4MjQ6MTYzNjg3NjQyNF9WNA)

登录业务1：用户输入用户名和密码，当两者均正确时，登录成功。

登录业务2：用户输入手机号和验证码，当两者均正确时，登录成功。

登出业务：用户选择登出后，连接中断。

注销业务：用户选择注销后，数据库数据删除，连接中断。

### 4.3.2 登录态问题

为了完成登录态，我们使用Redis来存储登录态。

因为SessionID具有唯一性，且能够标识用户，因此我们使用SessionID作为Redis键。我们使用用户作为Redis值，其中，我们使用 fastjson 将用户转换为JSON形式的字符串。

应用：

-   当用户在注册成功、登录成功后，会将<SessionID, User> 存储到Redis中。
-   当用户在查看用户信息时，会根据sessionID 从Redis中获取User类，从而查看自己的账户信息。
-   当用户在注销、登出后，会将数据从Redis中删除。

### 4.3.3 用户信息存储

我们使用MySQL数据库来存储用户信息。

```
# 创建用户信息数据库
CREATE TABLE `accounts`
(
    `uid`         int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username`    varchar(30) UNIQUE,
    `password`    varchar(25),
    `phoneNumber` char(11) UNIQUE,
    `tag`         int NOT NULL DEFAULT 0
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into accounts
values (11111, "name1", "pwd1", 13555555555, 1);
```

### 4.3.2 注册业务

当用户访问 localhost:8080/register 后，将用户名、密码、手机号、验证码传入进来。

首先根据风控判断是否注册状态，并进行相应操作。

之后，分别判断是否存在相同手机号、用户名，判断是否验证码正确。

如果没有问题，则尝试将用户信息插入accounts数据库。

如果数据插入成功，则说明注册成功，将<SessionID, User> 存储到Redis中，记录登录状态。

最后返回 RegisterStatus 注册状态。

### 4.3.3 登录业务1~用户名和密码

当用户访问 localhost:8080/login 后，输入用户名和密码，点击提交尝试用户登录。

首先根据风控判断是否账号异常，并进行相应操作。

之后从数据库中获取数据，判断是否存在对应用户。

如果数据库存在该用户，则登录成功，将<SessionID, User> 存储到Redis中，记录登录状态。

最后返回 LoginStatus 登录状态。

### 4.3.4 登录业务2~手机号和验证码

当用户访问 localhost:8080/login 后，输入手机号和验证码，点击提交尝试用户登录。验证码部分与之前验证码业务中逻辑相同。

首先根据风控判断是否账号异常，并进行相应操作。

之后从数据库中获取数据，判断是否有手机号对应的账户。

判断验证码是否正确。

如果数据库存在该用户且验证码正确，则登录成功，将<SessionID, User> 存储到Redis中，记录登录状态。

最后返回 LoginStatus 登录状态。

### 4.3.5 用户信息查看业务

当用户访问 localhost:8080/user 后，查看用户信息。该业务主要是查看当前连接的用户信息，从而展示登录态。

后端首先获得当前的sessionid，然后从redis缓存中获取对应 value。如果value存在，则转变成User类，将用户名和手机号显示在前端。反之，如果当用户注销、登出、存储超时的情况，用户信息结果为空。

### 4.3.5 登出业务

当用户访问 localhost:8080/logout 后，进行当前连接用户登出。

后端将session注销，redis中删除登录态，返回主界面index.

### 4.3.6 注销业务

当用户访问 localhost:8080/deregister 后，进行账户注销。

后端获取当前sessionid，并从redis缓存中获得用户类。请求数据库删除数据。删除数据成功后，将session注销，redis中删除登录态，返回主界面index.

## 4.4 风控模块

### 4.4.1 风控策略（1：滑块，2：过一段时间，3：拦截）


### 4.4.2 主要使用的技术

Drools + Redis

### 4.4.3 风控流程

写入黑名单是先写入Redis，再异步写入MySQL中；

判断是否在黑名单也是先判断该请求对应的key里，Redis集合中是否有找到，如果找到直接返回True,否则查询MySQL数据库，并写入Redis中。

### 4.4.4 Redis键/值设计

-   频控

事件类型:维度类型:时间范围

比如键register:ip:last_second,表示这个ip过去1秒注册的频数

值就是对应不同时间的事件

-   绑定设备数量

键：用户标识:设备ID

比如张三:DEVICE_DFSDFDSFDS

值：绑定的数量,

-   黑名单

键：事件类型:blackList:检测维度

比如 register:blackList:username

值就是一个集合

### 4.4.5 Redis Lua脚本

-   频控

使用Redis的zset,score取的日期，根据当前传入的参数，求得过去一段时间该用户/手机号/IP/设备出现的频率

```
"if tonumber(ARGV[1])>0 then " +
    "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);" +
    "redis.call('EXPIRE',KEYS[1],ARGV[2]);" +
    "end;" +
    "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]);" +
    "return redis.call('ZCOUNT',KEYS[1],ARGV[5],ARGV[6]);";
```

-   绑定设备数量

```
"redis.call('INCR', KEYS[1]);" +
    "return redis.call('GET', KEYS[1]);"
```

-   黑名单

直接使用Redis的集合数据结构

### 4.4.6 数据库表

blanklist(用于存储黑名单)会用redis作缓存

```
DROP TABLE IF EXISTS `BLACK_LIST`;
CREATE TABLE `BLACK_LIST` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `dimension` int NOT NULL DEFAULT 2 COMMENT '维度, 0表示手机号，1表示IP,2表示设备ID',
  `type` int NOT NULL DEFAULT 0 COMMENT '类型, 0表示注册，1表示登录，2表示验证码',
  `value` varchar(128) NOT NULL DEFAULT '' COMMENT '值，对应的名称(手机号\IP\设备ID)',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `reason` varchar(512) DEFAULT NULL COMMENT '详情,进入黑名单的原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 4.4.7 对外提供服务的接口

```

/**
 * 风控分析接口
 * @param userName
 * @param type
 * @param ip
 * @param deviceID
 * @param telephone 如果没有可以填null/空字符
 * @return -1: 表示出错，0:表示正常，1：RiskControllConfig.HUA_KUAI, 2: RiskControllConfig.WAIT_FOR, 3: RiskControllConfig.REJECT
 */
int analysis(String userName, EventType type, String ip, String deviceID, String telephone);
```