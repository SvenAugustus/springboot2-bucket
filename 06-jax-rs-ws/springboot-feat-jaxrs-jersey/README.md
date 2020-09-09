# JAX-RS Using Jersey
- https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/htmlsingle/#boot-features-jersey
- https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-samples/spring-boot-sample-jersey

-----
By default, the Jersey servlet is registered and mapped to `/*`. You can change the mapping by adding `@ApplicationPath` to your `ResourceConfig`.

#### Starting the server

The sample uses Maven. It can be built and run from the command line using Maven, Java:

##### ---- With Maven ----
```
$ mvn -Pserver
```
##### ---- With Java ----
```
$ java -jar target/springboot-feat-jaxrs-jersey.jar
```

#### Testing the server

##### ---- From the browser ----

<http://localhost:8295/api/sayHello/JerseyUser>

will display "Hello JerseyUser, Welcome to Jersey RS Spring Boot World!!!"

<http://localhost:8295/api/sayHello2/JerseyUser>

will display "Hello2 JerseyUser, Welcome to Jersey RS Spring Boot World!!!"

