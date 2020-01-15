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

import com.github.flysium.io.bucket.springboot.model.UserInfo;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Producer.
 *
 * @author Sven Augustus
 */
@Component
public class DemoProducer {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final KafkaTemplate<String, UserInfo> kafkaTemplate;

  public DemoProducer(KafkaTemplate<String, UserInfo> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Send Messages Synchronously.
   */
  public Future<SendResult<String, UserInfo>> send(String key, UserInfo message) {
    return kafkaTemplate.send(MyMQConstants.TOPIC, key, message);
  }

  /**
   * Send Messages Asynchronously.
   */
  public void send(String key, UserInfo message,
      ListenableFutureCallback<SendResult<String, UserInfo>> callback) {
    kafkaTemplate.send(MyMQConstants.TOPIC, key, message).addCallback(callback);
  }

  /**
   * Send Messages in transactions.
   */
  public void sendInTransaction(String key, UserInfo message) {
    kafkaTemplate.executeInTransaction(t -> {

      kafkaTemplate.send(MyMQConstants.TOPIC, key, message);
      return true;
    });
  }

}
