package com.github.flysium.io.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Starter
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.github.flysium.io.springboot.repository")
public class SampleMybatisPlusApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisPlusApplication.class, args);
  }

}
