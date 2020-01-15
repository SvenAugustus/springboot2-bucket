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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskDecorator;

/**
 * TaskDecorator for Logging the time。
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.8
 */
class LoggerTaskDecorator implements TaskDecorator {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTaskDecorator.class);

  @Override
  public Runnable decorate(Runnable runnable) {
    return () -> {
      long t = System.currentTimeMillis();
      LOGGER.debug("Thread: {} starts processing...", Thread.currentThread().getName());
      try {
        runnable.run();
      } finally {
        LOGGER.debug("Thread: {} ends processing, and takes time:  {} ms.",
            Thread.currentThread().getName(), (System.currentTimeMillis() - t));
      }
    };
  }

}
