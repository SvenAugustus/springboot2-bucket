# JAX-RS Using CXF
- https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jersey
-  http://cxf.apache.org/docs/springboot.html
-  https://github.com/apache/cxf/tree/master/distribution/src/main/release/samples

#### Starting the server

The sample uses Maven. It can be built and run from the command line using Maven, Java:

##### ---- With Maven ----
```
$ mvn -Pserver
```
##### ---- With Java ----
```
$ java -jar target/springboot-feat-jaxrs-cxf.jar
```

#### Testing the server

##### ---- From the browser ----

<http://localhost:8293/services/helloservice/sayHello/ApacheCxfUser>

will display "Hello ApacheCxfUser, Welcome to CXF RS Spring Boot World!!!"

<http://localhost:8293/services/helloservice/sayHello2/ApacheCxfUser>

will display "Hello2 ApacheCxfUser, Welcome to CXF RS Spring Boot World!!!"

##### ---- From the command line ----

```
$ mvn -Pclient
```