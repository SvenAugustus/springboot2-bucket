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

package com.github.flysium.io.bucket.springboot.jpa.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: User Information
 *
 * @author Sven Augustus
 */
@Entity
@Table(name = "USER")
public class UserInfo {

  @Id
  private Long userId;

  @Column(length = 100)
  private String userName;

  @Column(length = 1)
  private String userSex;

  @Column(length = 3)
  private Integer userAge;

  @Column(length = 18)
  private String userIdNo;

  @Column(length = 11)
  private String userPhoneNum;

  @Column(length = 100)
  private String userEmail;

  @Column
  private Timestamp createTime;

  @Column
  private Timestamp modifyTime;

  @Column(length = 1)
  private String userState;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  public Integer getUserAge() {
    return userAge;
  }

  public void setUserAge(Integer userAge) {
    this.userAge = userAge;
  }

  public String getUserIdNo() {
    return userIdNo;
  }

  public void setUserIdNo(String userIdNo) {
    this.userIdNo = userIdNo;
  }

  public String getUserPhoneNum() {
    return userPhoneNum;
  }

  public void setUserPhoneNum(String userPhoneNum) {
    this.userPhoneNum = userPhoneNum;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  public String getUserState() {
    return userState;
  }

  public void setUserState(String userState) {
    this.userState = userState;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
        "userId=" + userId +
        ", userName='" + userName + '\'' +
        ", userSex='" + userSex + '\'' +
        ", userAge=" + userAge +
        ", userIdNo='" + userIdNo + '\'' +
        ", userPhoneNum='" + userPhoneNum + '\'' +
        ", userEmail='" + userEmail + '\'' +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", userState='" + userState + '\'' +
        '}';
  }
}
