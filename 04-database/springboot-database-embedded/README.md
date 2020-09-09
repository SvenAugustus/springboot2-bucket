# Embedded Database Support
-  https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-embedded-database-support

### Support Databases
Spring Boot can auto-configure embedded [H2](https://www.h2database.com/), [HSQL](http://hsqldb.org/), and [Derby](https://db.apache.org/derby/) databases. You need not provide any connection URLs. You need only include a build dependency to the embedded database that you want to use.

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

### Tips
If you are using this feature in your tests, you may notice that **the same database is reused by your whole test suite regardless of the number of application contexts that you use**.    
If you want to make sure that each context has a separate embedded database, you should set `spring.datasource.generate-unique-name` to `true`.

If, for whatever reason, you do configure the connection URL for an embedded database, **take care to ensure that the database’s automatic shutdown is disabled**.   

- If you use `H2`, you should use `DB_CLOSE_ON_EXIT=FALSE` to do so.   
- If you use `HSQLDB`, you should ensure that `shutdown=true` is not used.    

Disabling the database’s automatic shutdown lets Spring Boot control when the database is closed, thereby ensuring that it happens once access to the database is no longer needed.

### H2
##### Configuration
See `application.yml`.

##### ---- From the browser ----

<http://localhost:8311/h2/>

Will display the H2 Web Console, enter the JDBC URL `jdbc:h2:mem:test` which configed in `application.yml`.

Then you can connect and enter the h2 database views.
