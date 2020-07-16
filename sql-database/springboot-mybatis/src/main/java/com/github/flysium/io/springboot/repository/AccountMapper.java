package com.github.flysium.io.springboot.repository;

import com.github.flysium.io.springboot.entity.Account;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author SvenAugustus
 */
@Mapper
//@CacheNamespace(flushInterval = 0) // 启用二级缓存, org.apache.ibatis.mapping.MappedStatement#
public interface AccountMapper {

  @Select("SELECT * FROM account")
//  @Flush // 禁用一级缓存
//  @Options(useCache = true)
  List<Account> findAll();

  void add(Account account);
}
