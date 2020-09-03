/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.flysium.TransactionProducer;
import xyz.flysium.constant.Producer;
import xyz.flysium.dao.entity.OrderInfo;
import xyz.flysium.dao.entity.UserInfo;

/**
 * Test.
 *
 * @author Sven Augustus
 */
@RestController
public class TestController {

  private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  private final Producer producer;
  private final TransactionProducer transactionProducer;
  private AtomicLong atomicLong = new AtomicLong(0);

  public TestController(Producer producer,
      TransactionProducer transactionProducer) {
    this.producer = producer;
    this.transactionProducer = transactionProducer;
  }

  @GetMapping("/send")
  public String send()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    long now = atomicLong.incrementAndGet();
    UserInfo user = new UserInfo(now, "Jack", 31);
    producer.send(user);
    return "Message was sent to the Topic: " + user;
  }

  @GetMapping("/order")
  public String order()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    transactionProducer.sendMessageInTransaction("msg-1", new OrderInfo(101, 1, "OK"));
    transactionProducer.sendMessageInTransaction("msg-2", new OrderInfo(101, 2, "OK"));
    transactionProducer.sendMessageInTransaction("msg-3", new OrderInfo(103, 3, "msg-13"));
    transactionProducer.sendMessageInTransaction("msg-4", new OrderInfo(104, 4, "OK"));
    transactionProducer.sendMessageInTransaction("msg-5", new OrderInfo(105, 5, "OK"));
    return "OK, " + LocalDateTime.now();
  }

}
