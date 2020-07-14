package com.github.flysium.io.springboot.repository;

import com.github.flysium.io.springboot.entity.Account;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author SvenAugustus
 */
@Mapper
public interface AccountMapper {

  @Select("SELECT * FROM account")
  List<Account> findAll();

  void add(Account account);
}
