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

import com.github.flysium.io.bucket.springboot.DemoProducer;
import com.github.flysium.io.bucket.springboot.model.UserInfo;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test.
 *
 * @author Sven Augustus
 */
@RestController
public class TestController {

  private final DemoProducer producer;

  public TestController(DemoProducer producer) {
    this.producer = producer;
  }

  @GetMapping("/send")
  public String send()
      throws InterruptedException, IOException, ExecutionException, TimeoutException {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Jack", 31);
    Future future = producer.send(user.getId(), user);
    future.get(10, TimeUnit.SECONDS);
    return "Message was sent to the Topic: " + user;
  }

  @GetMapping("/sendInTransaction")
  public String sendInTransaction() throws InterruptedException, IOException, ExecutionException {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Tim", 28);
    producer.sendInTransaction(user.getId(), user);
    return "Message was sent to the Topic: " + user;
  }

}
