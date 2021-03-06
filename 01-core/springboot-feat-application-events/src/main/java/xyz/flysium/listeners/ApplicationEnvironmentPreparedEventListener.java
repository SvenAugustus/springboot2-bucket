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

package xyz.flysium.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * <code>ApplicationEnvironmentPreparedEvent</code>: 当已知`Environment`在上下文创建前使用时发送该事件。
 *
 * @author Sven Augustus
 */
public class ApplicationEnvironmentPreparedEventListener implements
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationEnvironmentPreparedEventListener.class);

  @Override
  public void onApplicationEvent(
      ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
    LOGGER.error("应用环境变量已经准备完毕，环境变量：" + applicationEnvironmentPreparedEvent.getEnvironment());
  }

}
