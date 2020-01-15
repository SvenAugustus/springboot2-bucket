# Spring Application Events and Listeners

## 概念与用法
某些事件实际上是在创建`ApplicationContext`之前触发的，因此你无法将这些事件的侦听器注册为@Bean。  
你可以使用`SpringApplication.addListeners（...）`方法或`SpringApplicationBuilder.listeners（...）`方法注册它们。如果您希望自动注册这些侦听器，无论应用程序的创建方式如何，你都可以将`META-INF/spring.factories`文件添加到项目中，并使用`org.springframework.context.ApplicationListener`引用侦听器。
如以下示例所示：
```bash
org.springframework.context.ApplicationListener=\
    com.example.project.MyListener1,\
    com.example.project.MyListener2
```

## Application Events and Listeners
应用程序运行时，按以下顺序发送应用程序事件：
  - `ApplicationStartingEvent` : 在运行开始时但在任何处理（除了接受监听器和初始化程序的注册）之前发送该事件。
  - `ApplicationEnvironmentPreparedEvent` : 当已知`Environment`在上下文创建前使用时发送该事件。
  - `ApplicationPreparedEvent` : 在刷新开始之前，但是在加载`bean`定义之后发送该事件。
  - `ApplicationStartedEvent` : 在刷新上下文之后但在调用任何应用程序和命令行运行程序之前发送该事件。
  - `ApplicationReadyEvent` : 表示应用准备好提供服务了。
  - `ApplicationFailedEvent` : 启动时发生任何异常，则发送该事件。

你可能经常不需要使用应用程序事件，但知道它们存在可能很方便。在内部，Spring Boot使用事件来处理各种任务。  

## 参考
https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-application-events-and-listeners




