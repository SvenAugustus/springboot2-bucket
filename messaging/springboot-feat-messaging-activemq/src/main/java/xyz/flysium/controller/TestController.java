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

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.flysium.QueueProducer;
import xyz.flysium.TopicProducer;
import xyz.flysium.dao.entity.UserInfo;

/**
 * Test.
 *
 * @author Sven Augustus
 */
@RestController
public class TestController {

  private final QueueProducer queueProducer;
  private final TopicProducer topicProducer;

  public TestController(QueueProducer queueProducer, TopicProducer topicProducer) {
    this.queueProducer = queueProducer;
    this.topicProducer = topicProducer;
  }

  @GetMapping("/sendToQueue")
  public String sendToQueue() {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Jack", 29);
    this.queueProducer.send(user);
    return "Message was sent to the Queue: " + user;
  }

  @GetMapping("/sendToTopic")
  public String sendToTopic() {
    UserInfo user = new UserInfo(String.valueOf(LocalDateTime.now()), "Rose", 22);
    this.topicProducer.send(user);
    return "Message was sent to the Topic: " + user;
  }

}
