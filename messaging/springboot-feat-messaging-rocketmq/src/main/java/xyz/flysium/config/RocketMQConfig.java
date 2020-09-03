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

package xyz.flysium.config;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import xyz.flysium.support.rocketmq.RocketMQMessageConverter;
import xyz.flysium.util.KryoUtil;

/**
 * RocketMQ Configuration.
 *
 * @author Sven Augustus
 */
@Configuration
@EnableConfigurationProperties
public class RocketMQConfig {

  private final RocketMQProperties properties;

  public RocketMQConfig(RocketMQProperties properties) {
    this.properties = properties;
  }

  @Bean
  public RocketMQMessageConverter rocketMqMessageConverter() {
    return new RocketMQMessageConverter() {

      @Override
      public Message toMessage(String keys, Object object) {
        Message message = new Message(
            /* Topic */
            properties.getDefaultTopic(),
            /* Tag */
            properties.getDefaultTag(),
            /* Message body */
            KryoUtil.toBytes(object)
        );
        message.setKeys(keys);
        return message;
      }

      @Override
      public Object fromMessage(Message message) {
        return KryoUtil.fromBytes(message.getBody());
      }
    };
  }

  @Bean
  public DefaultMQProducer mqProducer() {
    // Instantiate with a producer group name.
    DefaultMQProducer producer = new DefaultMQProducer(properties.getProducerGroup());
    // Specify name server addresses.
    producer.setNamesrvAddr(properties.getNamesrvAddr());
    // FIXME 发送端的 send 方法本身支持内部重试，重试逻辑如下：
    //  a)至多重试2次；
    //  b)如果发送失败，则轮转到下一个broker；
    //  c)这个方法的总耗时不超过sendMsgTimeout 设置的值，默认 3s，超过时间不在重试。
    producer.setRetryTimesWhenSendFailed(2);
    producer.setRetryTimesWhenSendAsyncFailed(2);
    producer.setSendMsgTimeout(3000);
    return producer;
  }

  @Bean
  public TransactionMQProducer transactionMqProducer() {
    // Instantiate with a producer group name.
    TransactionMQProducer producer = new TransactionMQProducer(StringUtils
        .defaultString(properties.getTransactionProducerGroup(), properties.getProducerGroup()));
    // Specify name server addresses.
    producer.setNamesrvAddr(properties.getNamesrvAddr());
    // FIXME 发送端的 send 方法本身支持内部重试，重试逻辑如下：
    //  a)至多重试2次；
    //  b)如果发送失败，则轮转到下一个broker；
    //  c)这个方法的总耗时不超过sendMsgTimeout 设置的值，默认 3s，超过时间不在重试。
    producer.setRetryTimesWhenSendFailed(2);
    producer.setRetryTimesWhenSendAsyncFailed(2);
    producer.setSendMsgTimeout(3000);
    // FIXME 设置事务回查线程池
    producer.setExecutorService(
        new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new SynchronousQueue<>(),
            new CustomizableThreadFactory("MQ-transaction-check-"),
            new CallerRunsPolicy()));
    return producer;
  }

  @Bean
  public MQPushConsumer pushMqConsumer() throws MQClientException {
    // Instantiate with specified consumer group name.
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getConsumerGroup());
    // Specify name server addresses.
    consumer.setNamesrvAddr(properties.getNamesrvAddr());
    // set to broadcast/clustering mode
    // consumer.setMessageModel(MessageModel.CLUSTERING);
    // 消费进度策略： CONSUME_FROM_LAST_OFFSET : 忽略 Broker 存在的历史信息，从上次停下的地方继续消费
    //              CONSUME_FROM_FIRST_OFFSET ：每次都是从 Broker 最开始的消息开始消费
    //              CONSUME_FROM_TIMESTAMP： 从指定时间戳的消息之后开始消费
    // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    // Subscribe one more more topics to consume.
    consumer.subscribe(properties.getDefaultTopic(), "*");
    //    consumer.setConsumeMessageBatchMaxSize(1);
    // FIXME 线程数: 消费者使用一个 ThreadPoolExecutor 来处理内部的消费，
    //   因此您可以通过设置setConsumeThreadMin或setConsumeThreadMax来更改它。
    consumer.setConsumeThreadMin(2);
    consumer.setConsumeThreadMax(4);
    return consumer;
  }

}
