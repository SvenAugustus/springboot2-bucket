package com.github.flysium.io.springboot.web.controller;

import com.github.flysium.io.springboot.repository.AccountMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@RestController
@RequestMapping("account")
public class AccountController {

  private final AccountMapper accountMapper;

  public AccountController(AccountMapper accountMapper) {
    this.accountMapper = accountMapper;
  }

  @GetMapping("query")
  @Transactional
  public Object testSqlSessionLocalCacheTrue() {
    System.out.println(accountMapper.findAll());
    System.out.println(accountMapper.findAll());
    return null;
  }

}
