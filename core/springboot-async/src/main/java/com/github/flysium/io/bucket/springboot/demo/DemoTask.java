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

package com.github.flysium.io.bucket.springboot.demo;

import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * Demo Task
 *
 * @author Sven Augustus
 * @version 1.0
 */
@Component
public class DemoTask {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Async
  public void dealNoReturnTask() {
    logger.info("返回值为void的异步调用开始" + Thread.currentThread().getName());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("返回值为void的异步调用结束" + Thread.currentThread().getName());
  }

  @Async
  public Future<String> dealHaveReturnTask(int i) {
    logger.info("asyncInvokeReturnFuture, parementer=" + i);
    Future<String> future;
    try {
      Thread.sleep(1000 * i);
      future = new AsyncResult<String>("success:" + i);
    } catch (InterruptedException e) {
      future = new AsyncResult<String>("error");
    }
    return future;
  }

}
