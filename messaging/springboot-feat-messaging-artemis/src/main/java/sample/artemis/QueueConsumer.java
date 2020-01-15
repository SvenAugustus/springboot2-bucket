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

package sample.artemis;

import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sample.artemis.model.UserInfo;

/**
 * Copy from git@github.com:spring-projects/spring-oot.git
 *
 * @author Sven Augustus
 */
@Component
@Profile("queue-consumer")
public class QueueConsumer {

  @JmsListener(destination = "sample.queue")
  public void receiveQueue(String text) {
    System.out.println("receiveQueue==" + text);
  }

  @JmsListener(destination = "sample.object.queue")
  public void receiveQueue(UserInfo userInfo) {
    System.out.println("receiveQueue==" + userInfo);
  }

}
