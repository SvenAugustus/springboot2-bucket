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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RocketMQ Properties.
 *
 * @author Sven Augustus
 */
@Component
@ConfigurationProperties(prefix = "apache.rocketmq")
public class RocketMQProperties {

  /**
   * Name Server Address.
   */
  private String namesrvAddr;
  /**
   * Topic.
   */
  private String defaultTopic;
  /**
   * Tag.
   */
  private String defaultTag;
  /**
   * The Producer Group
   */
  private String producerGroup;
  /**
   * The Producer Group
   */
  private String transactionProducerGroup;
  /**
   * The Consumer Group
   */
  private String consumerGroup;

  public String getNamesrvAddr() {
    return namesrvAddr;
  }

  public void setNamesrvAddr(String namesrvAddr) {
    this.namesrvAddr = namesrvAddr;
  }

  public String getDefaultTopic() {
    return defaultTopic;
  }

  public void setDefaultTopic(String defaultTopic) {
    this.defaultTopic = defaultTopic;
  }

  public String getDefaultTag() {
    return defaultTag;
  }

  public void setDefaultTag(String defaultTag) {
    this.defaultTag = defaultTag;
  }

  public String getProducerGroup() {
    return producerGroup;
  }

  public void setProducerGroup(String producerGroup) {
    this.producerGroup = producerGroup;
  }

  public String getTransactionProducerGroup() {
    return transactionProducerGroup;
  }

  public void setTransactionProducerGroup(String transactionProducerGroup) {
    this.transactionProducerGroup = transactionProducerGroup;
  }

  public String getConsumerGroup() {
    return consumerGroup;
  }

  public void setConsumerGroup(String consumerGroup) {
    this.consumerGroup = consumerGroup;
  }
}
