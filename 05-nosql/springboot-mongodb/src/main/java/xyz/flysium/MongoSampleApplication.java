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

package xyz.flysium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import xyz.flysium.dao.entity.UserInfo;
import xyz.flysium.dao.repository.UserRepository;

/**
 * Application Starter.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootApplication
public class MongoSampleApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(MongoSampleApplication.class).run(args);
  }

  @Autowired
  private UserRepository repository;
  @Autowired
  private MongoTemplate mongoTemplate;

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      this.repository.deleteAll();

      // save a couple of UserInfos
      this.repository.save(new UserInfo("Jack", 28, "M"));
      this.repository.save(new UserInfo("Alice", 18, "W"));

      // fetch all UserInfos
      System.out.println("UserInfos found with findAll():");
      System.out.println("-------------------------------");
      for (UserInfo userInfo : this.repository.findAll()) {
        System.out.println(userInfo);
      }
      System.out.println();

      // fetch UserInfos using mongoTemplate
      System.out.println("UserInfos found with mongoTemplate find():");
      for (UserInfo userInfo : mongoTemplate
          .find(Query.query(Criteria.where("name").is("Jack")), UserInfo.class)) {
        System.out.println(userInfo);
      }
      System.out.println();

      // fetch an individual UserInfo
      System.out.println("UserInfo found with findByName('Alice'):");
      System.out.println("--------------------------------");
      System.out.println(this.repository.findByName("Alice"));

      System.out.println("UserInfo found with findBySex('M'):");
      System.out.println("--------------------------------");
      for (UserInfo userInfo : this.repository.findBySex("M")) {
        System.out.println(userInfo);
      }
    };
  }
}
