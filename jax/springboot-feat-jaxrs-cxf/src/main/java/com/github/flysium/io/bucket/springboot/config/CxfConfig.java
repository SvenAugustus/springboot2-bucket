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

package com.github.flysium.io.bucket.springboot.config;

import com.github.flysium.io.bucket.springboot.hello.HelloServiceImpl1;
import com.github.flysium.io.bucket.springboot.hello.HelloServiceImpl2;
import java.util.Arrays;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CXF 配置
 *
 * @author Sven Augustus
 */
@Configuration
public class CxfConfig {

  private final Bus bus;

  public CxfConfig(Bus bus) {
    this.bus = bus;
  }

  @Bean
  public Server rsServer() {
    JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
    endpoint.setBus(bus);
    endpoint
        .setServiceBeans(Arrays.<Object>asList(new HelloServiceImpl1(), new HelloServiceImpl2()));
    endpoint.setAddress("/helloservice");
    endpoint.setFeatures(Arrays.asList(new LoggingFeature()));
    return endpoint.create();
  }

}
