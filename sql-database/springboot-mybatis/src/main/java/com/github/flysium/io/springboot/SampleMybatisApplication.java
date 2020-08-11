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

package com.github.flysium.io.springboot;

import com.github.flysium.io.springboot.entity.Account;
import com.github.flysium.io.springboot.entity.City;
import com.github.flysium.io.springboot.entity.Permission;
import com.github.flysium.io.springboot.entity.User;
import com.github.flysium.io.springboot.repository.AccountMapper;
import com.github.flysium.io.springboot.repository.CityMapper;
import com.github.flysium.io.springboot.repository.UserMapper;
import java.util.List;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

/**
 * Application Starter
 *
 * @author Sven Augustus
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.github.flysium.io.springboot.repository")
public class SampleMybatisApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisApplication.class, args);
  }

  private final CityMapper cityMapper;
  private final AccountMapper accountMapper;
  private final UserMapper userMapper;
  private final Environment environment;

  public SampleMybatisApplication(
      CityMapper cityMapper,
      AccountMapper accountMapper,
      UserMapper userMapper, Environment environment) {
    this.cityMapper = cityMapper;
    this.accountMapper = accountMapper;
    this.userMapper = userMapper;
    this.environment = environment;
  }

  @Bean
  CommandLineRunner sampleCommandLineRunner() {
    return args -> {
      City city = new City();
      city.setName("Guangzhou");
      city.setState("Guangdong");
      city.setCountry("CN");
      cityMapper.insert(city);
      System.out.println(this.cityMapper.findById(city.getId()));
    };
  }

  @Bean
  CommandLineRunner sampleCommandLineRunner2() {
    return args -> {
      Account account = new Account();
      account.setLoginName("SvenAugustus");
      account.setPassword("root123");
      account.setNickName("zeno");
      account.setAge(18);
      account.setLocation("Guangzhou");
      accountMapper.add(account);
      System.out.println(this.accountMapper.findAll());
    };
  }


  /**
   * <li>一对一的 关联查询，嵌套查询</li>
   * <li>一对多的 关联查询，嵌套查询</li>
   */
  @Bean
  CommandLineRunner sampleCommandLineRunner3() {
    return args -> {
      System.out.println("一对一关联查询1---->" + this.userMapper.findById11(1L));
      System.out.println("一对一关联查询2---->" + this.userMapper.findById12(1L));
      System.out.println("一对一嵌套查询3---->" + this.userMapper.findById13(1L));
      System.out.println("一对多关联查询1---->" + this.userMapper.findById21(1L));
      System.out.println("一对多嵌套查询2---->" + this.userMapper.findById22(1L));
    };
  }


  /**
   * 延迟加载, 需要修改 application.yml:
   * <pre>
   *   mybatis.configuration.lazy-loading-enabled=true
   *   mybatis.configuration.aggressive-lazy-loading=true
   * </pre>
   */
  @Bean
  CommandLineRunner sampleCommandLineRunner4() {
    return args -> {
      if (!"true".equals(environment.getProperty(
          "mybatis.configuration.lazy-loading-enabled"))) {
        System.out.println("------延迟加载关闭---->");
        return;
      }
      System.out.println("------延迟加载---->");
      User byId22 = this.userMapper.findById22(1L);
      System.out.println("------尝试get---->");
      System.out.println(byId22.getRole());

      if (!"true".equals(environment.getProperty(
          "mybatis.configuration.aggressive-lazy-loading"))) {
        System.out.println("------延迟加载集合对象关闭---->");
        return;
      }
      System.out.println("------尝试get集合对象---->");
      List<Permission> permissions = byId22.getPermissions();
      System.out.println(permissions);
      if (!CollectionUtils.isEmpty(permissions)) {
        System.out.println(permissions.get(0));
      }
    };
  }

}
