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

package com.github.flysium.io.bucket.springboot;

import com.github.flysium.io.bucket.springboot.entity.OrderInfo;
import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Sven Augustus
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleRocketMQApplicationTest {

  private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  @Rule
  public OutputCapture outputCapture = new OutputCapture();

  @Autowired
  private Producer producer;

  @Autowired
  private TransactionProducer transactionProducer;

  private AtomicLong atomicLong = new AtomicLong(0);

  @Test
  public void send()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    long now = atomicLong.incrementAndGet();
    this.producer.send(new UserInfo(now, "Alice", 18));
    Thread.sleep(1000L);
    Assertions.assertThat(this.outputCapture.toString().contains("UserInfo{id='" + now)).isTrue();
  }

  @Test
  public void sendAsynchronously()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    long now = atomicLong.incrementAndGet();
    this.producer.send(new UserInfo(now, "Alice", 18), new SendCallback() {

      @Override
      public void onSuccess(SendResult sendResult) {
        System.out.println("Send Result=" + sendResult + ", msgId=" + sendResult.getMsgId());
      }

      @Override
      public void onException(Throwable e) {
        System.err.println("Send Failed: " + e.getMessage());
      }
    });
    Thread.sleep(1000L);
    Assertions.assertThat(this.outputCapture.toString().contains("UserInfo{id='" + now)).isTrue();
  }

  /**
   * Scheduled messages differ from normal messages in that they won’t be delivered until a provided
   * time later.
   */
  @Test
  public void sendScheduleMessage()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    long now = atomicLong.incrementAndGet();
    // This message will be delivered to consumer 5 seconds later.
    this.producer.send(new UserInfo(now, "Alice", 18), 2);
    Thread.sleep(15000L);
    // You should see messages are consumed about 5 seconds later than their storing time.
    Assertions.assertThat(this.outputCapture.toString().contains("UserInfo{id='" + now)).isTrue();
  }

  /**
   * What is transactional message ?
   *
   * It can be thought of as a two-phase commit message implementation to ensure eventual
   * consistency in distributed system.
   */
  @Test
  public void sendTransactionMessage()
      throws InterruptedException, RemotingException, MQClientException, MQBrokerException, IOException {
    Thread.sleep(1000L);
    long now = atomicLong.incrementAndGet();
    long now3 = atomicLong.incrementAndGet();
    this.transactionProducer.sendMessageInTransaction("msg-11", new OrderInfo(now, 1, "msg-11"));
    // msg-12 重复插入数据，事务失败
    this.transactionProducer.sendMessageInTransaction("msg-12", new OrderInfo(now, 2, "msg-12"));
    this.transactionProducer.sendMessageInTransaction("msg-13", new OrderInfo(now3, 3, "msg-13"));
    Thread.sleep(15000L);
    /**
     * @see com.github.flysium.io.bucket.springboot.repository.OrderServiceImpl
     * 这里模拟一下这些数据
     */
    Assertions.assertThat(this.outputCapture.toString().contains("keys=msg-11")).isTrue();
    Assertions.assertThat(this.outputCapture.toString().contains("keys=msg-12")).isFalse();
    Assertions.assertThat(this.outputCapture.toString().contains("keys=msg-13")).isFalse();
  }

}