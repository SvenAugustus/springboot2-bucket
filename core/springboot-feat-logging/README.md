# Spring Logging

## 概念
`Spring Boot`使用`Commons Logging`进行所有内部日志记录，但保留底层日志实现。为`Java Util Logging`，`Log4J2`和`Logback`提供了默认配置。在每种情况下，记录器都预先配置为使用控制台输出，并且还提供可选的文件输出。  

默认,如果使用`Starters`,那么`Logback`被选为日志记录器。

## 日志级别
所有受支持的日志记录系统都可以使用`logging.level.<logger-name>=<level>` 设置`Spring`环境中（例如，在`application.properties`中）的记录器级别.其中level是`TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `FATAL`, 或 `OFF`之一， 可以使用`logging.level.root`配置root记录器。

以下示例显示了`application.properties`中的潜在日志记录设置：  

```
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

## 日志分组
日志组能够将相关的记录器组合在一起以便可以同时配置它们通常很有用。
例如，您通常可以更改所有Tomcat相关记录器的日志记录级别，但您无法轻松记住顶级软件包。为了解决这个问题，`Spring Boot`允许您在Spring环境中定义日志记录组。
例如，以下是通过将`tomcat`组添加到`application.properties`来定义`tomcat`组的方法：  
```
logging.group.tomcat = org.apache.catalina，org.apache.coyote，org.apache.tomcat
```
定义后，您可以使用一行更改组中所有记录器的级别：
```
logging.level.tomcat = TRACE
```
Spring Boot包含以下预定义的日志记录组，可以直接使用：  

|   分组名称    |      对应的包名                                                                           |  
|--------------|:----------------------------------------------------------------------------------------:|  
|      web     |  `org.springframework.core.codec`, `org.springframework.http`, `org.springframework.web` |   
|      sql     |  `org.springframework.jdbc.core`, `org.hibernate.SQL`                                    |  


## 自定义日志配置
可以通过在类路径中包含适当的库来激活各种日志记录系统，并且可以通过在类路径的根目录中或在属性`logging.config`中指定的位置提供合适的配置文件来进一步自定义。

另外，也可以通过系统属性`org.springframework.boot.logging.LoggingSystem`强制`Spring Boot`使用某个日志,值是实现了`LoggingSystem`的全限定类名.也可设置为`none`完全禁用日志配置。

根据日志系统,下列文件将加载：

|   日志系统	   |      配置文件                                                                           |  
|--------------|:--------------------------------------------------------------------------------------:|  
|   Logback    |  `logback-spring.xml`, `logback-spring.groovy`, `logback.xml`, or `logback.groovy`     |   
|   Log4j2     |  `log4j2-spring.xml` or `log4j2.xml`                                                   |
|   JDK (`Java Util Logging`)  |  `logging.properties`                                                  |        

## Logback 扩展
https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-logback-extensions

### 特定于配置文件的配置
`<springProfile>` 标签允许您根据活动的Spring配置文件选择性地包含或排除配置部分。 在`<configuration>`元素中的任何位置都支持配置`<springProfile>`节点。 
使用`name`属性指定哪个配置文件接受配置。 可以使用逗号分隔列表指定多个配置文件。 以下清单显示了三个示例配置文件：

```xml
<springProfile name="staging">
	<!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev, staging">
	<!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
	<!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```

### 环境属性
使用`<springProperty>`标记可以公开`Spring中Environment`的属性，以便在`Logback`中使用。 如果要从Logback配置中的`application.properties`文件访问值，这样做会很有用。 标签的工作方式与Logback的标准`<property>`标签类似。 
但是，您可以指定属性的来源（来自Environment），而不是指定直接值。 如果需要将属性存储在local范围以外的其他位置，则可以使用`scope`属性。 如果需要默认值（如果未在Environment中设置该属性），则可以使用`defaultValue`属性。 
以下示例显示如何公开在Logback中使用的属性：

```xml
<springProperty scope="context" name="fluentHost" source="myapp.fluentd.host"
		defaultValue="localhost"/>
<appender name="FLUENT" class="ch.qos.logback.more.appenders.DataFluentAppender">
	<remoteHost>${fluentHost}</remoteHost>
	...
</appender>
```


## 参考
https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-logging


