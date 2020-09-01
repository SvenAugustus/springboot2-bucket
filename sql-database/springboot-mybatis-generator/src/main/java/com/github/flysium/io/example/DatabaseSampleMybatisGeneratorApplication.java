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
@MapperScan("com.github.flysium.io.example.repository")
public class DatabaseSampleMybatisGeneratorApplication {

  public static void main(String[] args) {
    SpringApplication.run(DatabaseSampleMybatisGeneratorApplication.class, args);
  }

  private final EmpService empService;

  public DatabaseSampleMybatisGeneratorApplication(EmpService empService) {
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
