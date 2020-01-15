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

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * <code>ApplicationStartingEvent</code> : 在运行开始时但在任何处理（除了接受监听器和初始化程序的注册）之前发送该事件
 *
 * @author Sven Augustus
 */
public class ApplicationStartingEventListener implements
    ApplicationListener<ApplicationStartingEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
    System.err.println(
        "应用开始启动。时间戳：" + applicationStartingEvent.getTimestamp()
            + ", 启动类：" + applicationStartingEvent.getSpringApplication().getAllSources()
            + ", 启动参数：[" + StringHelper.join(applicationStartingEvent.getArgs(), ",") + "]");
    // 此处不能使用LOGGER, 因为尚未初始化LOGGER
  }

}
