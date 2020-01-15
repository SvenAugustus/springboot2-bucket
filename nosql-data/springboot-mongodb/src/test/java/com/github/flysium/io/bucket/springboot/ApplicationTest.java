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

package com.github.flysium.io.bucket.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link SampleMongoApplication}.
 *
 * @author Sven Augustus
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

  @ClassRule
  public static OutputCapture outputCapture = new OutputCapture();

  @Test
  public void testDefaultSettings() {
    String output = ApplicationTest.outputCapture.toString();
    assertThat(output).contains("name='Alice', age=18, sex='W'");
  }

}