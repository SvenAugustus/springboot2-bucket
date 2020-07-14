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

package com.github.flysium.io.bucket.springboot;

import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import com.github.flysium.io.bucket.springboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Application Starter.
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootApplication
public class SampleMongoApplication implements CommandLineRunner {

  private final UserRepository repository;
  private final MongoTemplate mongoTemplate;

  public SampleMongoApplication(UserRepository repository, MongoTemplate mongoTemplate) {
    this.repository = repository;
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
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
    for (UserInfo userInfo : mongoTemplate.find(Query.query(Criteria.where("name").is("Jack")),
        UserInfo.class)) {
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
  }
}
