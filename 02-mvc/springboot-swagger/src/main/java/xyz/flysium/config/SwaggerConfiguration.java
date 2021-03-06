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

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger 文档配置
 *
 * @author Sven Augustus
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerConfiguration {

  @Bean
  public Docket docket() throws IOException {
    ApiInfo apiInfo = new ApiInfoBuilder().title("Springboot Swagger Demo").version("1.0.0")
        .build();
    // @formatter:off
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo)
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("xyz.flysium.controller"))
//        .paths(PathSelectors.ant("/app/*/**"))
        .paths(PathSelectors.any())
        .build();
    // @formatter:on
  }

}
