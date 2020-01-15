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

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.ProducerListener;

/**
 * Kafka Configuration.
 *
 * @author Sven Augustus
 */
@Configuration
public class KafkaProducerConfig {

  @Bean
  public ProducerListener<?, ?> producerListener() {
    return new ProducerListener<Object, Object>() {

      @Override
      public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        System.out.println(
            "====== Sent message: (" + producerRecord.key() + ", " + producerRecord.value() + ")");
      }

      @Override
      public void onError(ProducerRecord producerRecord, Exception exception) {
        if (producerRecord != null) {
          System.err.println(
              "====== Sent message Failed: (" + producerRecord.key() + ", " + producerRecord.value()
                  + ") sent to partition(" + producerRecord
                  .partition() + "), " + "timestamp(" + producerRecord.timestamp() + ")  ");
        } else {
          exception.printStackTrace();
        }
      }
    };
  }

}
