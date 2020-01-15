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

/**
 * MQ 常量
 *
 * @author Sven Augustus
 */
public class MyMQConstants {

  public static final String EXCHANGE_DIRECT = "springboot.direct.exchange";
  public static final String EXCHANGE_TOPIC = "springboot.topic.exchange";
  public static final String EXCHANGE_FANOUT = "springboot.fanout.exchange";
  public static final String EXCHANGE_HEADER = "springboot.header.exchange";

  public static final String QUEUE_NAME_1 = "springboot.queue.1";
  public static final String QUEUE_NAME_2 = "springboot.queue.2";
  public static final String QUEUE_NAME_3 = "springboot.queue.3";
  public static final String QUEUE_NAME_4 = "springboot.queue.4";

  public final static String BINDING_KEY_D = "spring.boot.key.user";
  public final static String BINDING_KEY_T = "spring.boot.key.#";

}