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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.flysium.annotation.UserAccess;

/**
 * User Access Aspect.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@Aspect
@Component
@Order(2)
public class UserAccessAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserAccessAspect.class);

  @Pointcut(value = "@annotation(xyz.flysium.annotation.UserAccess)")
  public void access() {
  }

  @Before("access()")
  public void deBefore(JoinPoint joinPoint) throws Throwable {
    LOGGER.info(">>> access doBefore...");
  }

  @Around("@annotation(userAccess)")
  public Object around(ProceedingJoinPoint joinPoint, UserAccess userAccess) throws Throwable {
    LOGGER.info(">>> access doAround ... ");
    LOGGER.info("doAround，desc: " + userAccess.desc());
    return joinPoint.proceed();
  }

}
