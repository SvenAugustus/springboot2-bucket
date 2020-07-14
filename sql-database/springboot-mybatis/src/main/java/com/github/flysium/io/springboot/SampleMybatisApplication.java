package com.github.flysium.io.springboot;

import com.github.flysium.io.springboot.entity.Account;
import com.github.flysium.io.springboot.entity.City;
import com.github.flysium.io.springboot.repository.AccountMapper;
import com.github.flysium.io.springboot.repository.CityMapper;
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
@MapperScan("com.github.flysium.io.springboot.repository")
public class SampleMybatisApplication {

  public static void main(String[] args) {
    SpringApplication.run(SampleMybatisApplication.class, args);
  }

  private final CityMapper cityMapper;
  private final AccountMapper accountMapper;

  public SampleMybatisApplication(CityMapper cityMapper,
      AccountMapper accountMapper) {
    this.cityMapper = cityMapper;
    this.accountMapper = accountMapper;
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
      accountMapper.add(account);
      System.out.println(this.accountMapper.findAll());
    };
  }

}
