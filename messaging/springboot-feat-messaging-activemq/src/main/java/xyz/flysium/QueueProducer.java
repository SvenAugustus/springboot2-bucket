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

package xyz.flysium;

import java.time.LocalDateTime;
import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Copy from git@github.com:spring-projects/spring-boot.git
 *
 * @author Sven Augustus
 */
@Component
public class QueueProducer implements CommandLineRunner {

  private final JmsMessagingTemplate jmsMessagingTemplate;

  @Qualifier("queue")
  private final Queue queue;

  @Qualifier("objectQueue")
  private final Queue objectQueue;

  public QueueProducer(JmsMessagingTemplate jmsMessagingTemplate, Queue queue,
      Queue objectQueue) {
    this.jmsMessagingTemplate = jmsMessagingTemplate;
    this.queue = queue;
    this.objectQueue = objectQueue;
  }

  @Override
  public void run(String... args) throws Exception {
    send("Sample message "+ LocalDateTime.now());
    System.out.println("Message was sent to the Queue");
  }

  public void send(String msg) {
    this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
  }

  public void send(Object msg) {
    this.jmsMessagingTemplate.convertAndSend(this.objectQueue, msg);
  }
}
