/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.dao.entity;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Account implements java.io.Serializable {

  private static final long serialVersionUID = -8524181344925220573L;

  private int id;

  private String loginName;

  private String password;

  private String nickName;

  private int age;

  private String location;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Account{");
    sb.append("id=").append(id);
    sb.append(", loginName='").append(loginName).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", nickName='").append(nickName).append('\'');
    sb.append(", age=").append(age);
    sb.append(", location='").append(location).append('\'');
    sb.append('}');
    return sb.toString();
  }

}
