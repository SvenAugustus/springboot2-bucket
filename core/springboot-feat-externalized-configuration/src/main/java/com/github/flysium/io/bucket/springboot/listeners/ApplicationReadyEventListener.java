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

import com.github.flysium.io.bucket.springboot.config.AppIoProperties;
import com.github.flysium.io.bucket.springboot.config.AppSystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * <code>ApplicationReadyEvent</code> : 应用准备好提供服务了。
 *
 * @author Sven Augustus
 */
public class ApplicationReadyEventListener implements
    ApplicationListener<ApplicationReadyEvent> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationReadyEventListener.class);

  @Override
  public void onApplicationEvent(
      ApplicationReadyEvent readyEvent) {
    Environment env = readyEvent.getApplicationContext().getEnvironment();

    System.setProperty("externalized-configuration-test-cover",
        "9-Java System properties (System.getProperties())");
    System
        .setProperty("externalized-configuration-9-Java System properties (System.getProperties())",
            "9-Java System properties (System.getProperties())");

    logger(env, "？被覆盖的变量：", "externalized-configuration-test-cover");
    logger(env, "？未覆盖的变量：", "externalized-configuration-17-SpringApplication.setDefaultProperties");
    logger(env, "？未覆盖的变量：", "externalized-configuration-16-Configuration-PropertySource");
    logger(env, "？未覆盖的变量：", "externalized-configuration-15-application.properties-YAML");
    logger(env, "？未覆盖的变量：", "externalized-configuration-14-application.properties-YAML");
    logger(env, "？未覆盖的变量：", "externalized-configuration-13-application-{profile}.properties-YAML");
    logger(env, "？未覆盖的变量：", "externalized-configuration-12-application-{profile}.properties-YAML");
    logger(env, "？未覆盖的变量：",
        "externalized-configuration-9-Java System properties (System.getProperties())");
    logger(env, "？未覆盖的变量：",
        "externalized-configuration-2-@TestPropertySource");

    AppSystemProperties appSystemProperties = readyEvent.getApplicationContext()
        .getBean(AppSystemProperties.class);
    LOGGER.error("属性转换1：" + appSystemProperties.getSessionTimeout());
    LOGGER.error("属性转换2：" + appSystemProperties.getReadTimeout());

      AppIoProperties appIoProperties = readyEvent.getApplicationContext()
        .getBean(AppIoProperties.class);
    LOGGER.error("属性转换3：" + appIoProperties.getBufferSize());
    LOGGER.error("属性转换4：" + appIoProperties.getSizeThreshold());

  }

  private void logger(Environment env, String prefix, String key) {
    LOGGER.error(prefix + env.getProperty(key));
  }

}
