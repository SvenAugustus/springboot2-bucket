package xyz.flysium.annotation.dao.repository;

import org.springframework.stereotype.Repository;
import xyz.flysium.annotation.dao.entity.User;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Repository("oracleUserRepository")
public class OracleUserRepository implements UserRepository {

  @Override
  public User queryUser(String username, String password) {
    System.out.println("oracle repository...");
    return null;
  }

}
