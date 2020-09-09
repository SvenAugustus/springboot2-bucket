# Spring Data Redis
- https://docs.spring.io/spring-data/redis/docs/2.2.0.RELEASE/reference/html/#reference
- [Lettuce](https://github.com/lettuce-io/lettuce-core) 

### Requirements

Spring Redis requires `Redis` `2.6` or above and Spring Data Redis integrates with `Lettuce` and `Jedis`, two popular open-source Java libraries for Redis.

### Connecting to Redis

One of the first tasks when using Redis and Spring is to connect to the store through the IoC container. To do that, a Java connector (or binding) is required. No matter the library you choose, you need to use only one set of Spring Data Redis APIs (which behaves consistently across all connectors): the `org.springframework.data.redis.connection` package and its `RedisConnection` and `RedisConnectionFactory` interfaces for working with and retrieving active connections to `Redis`.

### `Lettuce`  is recommended

There are also a few Lettuce-specific connection parameters that can be tweaked. By default, all `LettuceConnection` instances created by the `LettuceConnectionFactory` share the same thread-safe native connection for all non-blocking and non-transactional operations. To use a dedicated connection each time, set `shareNativeConnection` to `false`. `LettuceConnectionFactory` can also be configured to use a `LettucePool` for pooling blocking and transactional connections or all connections if `shareNativeConnection` is set to `false`.

```java
@Configuration
public class CustomRedisConfiguration {

  @Autowired
  public void setLettuceConnectionFactory(LettuceConnectionFactory lettuceConnectionFactory) {
    lettuceConnectionFactory.setShareNativeConnection(false);
  }

}
```

### Using `Jedis` Instead of `Lettuce`

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
 <exclusions>
   <exclusion>
     <groupId>io.lettuce</groupId>
     <artifactId>lettuce-core</artifactId>
   </exclusion>
 </exclusions>
</dependency>
<dependency>
<groupId>redis.clients</groupId>
<artifactId>jedis</artifactId>
</dependency> 
```

#### Testing the server

##### ---- From the browser ----

<http://localhost:8321/>

will display "Welcome to Spring Data Redis !!!"

<http://localhost:8321/redisSerializerTest>

will display "UserInfo{id=100, name='Sven Augustus', age=129}"
