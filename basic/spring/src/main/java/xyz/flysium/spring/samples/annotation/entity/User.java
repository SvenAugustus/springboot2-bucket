package xyz.flysium.spring.samples.annotation.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Component
@Scope("prototype")
public class User {

  @Value("zhangsan")
  private String username;

  @Value("123")
  private String pwd;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

}
