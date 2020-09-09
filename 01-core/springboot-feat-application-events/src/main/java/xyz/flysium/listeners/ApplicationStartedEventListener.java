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

package xyz.flysium.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * <code>ApplicationStartedEvent</code> : 在刷新上下文之后但在调用任何应用程序和命令行运行程序之前发送该事件。
 *
 * @author Sven Augustus
 */
public class ApplicationStartedEventListener implements
    ApplicationListener<ApplicationStartedEvent> {


  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationPreparedEventListener.class);

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    LOGGER.error("应用上下文刷新完毕：" + applicationStartedEvent.getApplicationContext());
  }

}
