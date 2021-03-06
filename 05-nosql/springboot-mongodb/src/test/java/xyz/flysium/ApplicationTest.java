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

package xyz.flysium;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link MongoSampleApplication}.
 *
 * @author Sven Augustus
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @ClassRule
    public static OutputCaptureRule outputCapture = new OutputCaptureRule();

    @Test
    public void testDefaultSettings() {
        String output = ApplicationTest.outputCapture.toString();
        assertThat(output).contains("name='Alice', age=18, sex='W'");
    }

}