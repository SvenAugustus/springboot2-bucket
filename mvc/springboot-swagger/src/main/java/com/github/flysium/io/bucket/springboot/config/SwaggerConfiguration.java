package com.github.flysium.io.bucket.springboot.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 文档配置
 *
 * @author Sven Augustus
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
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
            .basePackage("com.github.flysium.io.bucket.springboot.controller"))
//        .paths(PathSelectors.ant("/app/*/**"))
        .paths(PathSelectors.any())
        .build();
    // @formatter:on
  }

}
