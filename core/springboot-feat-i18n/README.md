# Spring Internationalization

## Java 国际化
`Java`程序的国际化的思路是将程序中的标签、提示等信息放在资源文件中，程序需要支持哪些国家、语言环境，就对应提供相应的资源文件。资源文件是key-value对，每个资源文件中的key是不变的，但value则随不同国家、语言改变。 
 
### Java程序的国际化
- `java.util.ResourceBundle`：用于加载一个国家、语言资源包。
- `java.util.Locale`：用于封装一个特定的国家/区域、语言环境。
- `java.text.MessageFormat`：用于格式化带占位符的字符串。

### Java国际化资源文件
为了实现程序的国际化，必须先提供程序所需要的资源文件。资源文件的内容是很多key-value对。其中key是程序使用的部分，而value则是程序界面的显示字符串。

资源文件的命名可以有如下三种形式：  
- `baseName _ language _country.properties`
- `baseName _language.properties`  
- `baseName.properties  `
其中`baseName`是资源文件的基本名，用户可以自由定义。
而`language`和`country`都不可随意变化，必须是Java所支持的语言和国家。

### Java支持的语言和国家

事实上，Java不可能支持所有国家和语言，如需要获取Java所支持的语言和国家，可调用`Locale`类的`getAvailableLocale`方法获取，该方法返回一个Locale数组，该数组里包含了Java所支持的语言和国家。

## Spring 国际化

### MessageSource
`Spring`中定义了一个`MessageSource`接口，以用于支持信息的国际化和包含参数的信息的替换。
可以使用`spring.messages`命名空间配置资源包的基本名称以及其他几个属性，如以下示例所示：
利用messageSource告诉Spirng MVC国际化的属性文件保存在哪里。

### localeResolver
用户选择语言区域的时候，最常用的方法是通过读取用户浏览器的`accept-language`标题值。其他方式还有读取`HttpSession`或者`Cookie`。

Spring MVC提供的包:
- `AcceptHeaderLocaleResolver`  
读取浏览器的`accept-language`标题是默认的，也是最容易使用的语言区域解析器。  
可以不用显式配置。
- `SessionLocaleResolver`
- `CookieLocaleResolver`

### Spring Boot 国际化配置

```yml
spring.messages.basename=messages,config.i18n.messages
spring.messages.fallback-to-system-locale=false
```

#### fallbackToSystemLocale
默认情况下，当指定`Locale`不存在某code对应的属性时，默认将尝试从系统对应的`Locale`中解析对应的code，只有都不能解析时才会使用基文件进行解析，如果还不能解析则将抛出异常。

打个比方针对基名称“message”我们有两个属性文件， message.properties 和 message_zh_CN.properties，其中前者定义了appName=test，且定义了hello=hello，而后者只定义了appName=测试，那么当我们通过如下代码获取对应code对应的信息时，输出将如代码中注释所示，即输出两个“测试”，一个“hello”。

```java
System.out.println(this.messageSource.getMessage("appName", null, null));//测试
System.out.println(this.messageSource.getMessage("appName", null, Locale.ENGLISH));//测试
System.out.println(this.messageSource.getMessage("hello", null, Locale.CHINA));//hello
```

## 参考
https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-internationalization


