package xyz.flysium.annotation.dao.repository;

import xyz.flysium.annotation.dao.entity.User;

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
