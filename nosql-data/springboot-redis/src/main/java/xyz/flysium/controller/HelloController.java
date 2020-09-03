/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.flysium.dao.entity.UserInfo;

/**
 * Hello.
 *
 * @author Sven Augustus
 */
@RestController
public class HelloController {

  private final RedisTemplate redisTemplate;

  public HelloController(RedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @GetMapping("/")
  public String hello() {
      BoundValueOperations<String, String> ops = redisTemplate.boundValueOps("spring.boot.redis.test");
    ops.setIfAbsent("Welcome to Spring Data Redis !!!");
    return ops.get();
  }

  @GetMapping("/redisSerializerTest")
  public String redisSerializerTest() {
      BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("spring.boot.redis.serializer.test");
      UserInfo info = new UserInfo();
    info.setId(100L);
    info.setName("Sven Augustus");
    info.setAge(129);
    ops.setIfAbsent(info);
    return String.valueOf(ops.get());
  }

  @GetMapping("/readyTest")
  public String readyTest() {
    redisTemplate.boundValueOps("spring.boot.redis.test.string").setIfAbsent("Test-String");
    redisTemplate.boundHashOps("spring.boot.redis.test.map").putAll(new HashMap(16) {
      {
        this.put("id", 1L);
        this.put("name", "Sven Augustus");
        this.put("age", 129);
      }
    });
    return "OK";
  }

  @GetMapping("/redisTest")
  public String redisTest() {
    Object value = redisTemplate.boundValueOps("spring.boot.redis.test.string").get();
    Map<Object, Object> map = redisTemplate.boundHashOps("spring.boot.redis.test.map").entries();
    return "OK";
  }

}
