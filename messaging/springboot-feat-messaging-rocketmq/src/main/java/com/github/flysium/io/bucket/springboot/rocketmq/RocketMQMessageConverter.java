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

package com.github.flysium.io.bucket.springboot.rocketmq;

import org.apache.rocketmq.common.message.Message;

/**
 * Strategy interface that specifies a converter between Java objects and RocketMQ messages.
 *
 * @author Sven Augustus
 */
public interface RocketMQMessageConverter {

  /**
   * Convert a Java object to a RocketMQ Message  to create the message object.
   *
   * @param object Java object
   * @return RocketMQ Message
   */
  default Message toMessage(Object object) {
    return toMessage(null, object);
  }

  /**
   * Convert a Java object to a RocketMQ Message  to create the message object.
   *
   * @param keys keys
   * @param object Java object
   * @return RocketMQ Message
   */
  Message toMessage(String keys, Object object);

  /**
   * Convert from a RocketMQ Message to a Java object.
   *
   * @param message RocketMQ Message
   * @return Java object
   */
  Object fromMessage(Message message);

}
