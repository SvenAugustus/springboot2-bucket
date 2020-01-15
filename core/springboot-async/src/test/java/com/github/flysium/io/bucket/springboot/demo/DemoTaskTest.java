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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.flysium.io.bucket.springboot.SampleAsyncApplication;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demo Task Unit Tests
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootTest(classes = {
    SampleAsyncApplication.class}/*, webEnvironment = WebEnvironment.RANDOM_PORT*/)
@RunWith(SpringRunner.class)
public class DemoTaskTest {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private DemoTask demoTask;

  @Test
  public void testAsync() throws InterruptedException, ExecutionException {
    demoTask.dealNoReturnTask();

    Future<String> f = demoTask.dealHaveReturnTask(5);

    logger.info("主线程执行finished");

    logger.info(f.get());
    assertThat(f.get(), is("success:" + 5));
  }

}