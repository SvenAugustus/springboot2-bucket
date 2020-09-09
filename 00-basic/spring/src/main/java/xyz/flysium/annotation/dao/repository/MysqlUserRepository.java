package xyz.flysium.annotation.dao.repository;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.flysium.annotation.dao.entity.User;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Repository("mysqlUserRepository")
public class MysqlUserRepository implements UserRepository {

  @Autowired
  private User user;

  @Override
  public User queryUser(String username, String password) {
    System.out.println("mysql repository...");
    // query
    if (Objects.equals(username, user.getUsername()) && Objects.equals(password, user.getPwd())) {
      return user;
    }
    return null;
  }

}
