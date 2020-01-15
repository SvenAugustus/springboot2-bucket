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

package com.github.flysium.io.bucket.springboot.test;

import com.github.flysium.io.bucket.springboot.SampleExternalizedConfigurationApplication;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Configuration Tests.
 *
 * @author Sven Augustus
 */
@SpringBootTest(classes = {SampleExternalizedConfigurationApplication.class})
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:config/2-TestPropertySource.yml")
public class SpringTestConfiguration {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void test() throws InterruptedException, ExecutionException {
    logger.info("End.");
  }

}
