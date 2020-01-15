# Spring Externalized Configuration

## 概念
Spring Boot允许您外部化配置，以便您可以在不同的环境中使用相同的应用程序代码。 您可以使用`properties `文件，`YAML`文件，环境变量和命令行参数来外部化配置。 可以使用`@Value`注解将属性值直接注入到bean中，通过Spring的`Environment`抽象访问，或者通过`@ConfigurationProperties`绑定到[结构化对象](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-external-config-typesafe-configuration-properties)。

## 配置加载
Spring Boot使用一个非常特殊的`PropertySource`排序，旨在允许合理地覆盖值。 按以下顺序考虑属性：

- 1、[Devtools global settings properties](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#using-boot-devtools-globalsettings) on your home directory (`~/.spring-boot-devtools.properties` when devtools is active).
- 2、[@TestPropertySource](https://docs.spring.io/spring/docs/5.1.6.RELEASE/javadoc-api/org/springframework/test/context/TestPropertySource.html) annotations on your tests.
- 3、`properties` attribute on your tests. Available on [@SpringBootTest](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/api/org/springframework/boot/test/context/SpringBootTest.html) and the [test annotations for testing a particular slice of your application](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-tests).
- 4、Command line arguments.
- 5、Properties from `SPRING_APPLICATION_JSON` (inline JSON embedded in an environment variable or system property).
- 6、`ServletConfig` init parameters.
- 7、`ServletContext` init parameters.
- 8、JNDI attributes from `java:comp/env`.
- 9、Java System properties (`System.getProperties()`).
- 10、OS environment variables.
- 11、A `RandomValuePropertySource` that has properties only in `random.*`.
- 12、[Profile-specific application properties](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-external-config-profile-specific-properties) outside of your packaged jar (`application-{profile}.properties` and YAML variants).
- 13、[Profile-specific application properties](https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-external-config-profile-specific-properties)  packaged inside your jar (`application-{profile}.properties` and YAML variants).
- 14、Application properties outside of your packaged jar (`application.properties` and YAML variants).
- 15、Application properties packaged inside your jar (`application.properties` and YAML variants).
- 16、[@PropertySource](https://docs.spring.io/spring/docs/5.1.6.RELEASE/javadoc-api/org/springframework/context/annotation/PropertySource.html) annotations on your `@Configuration` classes.
- 17、Default properties (specified by setting `SpringApplication.setDefaultProperties`).

加载顺序从1-17，即是如果存在同样的配置，在更小数字的加载顺序的配置会覆盖更大数字的加载顺序的配置。  


## 属性转换 Properties Conversion 
链接：https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-external-config-conversion  

Spring Boot attempts to coerce the external application properties to the right type when it binds to the @ConfigurationProperties beans. If you need custom type conversion, you can provide a ConversionService bean (with a bean named conversionService) or custom property editors (through a CustomEditorConfigurer bean) or custom Converters (with bean definitions annotated as `@ConfigurationPropertiesBinding`).  

可以参考 `org.springframework.boot.convert.StringToDataSizeConverter` 实现一个属性转换器。


## 参考
https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-external-config

