# 使用说明
## 这是专门用来通知的一个模块，后续会集成SMS，Email，Socket等方式，力求简化使用

# 1.在需要的模块引入依赖
### 引入依赖
```
<!-- 引入通知模块 -->
<dependency>
    <groupId>vip.efactory</groupId>
    <artifactId>common-notify</artifactId>
    <version>1.0.0</version>
</dependency>
```

# 2.配置要启用的模块
```
notify:
  sms:
    # 启用发送短信的功能
    enable: true
  email:
    # 启用发送邮件功能
    enable: true
```

# 3.配置各自模块需要的参数
### Email模块是基于JavaMailSender实现的，所以配置方式同JavaMailSender
```
spring:
  # javaMail发送邮件的配置
  mail:
    host: smtp.163.com
    username: eflow
    password: GB...UU
    port: 465
    properties:
      mail:
        smtp:
          # from不能缺，否则可能报认证用户不一致
          from: eflow@163.com
          auth: true
          ssl.enable: true
          starttls.enable: false
          required: false
    # 是否启动时测试可用性！
    test-connection: true
```
### 短信的配置
```
notify:
  sms:
    # 启用发送短信的功能
    enable: true
    signName: EFADMIN系统
    domain: dysmsapi.aliyuncs.com
    ...更多信息参见SmsConfProperties
```

# 4.在自己的组件里注入需要的通知模块
```java
    /**
     * 邮件发送服务
     */
    private final IEmailNotifyService emailNotifyService;

    /**
     * 短信通知模块
     */
    private final ISmsNotifyService smsNotifyService;
```

# 5.调用模块提供的方法即可!
