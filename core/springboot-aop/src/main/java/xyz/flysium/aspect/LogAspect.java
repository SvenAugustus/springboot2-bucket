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

package xyz.flysium.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Logger Aspect.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

  /**
   * 切点表达式
   */
  @Pointcut("execution(public * com.github.flysium.io.bucket.springboot.controller.*.*(..))")
  public void func() {
  }

  /**
   * 在方法执行前运行
   */
  @Before("func()")
  public void doBefore(JoinPoint joinPoint) {
    LOGGER.info(">>> doBefore...");
    // request info
    LOGGER.info(
        "METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint
            .getSignature().getName() + "(" + Arrays.toString(joinPoint.getArgs()) + ")");
  }

  /**
   * 在两种场景下执行 - 当一个方法成功执行或是抛出异常
   */
  @After("func()")
  public void doAfter(JoinPoint joinPoint) {
    LOGGER.info(">>> doAfter...");
  }

  /**
   * 只有在方法成功执行后运行
   */
  @AfterReturning(returning = "result", pointcut = "func()")
  public void doAfterReturning(Object result) {
    LOGGER.info(">>> after returning... ");
    // response info
    LOGGER.info("returning object : " + result);
  }

  /**
   * 只有在方法抛出异常后运行
   */
  @AfterThrowing("func()")
  public void doAfterThrows(JoinPoint joinPoint) {
    LOGGER.info(">>> after throws exception... ");
  }

  /**
   * 环绕通知,环绕增强，相当于MethodInterceptor
   */
  @Around("func()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    LOGGER.info(">>> doAround ... ");
    Object result = joinPoint.proceed();
    LOGGER.info("doAround，returning object : " + result);
    return result;
  }

}
