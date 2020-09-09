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

package xyz.flysium.hello;

import javax.ws.rs.Path;
import org.springframework.stereotype.Component;
import xyz.flysium.api.HelloService;

/**
 * Hello Service
 *
 * @author Sven Augustus
 */
@Path("/sayHello2")
@Component
public class HelloServiceImpl2 implements HelloService {

  @Override
  public String sayHello(String a) {
    return "Hello2 " + a + ", Welcome to Jersey RS Spring Boot World!!!";
  }

}
