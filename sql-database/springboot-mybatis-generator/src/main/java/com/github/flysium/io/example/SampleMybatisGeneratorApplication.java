package com.github.flysium.io.example;

import com.github.flysium.io.example.service.EmpService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Application Starter
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.github.flysium.io.example.mapper")
public class SampleMybatisGeneratorApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisGeneratorApplication.class, args);
  }

  private final EmpService empService;

  public SampleMybatisGeneratorApplication(EmpService empService) {
    this.empService = empService;
  }

  @Bean
  CommandLineRunner sampleCommandLineRunner() {
    return args -> {
      System.out.println("result--->" + this.empService.query());

      System.out.println("page--->" + this.empService.findByPage(1, 2));
      System.out.println("page--->" + this.empService.findByPage(2, 2));

      System.out.println("page2--->" + this.empService.findByPage0(1, 2));
      System.out.println("page2--->" + this.empService.findByPage0(2, 2));
    };
  }

}
