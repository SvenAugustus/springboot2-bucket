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

package com.github.flysium.io.bucket.springboot.controller;

import com.github.flysium.io.bucket.springboot.Producer;
import com.github.flysium.io.bucket.springboot.model.UserInfo;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test.
 *
 * @author Sven Augustus
 */
@RestController
public class TestController {

  private final Producer producer;

  public TestController(Producer producer) {
    this.producer = producer;
  }

  @GetMapping("/send")
  public String send() {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Jack", 31);
    producer.send("spring.boot.key.user", user);
    return "Message was sent to the Queue: " + user;
  }

  @GetMapping("/sendNative")
  public String sendNative() {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Jack", 31);
    producer.sendNative("spring.boot.key.user", user);
    return "Message was sent to the Queue: " + user;
  }

  // 测试：交换器无法根据自身的类型和路由键找到一个符合条件的队列
  @GetMapping("/sendNoRoute")
  public String sendNoRoute() {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Tim", 27);
    producer.send("xxxxx--spring.boot.key.user", user);
    return "Message was sent to the Queue: " + user;
  }

}
