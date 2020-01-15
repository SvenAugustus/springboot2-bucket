# SpringBoot Whitelabel Error Page

在使用 Spring Boot 如果出现错误会出现 `Whitelabel Error Page` 页面，这个是 Spring Boot 
默认处理错误的一个页面，是一硬编码的形式创建的。我们可以替换调，使用自己的`error`页面，并且美化它。  

默认`Whitelabel Error Page` 页面源代码：  
```<html><body><h1>Whitelabel Error Page</h1><p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p><div id='created'>Mon Feb 18 13:28:40 CST 2019</div><div>There was an unexpected error (type=Internal Server Error, status=500).</div><div>Request processing failed; nested exception is java.lang.UnsupportedOperationException: do not support?</div></body></html>```

## 源码分析
Spring Boot 如果出现错误，比如：401、403、404、500等，均是有 `org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController` 处理的。我们也可以重写它，实现自己的逻辑。

## 自定义错误页面

- 若项目中使用了模板引擎，比如[thymeleaf](https://www.thymeleaf.org/)、[freemarker](https://freemarker.apache.org/ )
等，此时`server.error.whitelabel.enabled`关闭和开启无关。 
  - 在`classpath`下新建静态资源 `/<staticlocation>/error/4xx.html`   `/<staticlocation>/error/5xx.html` 
  - 在`classpath`下新建模板 `/templates/error/4xx.html` `/templates/error/5xx.html`  
  - 在`classpath`下直接新建模板 `/templates/error.html`  

- 若项目中没有任何模板引擎

  - 设置`server.error.whitelabel.enabled=true`使用内置view渲染，查找以下静态资源，即`/<staticlocation>/error/4xx.html` `/<staticlocation>/error/5xx.html`。  
如果没有，则出现默认的`Whitelabel Error Page` 页面 (交给`ErrorMvcAutoConfiguration`中的 `StaticView` 去渲染)。
  - 若`server.error.whitelabel.enabled=false`将不会有任何信息。  

## 参考
Spring Boot [Error Handling](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-error-handling)

