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

import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumer.
 *
 * @author Sven Augustus
 */
@Component
@Profile("consumer")
public class Consumer {

  @RabbitListener(queues = MyMQConstants.QUEUE_NAME_2)
  @RabbitHandler
  public void processMessage(Channel channel, Message message, @Payload UserInfo content)
      throws IOException {
    System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), content);
    // TODO 消息确认
    //  RabbitMQ 会为未确认的消息设置过期时间，它判断此消息是否需要重新投递给消费者的。
    //  唯一依据是消费该消息的消费者连接是否己经断开，这么设计的原因是 RabbitMQ 允许消费者 消费一条消息的时间可以很久很久。
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    channel.basicAck(deliveryTag, false);
    // TODO 消息拒绝，消息被拒绝 (Basic.Reject/Basic.Nack) ，井且设置 requeue 参数为 false, 会被进入死信队列（DLX）;
    // channel.basicReject(deliveryTag, true);
    // TODO 批量拒绝：multiple 参数设置为 true 则表示拒绝 deliveryTag 编号之前所有未被当前消费者确认的消息。
    // channel.basicNack(deliveryTag, true, true);
  }

}
