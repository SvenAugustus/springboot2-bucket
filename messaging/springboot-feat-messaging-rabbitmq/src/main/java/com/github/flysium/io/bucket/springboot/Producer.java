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

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Producer.
 *
 * @author Sven Augustus
 */
@Component
public class Producer {

  private final RabbitMessagingTemplate rabbitMessagingTemplate;

  public Producer(RabbitMessagingTemplate rabbitMessagingTemplate) {
    this.rabbitMessagingTemplate = rabbitMessagingTemplate;
  }

  public void send(String routingKey, Object object) {
    // Call send message to deliver message to one of brokers.
    // 这里默认是持久化消息，参考 MessageProperties
    rabbitMessagingTemplate.convertAndSend(MyMQConstants.EXCHANGE_TOPIC, routingKey, object);
    // TODO ACK机制由 application.yml 配置，并回调 在 RabbitTemplate 中配置
  }

  public void sendNative(String routingKey, Object object) {
    MessageProperties properties = MessagePropertiesBuilder.newInstance()
//        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)  // 默认是持久化消息
        .setContentEncoding("UTF-8")
        // FIXME 设置 TTL, 单位为毫秒
        .setExpiration("6000")
        .build();
    Message message = rabbitMessagingTemplate.getAmqpMessageConverter()
        .toMessage(MessageBuilder.withPayload(object).build(),
            properties);

    rabbitMessagingTemplate.getRabbitTemplate().send(MyMQConstants.EXCHANGE_TOPIC, routingKey,
        message);
  }

}
