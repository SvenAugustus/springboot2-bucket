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

package xyz.flysium;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.flysium.dao.entity.OrderInfo;
import xyz.flysium.dao.entity.UserInfo;

/**
 * Push Consumer.
 *
 * @author Sven Augustus
 */
@Component
@Profile("consumer")
public class PushConsumer {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Component
  @RocketMQMessageListener(topic = MyRocketMQConstants.TOPIC, selectorExpression = "*", consumerGroup = MyRocketMQConstants.CONSUMER_GROUP1,
      consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 4)
  static class MyRocketMQListener implements RocketMQListener<UserInfo> {

    @Override
    public void onMessage(UserInfo userInfo) {
      //  FIXME 许多情况可能导致重复，例如：E
      //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
      //    消费者关闭时未将offsets 更新到 Broker
      //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。
      System.out.printf("====== %s Receive New Messages: %s %n",
          Thread.currentThread().getName(), userInfo);
    }
  }

  @Component
  @RocketMQMessageListener(topic = MyRocketMQConstants.TOPIC_2, consumerGroup = MyRocketMQConstants.CONSUMER_GROUP2,
      consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 4)
  static class OrderRocketMQListener implements RocketMQListener<OrderInfo>,
      RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(OrderInfo orderInfo) {
      //  FIXME 许多情况可能导致重复b，例如：E
      //    生产者重新发送消息（i.e, in case of FLUSH_SLAVE_TIMEOUT）
      //    消费者关闭时未将offsets 更新到 Broker
      //    因此，如果您的应用程序不能容忍重复，那么您可能需要做一些外部工作来处理这个问题。例如，您可以检查DB的主键。
      System.out.printf("====== %s Receive New Messages: %s %n",
          Thread.currentThread().getName(), orderInfo);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
      // set to broadcast/clustering mode
      consumer.setMessageModel(MessageModel.CLUSTERING);
      // 消费进度策略： CONSUME_FROM_LAST_OFFSET : 忽略 Broker 存在的历史信息，从上次停下的地方继续消费
      //              CONSUME_FROM_FIRST_OFFSET ：每次都是从 Broker 最开始的消息开始消费
      //              CONSUME_FROM_TIMESTAMP： 从指定时间戳的消息之后开始消费
      consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

      // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
      // consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
  }
//
//  @EventListener(ApplicationReadyEvent.class)
//  @Order(Ordered.HIGHEST_PRECEDENCE)
//  public void ready() {
//    // Launch the consumer instance.
//    try {
//      this.pushConsumer.start();
//    } catch (MQClientException e) {
//      throw new RuntimeException("启动[消息消费者]" + this.getClass().getName() + "  异常", e);
//    }
//    Runtime.getRuntime().addShutdownHook(new Thread(this.pushConsumer::shutdown));
//    LOGGER.info("启动[消息消费者]" + this.getClass().getName() + " 成功");
//  }

}
