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

package kafka.embedded.test;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Kafka 嵌入测试
 *
 * @author Sven Augustus
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
// TODO 直接启动一个 Kafka Server服务，包含四个Broker节点。
@EmbeddedKafka(count = 4, ports = {9192, 9193, 9194, 9195})
// @EmbeddedKafka(brokerProperties = {"log.index.interval.bytes = 4096","num.io.threads = 8"})
// @EmbeddedKafka(brokerPropertiesLocation = "classpath:application.properties")
public class ApplicationTests {

  @Test
  public void contextLoads() throws IOException {
    System.in.read();
  }

}