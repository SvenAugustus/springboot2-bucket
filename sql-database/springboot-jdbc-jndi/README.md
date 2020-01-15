# Jdbc and JNDI Datasource
-  https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-connect-to-production-database

### Connection to a Production Database
Production database connections can also be auto-configured by using a pooling `DataSource`. Spring Boot uses the following algorithm for choosing a specific implementation:

- We prefer [HikariCP](https://github.com/brettwooldridge/HikariCP)  for its performance and concurrency. If HikariCP is available, we always choose it.

- Otherwise, if the `Tomcat` pooling `DataSource` is available, we use it.

- If neither `HikariCP` nor the `Tomcat` pooling datasource are available and if [Commons DBCP2](https://commons.apache.org/proper/commons-dbcp/) is available, we use it.


If you use the `spring-boot-starter-jdbc` or `spring-boot-starter-data-jpa` “starters”, you automatically get a dependency to `HikariCP`.

[Note]
You can bypass that algorithm completely and specify the connection pool to use by setting the `spring.datasource.type` property. This is especially important if you run your application in a Tomcat container, as `tomcat-jdbc` is provided by default.

[Note]
Additional connection pools can always be configured manually. If you define your own `DataSource` bean, auto-configuration does not occur.

#### Run the server

VM Option:
```shell script
-Dspring.profiles.active=development
```

##### ---- From the browser ----

<http://localhost:8312/hello/%E7%8E%8B%E4%BA%94>

Will Display "Hello xxxx, userInfo = " ...


### Connection to a JNDI DataSource
If you deploy your Spring Boot application to an Application Server, you might want to configure and manage your DataSource by using your Application Server’s built-in features and access it by using JNDI.

The `spring.datasource.jndi-name` property can be used as an alternative to the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to access the `DataSource` from a specific JNDI location.     

For example, the following section in `application.properties` shows how you can access a `Tomcat` AS defined `DataSource`:

```
spring.datasource.jndi-name=java:comp/env/jdbc/myDataSource
```

For `JBoss`:

```
spring.datasource.jndi-name=java:jboss/datasources/myDataSource
```

#### Package the server

```shell script
mvn package
```

It will build an war in the target directory of this project.

#### Deploy the server to Tomcat 

- Step1: Copy the war `springboot-feat-sql-database-jdbc-jndi.war` to `Tomcat` `webapps` directory

- Step2: Modify the file `context.xml` in `conf` directory of `Tomcat`, as:
```shell script
<?xml version='1.0' encoding='utf-8'?>
<Context>

    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

    <Resource name="jdbc/myDataSource"  
	    type="javax.sql.DataSource"  
	    driverClassName="com.mysql.jdbc.Driver"  
	    url="jdbc:mysql://localhost:3306/test"  
	    username="root"  
	    password="root123"  
	    maxActive="50"  
	    maxWait="10000"   />

</Context>

```

- Step3: Start the Tomcat Server.

##### ---- From the browser ----

<http://localhost:8080/springboot_feat_sql_database_jdbc_jndi/hello/%E7%8E%8B%E4%BA%94>

Will Display "Hello xxxx, userInfo = " ...


