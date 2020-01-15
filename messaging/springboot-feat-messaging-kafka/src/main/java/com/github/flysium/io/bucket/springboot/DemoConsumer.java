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
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Consumer.
 *
 * @author Sven Augustus
 */
@Component
@Profile("consumer")
public class DemoConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(DemoConsumer.class);

  @KafkaListener(containerFactory = "kafkaAckListenerContainerFactory", id = "listener-1", groupId = "webGroup", topics = MyMQConstants.TOPIC)
  //      @KafkaListener(id = "webGroup", topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
//                    @TopicPartition(topic = "topic2", partitions = "0",
//                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
//            },concurrency = "6",errorHandler = "myErrorHandler")
  // TODO 消息转发: 一个消息需要做多重加工，不同的加工耗费的cup等资源不一致，那么就可以通过跨不同Topic和部署在不同主机上的consumer来解决了，使用 dTo 注解。
  //  @SendTo(KafkaProperties.TOPIC2)
  public void batch(List<ConsumerRecord<String, UserInfo>> data,
      Acknowledgment acknowledgment) {
    LOGGER.info("====== Received messages size: " + data.size());
    data.forEach(record -> {
      System.out.println(
          "==== Received message: (" + record.key() + ", " + record.value()
              + ") at topic(" + record.topic() + "), partition(" + record.partition()
              + "), offset(" + record.offset() + "), timestamp(" + record.timestamp() + ")");
    });
    // TODO 消息确认
    acknowledgment.acknowledge();
  }

  @KafkaListener(id = "dlt-listener-1", groupId = "dltGroup", topics = MyMQConstants.TOPIC + ".DLT")
  public void dltListen(UserInfo userInfo) {
    LOGGER.info("====== Received from DLT: " + userInfo);
  }

}
