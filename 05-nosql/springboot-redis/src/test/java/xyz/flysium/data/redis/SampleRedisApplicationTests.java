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

package xyz.flysium.data.redis;

import static org.assertj.core.api.Assertions.assertThat;

import io.lettuce.core.RedisClient;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.data.redis.RedisConnectionFailureException;
import xyz.flysium.RedisSampleApplication;

/**
 * Tests for {@link RedisClient}.
 *
 * @author Dave Syer
 */
public class SampleRedisApplicationTests {

	@Rule
	public OutputCaptureRule outputCapture = new OutputCaptureRule();

	@Test
	public void testDefaultSettings() {
		try {
      RedisSampleApplication.main(new String[0]);
    }
		catch (Exception ex) {
			if (!redisServerRunning(ex)) {
				return;
			}
		}
		String output = this.outputCapture.toString();
		assertThat(output).contains("Found key spring.boot.redis.test");
	}

	private boolean redisServerRunning(Throwable ex) {
		System.out.println(ex.getMessage());
		if (ex instanceof RedisConnectionFailureException) {
			return false;
		}
		return (ex.getCause() == null || redisServerRunning(ex.getCause()));
	}

}
