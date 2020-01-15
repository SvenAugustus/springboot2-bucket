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

package sample.artemis.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.apache.activemq.artemis.jms.client.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * Artemis Configuration.
 *
 * @author Sven Augustus
 */
@Configuration
public class ArtemisMQConfig {

  private static final Logger logger = LoggerFactory.getLogger(ArtemisMQConfig.class);

  private final Environment environment;

  public ArtemisMQConfig(Environment environment) {
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
   * 支持 Artemis Topic 模式
   */
  @Bean
  public JmsListenerContainerFactory<?> topicJmsListenerContainer(
      ConnectionFactory mqConnectionFactory) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(mqConnectionFactory);
    factory.setPubSubDomain(true);
    // 设置为订阅持久化，即持久化消费进度
    factory.setSubscriptionDurable(true);
    // 消息消费端要指定ClientID，同一时刻只能有一个ClientID相同的消费者连接消费，所以如果有多个消费者，则ClientID不能相同。
    factory.setClientId(environment.getProperty("AMQ_CLIENT_ID", "brokerClientId1"));
    factory.setConcurrency("1");
    // JMS 规范的 ACK 消息确认机制有一下四种，定于在 Session 对象中：
    //  Session.AUTO_ACKNOWLEDGE = 1 ：自动确认 考虑与 optimizeACK 配合使用
    //  Session.CLIENT_ACKNOWLEDGE = 2：客户端手动确认
    //  Session.DUPS_OK_ACKNOWLEDGE = 3： 自动批量确认
    //  Session.SESSION_TRANSACTED = 0：事务提交并确认
    // 但是在 ActiveMQ 补充了一个自定义的 ACK 模式:
    // ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE = 4 ：单条消息确认
//    factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);

    return factory;
  }

}
