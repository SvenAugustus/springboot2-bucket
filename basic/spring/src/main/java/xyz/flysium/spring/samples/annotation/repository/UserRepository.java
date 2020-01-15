package xyz.flysium.spring.samples.annotation.repository;

import xyz.flysium.spring.samples.annotation.entity.User;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public interface UserRepository {

  /**
   * Query User by username, password
   *
   * @param username Username
   * @param password Password
   * @return return user if login success, otherwise return null
   */
  User queryUser(String username, String password);

}
