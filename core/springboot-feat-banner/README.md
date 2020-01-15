# Spring Banner

## 概念
Banner 指的是 Spring Boot 启动时打印的字符画，默认是 Spring Boot。  

## 自定义 Banner
[Customizing the Banner](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-banner)

### Banner 参数
- `${application.version}`  
The version number of your application, as declared in `MANIFEST.MF`. For example,Implementation-Version: `1.0` is printed as `1.0`.

- `${application.formatted-version}`    
The version number of your application, as declared in `MANIFEST.MF` and formatted for display (surrounded with brackets and prefixed with v). For example (`v1.0`).

- `${spring-boot.version}`    
The Spring Boot version that you are using. For example `2.1.6.RELEASE`.

- `${spring-boot.formatted-version}`    
The Spring Boot version that you are using, formatted for display (surrounded with brackets and prefixed with v). For example (`v2.1.6.RELEASE`).

- `${Ansi.NAME}` (or `${AnsiColor.NAME}`, `${AnsiBackground.NAME}`, `${AnsiStyle.NAME}`)  
Where NAME is the name of an ANSI escape code. See `AnsiPropertySource.java` for details.

- `${application.title}`   
The title of your application, as declared in `MANIFEST.MF`. For exampleImplementation-Title: `MyApp` is printed as `MyApp`.

### 字符画生成工具
-  http://www.network-science.de/ascii/
-  http://patorjk.com/software/taag
-  http://www.network-science.de/ascii/
-  http://www.degraeve.com/img2txt.php


## 参考
https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-banner
