package xyz.flysium.spring.samples.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.flysium.spring.samples.annotation.entity.User;
import xyz.flysium.spring.samples.annotation.repository.UserRepository;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Service("mainService")
public class MainService {

  @Autowired
  @Qualifier("mysqlUserRepository")
  private UserRepository userRepository;

  public User login(String username, String pwd) {
    System.out.println("service...");
    return userRepository.queryUser(username, pwd);
  }

}
