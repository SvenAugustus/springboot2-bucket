# JPA and Spring Data JPA Simple Sample
-  https://spring.io/guides/gs/accessing-data-jpa/
-  https://projects.spring.io/spring-data-jpa/
-  https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data
- https://docs.spring.io/spring-boot/docs/2.1.15.RELEASE/reference/htmlsingle/#howto-database-initialization

### What is Java Persistence API (JPA) ?
As a specification, the Java Persistence API is concerned with persistence, which loosely means any mechanism by which Java objects outlive the application process that created them. Not all Java objects need to be persisted, but most applications persist key business objects. The JPA specification lets you define which objects should be persisted, and how those objects should be persisted in your Java applications.

By itself, `JPA` is not a tool or framework; rather, it defines a set of concepts that can be implemented by any tool or framework. While JPA's object-relational mapping (`ORM`) model was originally based on `Hibernate`, it has since evolved. Likewise, while JPA was originally intended for use with relational/SQL databases, some JPA implementations have been extended for use with NoSQL datastores. A popular framework that supports JPA with NoSQL is `EclipseLink`, the reference implementation for JPA 2.2.

### Powerful Spring Data JPA
-  https://docs.spring.io/spring-data/jpa/docs/2.2.0.RELEASE/reference/html/
- https://docs.spring.io/spring-data/jpa/docs/2.2.0.RELEASE/reference/html/#appendix

### Testing the server

##### ---- From the browser ----

<http://localhost:8313/hello/%E7%8E%8B%E4%BA%94>

Will Display "Hello xxxx, userInfo = " ...


### howto-initialize-a-database-using-jpa
##### Initialize a Database Using JPA
JPA has features for DDL generation, and these can be set up to run on startup against the
database. This is controlled through two external properties:

* `spring.jpa.generate-ddl` (boolean) switches the feature on and off and is vendor
independent.
* `spring.jpa.hibernate.ddl-auto` (enum) is a Hibernate feature that controls the
behavior in a more fine-grained way. This feature is described in more detail later in
this guide.


### howto-initialize-a-database-using-hibernate
##### Initialize a Database Using Hibernate
You can set `spring.jpa.hibernate.ddl-auto` explicitly and the standard Hibernate property
values are `none`, `validate`, `update`, `create`, and `create-drop`. Spring Boot chooses
a default value for you based on whether it thinks your database is embedded. It defaults
to `create-drop` if no schema manager has been detected or `none` in all other cases. An
embedded database is detected by looking at the `Connection` type. `hsqldb`, `h2`, and
`derby` are embedded, and others are not. Be careful when switching from in-memory to a
'`real`' database that you do not make assumptions about the existence of the tables and
data in the new platform. You either have to set `ddl-auto` explicitly or use one of the
other mechanisms to initialize the database.

NOTE: You can output the schema creation by enabling the `org.hibernate.SQL` logger. This
is done for you automatically if you enable the
<<boot-features-logging-console-output,debug mode>>.

In addition, a file named `import.sql` in the root of the classpath is executed on
startup if Hibernate creates the schema from scratch (that is, if the `ddl-auto` property
is set to `create` or `create-drop`). This can be useful for demos and for testing if you
are careful but is probably not something you want to be on the classpath in production.
It is a Hibernate feature (and has nothing to do with Spring).


### howto-initialize-a-database-using-spring-jdbc
##### Initialize a Database
Spring Boot can automatically create the schema (DDL scripts) of your `DataSource` and
initialize it (DML scripts). It loads SQL from the standard root classpath locations:
`schema-h2.sql` and `data-h2.sql`, respectively. In addition, Spring Boot processes the
`schema-${platform}.sql` and `data-${platform}.sql` files (if present), where `platform`
is the value of `spring.datasource.platform`. This allows you to switch to
database-specific scripts if necessary. For example, you might choose to set it to the
vendor name of the database (`hsqldb`, `h2`, `oracle`, `mysql`, `postgresql`, and so on).

Spring Boot automatically creates the schema of an embedded `DataSource`. This behavior
can be customized by using the `spring.datasource.initialization-mode` property (and it
can also be `always` or `never`).

By default, Spring Boot enables the fail-fast feature of the Spring JDBC initializer. This
means that, if the scripts cause exceptions, the application fails to start. You can tune
that behavior by setting `spring.datasource.continue-on-error`.

[NOTE]
 In a `JPA`-based app, you can choose to let `Hibernate` create the schema or use
`schema-h2.sql`, but you cannot do both. Make sure to disable
`spring.jpa.hibernate.ddl-auto` if you use `schema-h2.sql`.
