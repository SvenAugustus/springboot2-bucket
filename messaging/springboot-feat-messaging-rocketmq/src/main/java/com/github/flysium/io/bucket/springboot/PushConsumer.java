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

import com.github.flysium.io.bucket.springboot.rocketmq.RocketMQMessageConverter;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Push Consumer.
 *
 * @author Sven Augustus
 */
@Component
@Profile("consumer")
public class PushConsumer {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final RocketMQMessageConverter messageConverter;
  private final MQPushConsumer pushConsumer;

  public PushConsumer(RocketMQMessageConverter messageConverter, MQPushConsumer pushConsumer)
      throws MQClientException {
    this.messageConverter = messageConverter;
    this.pushConsumer = pushConsumer;
  }

  @Component
  @Profile("concurrently")
  class Concurrently {

    private Concurrently() {
      // Register callback to execute on arrival of messages fetched from brokers.
      PushConsumer.this.pushConsumer
          .registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            try {
              // FIXME 许多情况可能导致重复，例如：E
              //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
              //    消费者关闭时未将offsets 更新到 Broker
              //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。
              for (MessageExt msg : msgs) {
                // Print approximate delay time period
                System.out.println(
                    "Receive message[msgId=" + msg.getMsgId() + ",keys=" + msg.getKeys() + "] "
                        + (System.currentTimeMillis() - msg.getStoreTimestamp()) + "ms later");
              }
              List<Object> objects = msgs.stream()
                  .map(PushConsumer.this.messageConverter::fromMessage).collect(
                      Collectors.toList());
              System.out.printf("====== %s Receive New Messages: %s %n",
                  Thread.currentThread().getName(), objects);
              return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (RuntimeException e) {
              // FIXME: 并行（Concurrently）
              //  顾名思义，消费者将同时使用这些消息。为良好的性能，推荐使用此方法。
              //  不建议抛出异常，您可以返回ConsumeConcurrentlyStatus.RECONSUME_LATER代替。
              return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
          });
    }
  }

  @Component
  @Profile("orderly")
  private class Orderly {

    private Orderly() {
      // Register callback to execute on arrival of messages fetched from brokers.
      PushConsumer.this.pushConsumer
          .registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            context.setAutoCommit(false);
            try {
              // FIXME 许多情况可能导致重复，例如：E
              //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
              //    消费者关闭时未将offsets 更新到 Broker
              //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。
              List<Object> objects = msgs.stream()
                  .map(PushConsumer.this.messageConverter::fromMessage).collect(
                      Collectors.toList());
              System.out.printf("====== %s Receive New Ordered Messages: %s %n",
                  Thread.currentThread().getName(), objects);
              return ConsumeOrderlyStatus.SUCCESS;
            } catch (RuntimeException e) {
              // FIXME: 消费者将锁定每个MessageQueue，以确保每个消息被一个按顺序使用。
              //  这将导致性能损失，但是当您关心消息的顺序时，它就很有用了。
              //  不建议抛出异常，您可以返回ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT代替。
              context.setSuspendCurrentQueueTimeMillis(3000);
              return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
          });
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public void ready() {
    // Launch the consumer instance.
    try {
      this.pushConsumer.start();
    } catch (MQClientException e) {
      throw new RuntimeException("启动[消息消费者]" + this.getClass().getName() + "  异常", e);
    }
    Runtime.getRuntime().addShutdownHook(new Thread(this.pushConsumer::shutdown));
    LOGGER.info("启动[消息消费者]" + this.getClass().getName() + " 成功");
  }

}
