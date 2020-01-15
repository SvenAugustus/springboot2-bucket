package xyz.flysium.spring.samples.annotation.repository;

import org.springframework.stereotype.Repository;
import xyz.flysium.spring.samples.annotation.entity.User;

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
