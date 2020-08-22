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

package sample.activemq;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.flysium.io.bucket.springboot.QueueProducer;
import com.github.flysium.io.bucket.springboot.TopicProducer;
import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for demo application.
 *
 * @author Eddú Meléndez
 * @author Sven Augustus
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleActiveMqTests {

  private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  @Rule
  public OutputCaptureRule outputCapture = new OutputCaptureRule();

  @Autowired
  private QueueProducer queueProducer;

  @Autowired
  private TopicProducer topicProducer;

  @Test
  public void sendSimpleMessageToQueue() throws InterruptedException {
    String now = FORMAT.format(LocalDateTime.now());
    this.queueProducer.send("Test message " + now);
    Thread.sleep(1000L);
    assertThat(this.outputCapture.toString().contains("Test message " + now)).isTrue();
  }

  @Test
  public void sendObjectMessageToQueue() throws InterruptedException {
    String now = FORMAT.format(LocalDateTime.now());
    this.queueProducer.send(new UserInfo(now, "Jack", 28));
    Thread.sleep(1000L);
    assertThat(this.outputCapture.toString().contains("UserInfo{id='" + now)).isTrue();
  }

  @Test
  public void sendObjectMessageToTopic() throws InterruptedException {
    Thread.sleep(3000L); // wait for consumer startup first on persistent case. (Test only)
    String now = FORMAT.format(LocalDateTime.now());
    this.topicProducer.send(new UserInfo(now, "Alice", 18));
    Thread.sleep(1000L);
    assertThat(this.outputCapture.toString().contains("UserInfo{id='" + now)).isTrue();
  }

}
