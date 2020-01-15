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

package com.github.flysium.io.bucket.springboot.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * ActiveMQ Configuration.
 *
 * @author Sven Augustus
 */
@Configuration
public class ActiveMQConfig {

  private static final Logger logger = LoggerFactory.getLogger(ActiveMQConfig.class);

  private final Environment environment;

  public ActiveMQConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("sample.queue");
  }

  @Bean
  public Queue objectQueue() {
    return new ActiveMQQueue("sample.object.queue");
  }

  @Bean
  public Topic topic() {
    return new ActiveMQTopic("sample.topic");
  }

  /**
   * 支持 ActiveMQ Topic 模式
   */
  @Bean
  public JmsListenerContainerFactory<?> topicJmsListenerContainer(
      ConnectionFactory mqConnectionFactory) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(mqConnectionFactory);
    factory.setPubSubDomain(true);
    factory.setSessionTransacted(false);
    // 设置为订阅持久化，即持久化消费进度
    factory.setSubscriptionDurable(true);
    factory.setConcurrency("1");
    // JMS 规范的 ACK 消息确认机制有一下四种，定于在 Session 对象中：
    //  Session.AUTO_ACKNOWLEDGE = 1 ：自动确认 考虑与 optimizeACK 配合使用
    //  Session.CLIENT_ACKNOWLEDGE = 2：客户端手动确认
    //  Session.DUPS_OK_ACKNOWLEDGE = 3： 自动批量确认
    //  Session.SESSION_TRANSACTED = 0：事务提交并确认
    // 但是在 ActiveMQ 补充了一个自定义的 ACK 模式:
    // ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE = 4 ：单条消息确认
    factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);

    return factory;
  }

  /**
   * ActiveMQ 连接工厂
   */
  @Bean
  public ActiveMQConnectionFactoryCustomizer mqConnectionFactoryCustomizer(
      ActiveMQPrefetchPolicy prefetchPolicy,
      RedeliveryPolicy redeliveryPolicy) {
    return new ActiveMQConnectionFactoryCustomizer() {

      @Override
      public void customize(ActiveMQConnectionFactory factory) {
        // 消息消费端要指定ClientID，同一时刻只能有一个ClientID相同的消费者连接消费，所以如果有多个消费者，则ClientID不能相同。
        factory.setClientID(environment.getProperty("AMQ_CLIENT_ID", "brokerClientId1"));
        factory.setPrefetchPolicy(prefetchPolicy);
        factory.setRedeliveryPolicy(redeliveryPolicy);
        // 生产者策略：同步还是异步发送消息（消息发送后发送方不会同步等待 Message在服务端的任何回执。）
        // 相关的策略配置是 RedeliveryPolicy
        factory.setUseAsyncSend(true);
        // 同样设置消息发送者在累计发送 102400 byte大小的消息后
        // 等待服务端进行回执,以便确定之前发送的消息是否被正确处理
        // 确定服务器端是否产生了过量的消息堆积，需要减慢消息生产端的生产速度
        factory.setProducerWindowSize(102400);
        // 消费者策略：默认情况下ActiveMQ服务端采用异步方式 向客户端推送消息。
        // 也就是说ActiveMQ服务端在向某个消费者会话推送消息后，不会等待消费者的响应信息，直到消费者处理完消息后，主动向服务端返回处理结果。
        // factory.setDispatchAsync(true);
        // 延迟ACK: 考虑与 Session.AUTO_ACKNOWLEDGE 配合使用
        // Session.AUTO_ACKNOWLEDGE 情况下， “延迟确认”的数量阀值：prefetch * 0.65 “延迟确认”的时间阀值：> optimizeAcknowledgeTimeOut
        factory.setOptimizeAcknowledge(true);
        logger.info("\n\n" + "=========================================================\n"
            + "Using clientID : " + factory.getClientID() + "\n"
            + "=========================================================\n\n");
      }
    };
  }

  /**
   * ActiveMQ 批量消费策略
   *
   * @see <a>http://activemq.apache.org/what-is-the-prefetch-limit-for.html</a>
   */
  @Bean
  public ActiveMQPrefetchPolicy prefetchPolicy() {
    ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
    prefetchPolicy.setTopicPrefetch(100);
    // TODO
    return prefetchPolicy;
  }

  /**
   * ActiveMQ 消息重发策略
   */
  @Bean
  public RedeliveryPolicy redeliveryPolicy() {
    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
    // 是否在每次尝试重新发送失败后,增长这个等待时间
    redeliveryPolicy.setUseExponentialBackOff(true);
    // 重发次数,默认为6次   这里设置为 2 次
    redeliveryPolicy.setMaximumRedeliveries(2);
    // 重发时间间隔,默认为 1000毫秒（1秒）
    redeliveryPolicy.setInitialRedeliveryDelay(1000);
    // 第一次失败后重新发送之前等待500毫秒, 第二次失败再等待500 * 2毫秒, 这里的2就是value
    redeliveryPolicy.setBackOffMultiplier(2);
    // 是否避免消息碰撞
    redeliveryPolicy.setUseCollisionAvoidance(false);
    // 设置重发最大拖延时间-1 表示没有拖延， 只有UseExponentialBackOff(true)为true时生效
    // 假设首次重连间隔为10ms，倍数为2，那么第二次重连时间间隔为 20ms，
    //			第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，
    //			以后每次重连时间间隔都为最大重连时间间隔。
    redeliveryPolicy.setMaximumRedeliveryDelay(-1);
    return redeliveryPolicy;
  }

}
