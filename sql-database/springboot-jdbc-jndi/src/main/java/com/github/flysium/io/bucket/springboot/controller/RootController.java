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

package com.github.flysium.io.bucket.springboot.controller;

import com.github.flysium.io.bucket.springboot.api.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Root
 *
 * @author Sven Augustus
 */
@RequestMapping("/")
@RestController
public class RootController {

  private final HelloService helloService;

  public RootController(HelloService helloService) {
    this.helloService = helloService;
  }

  @GetMapping("/")
  public String home() {
    return "Hello, Welcome to Spring MVC.";
  }

  @GetMapping("/hello/{name}")
  public String sayHello(@PathVariable String name) {
    return helloService.sayHello(name);
  }

}
