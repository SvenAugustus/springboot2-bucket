# Spring Web MVC Framework
除REST Web服务外，您还可以使用`Spring MVC`来提供动态HTML内容。 

Spring MVC支持各种模板技术，包括Thymeleaf，FreeMarker和JSP。 此外，许多其他模板引擎包括他们自己的Spring MVC集成。

#### Spring Boot包含对以下模板引擎的自动配置支持：
- FreeMarker
- Groovy
- Thymeleaf
- Mustache

当使用了默认的自动配置,模板的存放位置 `src/main/resources/templates`

```
如果可能,避免使用JSPs ,有一些已知的问题存在嵌入的servlet
```

#### 使用`JSPs`已知问题
https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jsp-limitations

When running a Spring Boot application that uses an embedded servlet container (and is packaged as an executable archive), there are some limitations in the JSP support.

- With `Jetty` and `Tomcat`, it should work if you use war packaging. An executable war will work when launched with ` java -jar`, and will also be deployable to any standard container. <font color=red>`JSPs`  are  not supported when using an executable jar.</font>

- `Undertow` does not support `JSPs`.

- Creating a custom `error.jsp` page does not override the default view for [error handling](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-error-handling). [Custom error pages](https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-error-handling-custom-error-pages) should be used instead.

There is a [JSP sample](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-samples/spring-boot-sample-web-jsp) so that you can see how to set things up.
## 参考
https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-spring-mvc


