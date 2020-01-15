/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.bucket.springboot.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Message Model: User.
 *
 * @author Sven Augustus
 */
@Entity
@Table(name = "USER")
public class UserInfo implements java.io.Serializable {

  private static final long serialVersionUID = 6294613066169637324L;

  @Id
  // FIXME 这里为了方便H2 数据库模拟，使用 long ，实际生产环境可能采用 更长的整数存储 ？
  private Long id;
  @Column
  private String name;
  @Column
  private String sex = "M";
  @Column
  private Integer age;
  @Column
  private String idNo;
  @Column
  private String phoneNum;
  @Column
  private String email;
  @Column
  private Date createTime;
  @Column
  private Date modifyTime;
  @Column
  private String state;

  public UserInfo() {
  }

  public UserInfo(Long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getPhoneNum() {
    return phoneNum;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", sex='" + sex + '\'' +
        ", age=" + age +
        ", idNo='" + idNo + '\'' +
        ", phoneNum='" + phoneNum + '\'' +
        ", email='" + email + '\'' +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", state='" + state + '\'' +
        '}';
  }
}
