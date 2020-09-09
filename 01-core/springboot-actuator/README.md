# Spring Actuator

Maven:
```xml
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

yml：

```yml
spring:
  jmx:
    enabled: true
management:
  endpoints:
    enabled-by-default: true
    web:
      exposure:
        include: "*"
  endpoint:
    health:
      show-details: always
```

查询端点信息。


```sh
http://localhost:8003/actuator/health
```
```
检查资源来判断应用程序是否正常。
UP,DOWN,OUT_OF_SERVICE,UNKNOWN


{
  "status": "UP",
}
```