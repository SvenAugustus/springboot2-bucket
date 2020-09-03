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

package xyz.flysium.config;

import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步线程池配置
 *
 * @author Sven Augustus
 * @version 1.0
 * @since JDK 1.8
 */
@EnableAsync
@Configuration
class AsyncConfig implements AsyncConfigurer {

  private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

  private final ExecutorProperties executorProperties;

  public AsyncConfig(ExecutorProperties executorProperties) {
    this.executorProperties = executorProperties;
  }

  @Override
  @Bean
  public AsyncTaskExecutor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix(executorProperties.getThreadNamePrefix());
    executor.setCorePoolSize(executorProperties.getCorePoolSize());
    executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
    executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
    executor.setQueueCapacity(executorProperties.getQueueCapacity());
    executor.setTaskDecorator(new LoggerTaskDecorator());
    // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // 线程池在关闭的时候等待任务完成
    executor.setWaitForTasksToCompleteOnShutdown(true);
    // 如果不初始化，导致找到不到执行器
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> {
      LOGGER.error("Async method has uncaught exception, params:{}" + Arrays.toString(params));
      LOGGER.error("Async Exception :", ex);
    };
  }

}
