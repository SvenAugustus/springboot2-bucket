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

package com.github.flysium.io.bucket.springboot.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * <code>ApplicationReadyEvent</code> : 应用准备好提供服务了。
 *
 * @author Sven Augustus
 */
public class ApplicationReadyEventListener implements
    ApplicationListener<ApplicationReadyEvent> {


  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationPreparedEventListener.class);

  @Override
  public void onApplicationEvent(ApplicationReadyEvent readyEvent) {
    LOGGER.error("应用已经准备好：" + readyEvent.getApplicationContext());
  }

}
