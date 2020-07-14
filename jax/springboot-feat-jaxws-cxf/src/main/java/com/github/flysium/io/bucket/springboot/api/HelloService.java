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

package com.github.flysium.io.bucket.springboot.api;

import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Hello Service
 *
 * @author Sven Augustus
 */
@WebService(name = "HelloService", // 暴露服务名称
    targetNamespace = "http://model.springboot.bucket.io.flysium.github.com/" // Model的命名空间,一般是接口的包名倒排
)
public interface HelloService {

  /**
   * say hello.
   *
   * @param name user name
   * @return echo message.
   */
  @WebMethod
//    @WebResult(name = "String", targetNamespace = "")
  public String sayHello(@WebParam(name = "userName") String name);

  /**
   * Get User Information.
   *
   * @param name user name
   * @return User Information.
   */
  @WebMethod
//    @WebResult(name = "String", targetNamespace = "")
  public UserInfo getUser(@WebParam(name = "userName") String name);

}