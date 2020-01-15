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

import com.github.flysium.io.bucket.springboot.rocketmq.ListSplitter;
import com.github.flysium.io.bucket.springboot.rocketmq.RocketMQMessageConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Producer.
 *
 * @author Sven Augustus
 */
@Component
public class Producer {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final RocketMQMessageConverter messageConverter;
  private final DefaultMQProducer producer;

  public Producer(RocketMQMessageConverter messageConverter,
      @Qualifier("mqProducer") DefaultMQProducer producer) {
    this.messageConverter = messageConverter;
    this.producer = producer;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    // Launch the instance.
    try {
      producer.start();
    } catch (MQClientException e) {
      throw new RuntimeException("启动[消息生产者]" + this.getClass().getName() + " 异常", e);
    }
    Runtime.getRuntime().addShutdownHook(new Thread(this.producer::shutdown));
    LOGGER.info("启动[消息生产者]" + this.getClass().getName() + " 成功");
  }

  /**
   * Send Messages Synchronously.
   *
   * @param object 我们建议的消息的大小应该不超过 512 K。
   */
  public SendResult send(Object object)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    return send(object, -1);
  }

  /**
   * Send Messages Synchronously.
   *
   * @param object 我们建议的消息的大小应该不超过 512 K。
   * @param delayTimeLevel 如果需要延时定期消息，请设置大于0的数值，对应 Broker端 messageDelayLevel 配置项
   */
  public SendResult send(Object object, int delayTimeLevel)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    return send(Collections.singletonList(object), delayTimeLevel).stream().findFirst()
        .orElse(null);
  }

  /**
   * Sending messages in batch improves performance of delivering small messages.
   *
   * Besides, the total size of the messages in one batch should be no more than 1MiB.
   */
  public List<SendResult> send(Collection<?> objects, int delayTimeLevel)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    // Create a message instance, specifying topic, tag and message body.
    List<Message> msgs = objects.stream().map(messageConverter::toMessage)
        .collect(Collectors.toList());
    if (delayTimeLevel > 0) {
      // Broker端 可以设置 messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
      msgs.forEach(m -> m.setDelayTimeLevel(delayTimeLevel));
    }
    List<SendResult> results = new ArrayList<>();
    ListSplitter listSplitter = new ListSplitter(msgs);
    while (listSplitter.hasNext()) {
      List<Message> messages = listSplitter.next();
      // Call send message to deliver message to one of brokers.
      SendResult sendResult =
          objects.size() == 1 ? producer.send(msgs.get(0)) : producer.send(msgs);
      // 设置发送超时时间： The default wait time is 3 seconds.
//    SendResult sendResult =producer.send(msgs, 5000);
      if (SendStatus.FLUSH_DISK_TIMEOUT.equals(sendResult.getSendStatus())
          || SendStatus.FLUSH_SLAVE_TIMEOUT.equals(sendResult.getSendStatus())) {
        //  FIXME 重复或丢失
        //    如果您得到FLUSH_DISK_TIMEOUT、FLUSH_SLAVE_TIMEOUT并且 Broker 恰好在此时意外宕机，您会发现你的消息丢失。
        //    此时，您有两个选择，一个是不管它，这可能导致这个消息丢失；另一个是重新发送消息，这可能会导致消息重复。
        //    我们经常建议重新发送，然后再消费时使用某个方法移除重复的消息。除非你觉得一些信息丢失并不重要。
        //    但是请记住，当您得到 SLAVE_NOT_AVAILABLE 状态时，重新发送是没有用的。
        //    如果出现这种情况，您应该保存场景并通知集群管理员 （Cluster Manager）。
        System.err.println("Send Failed, status=" + sendResult.getSendStatus() + ", data=" + objects);
      }
      results.add(sendResult);
    }
    return results;
  }

  /**
   * Send Messages Asynchronously.
   */
  public void send(Object object, SendCallback sendCallback)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    // Create a message instance, specifying topic, tag and message body.
    Message msg = messageConverter.toMessage(object);
    producer.send(msg, sendCallback);
  }

  /**
   * Send Messages in One-way Mode.（this method won't wait for acknowledgement from broker before
   * return. Obviously, it has maximums throughput yet potentials of message loss.）
   */
  public void sendOneway(Object object)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    // Create a message instance, specifying topic, tag and message body.
    Message msg = messageConverter.toMessage(object);
    producer.sendOneway(msg);
  }

}
