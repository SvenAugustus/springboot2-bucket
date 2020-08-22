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

package com.github.flysium.io.bucket.springboot.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentBatchErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Kafka Configuration.
 *
 * @author Sven Augustus
 */
@Configuration
@Profile("consumer")
public class KafkaConsumerConfig {

  @Bean
  @SuppressWarnings({"unchecked", "raw-types"})
  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaAckListenerContainerFactory(
      ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
      ConsumerFactory<?, ?> kafkaConsumerFactory,
      KafkaTemplate<Object, Object> template) {
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
    // 设置消费者工厂
    factory.setConsumerFactory(kafkaConsumerFactory);
    // 消费者组中线程数量
    factory.setConcurrency(3);
    // 拉取超时时间
    factory.getContainerProperties().setPollTimeout(3000);
    // TODO 消息重试和死信队列: 触发运行时异常，然后监听器会尝试 maxFailures 次调用，当到达最大的重试次数后。
    //    消息就会被丢掉重试死信队列里面去。死信队列的Topic的规则是，业务Topic名字+“.DLT”。
    factory
        .setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template),
            new FixedBackOff(5000, 1)));
    // TODO 开启批量消费监听器
    factory.setBatchListener(true);
    factory.setBatchErrorHandler(new SeekToCurrentBatchErrorHandler());
    // TODO 设置ACK模式为 MANUAL
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }

}
