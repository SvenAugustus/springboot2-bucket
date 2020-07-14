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

package com.github.flysium.io.bucket.springboot.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order of User.
 *
 * @author Sven Augustus
 */
@Entity
@Table(name = "U_ORDER")
public class OrderInfo implements java.io.Serializable {

  private static final long serialVersionUID = 4611610861953677600L;

  @Id
  // FIXME 这里为了方便H2 数据库模拟，使用 long ，实际生产环境可能采用 更长的整数存储 ？
  private Long id;
  @Column
  private Long userId;
  @Column
  private Date createTime;
  @Column
  private Date modifyTime;
  @Column
  private String state;

  public OrderInfo() {
  }

  public OrderInfo(long id, long userId, String state) {
    this.id = id;
    this.userId = userId;
    this.state = state;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
    return "OrderInfo{" +
        "id=" + id +
        ", userId=" + userId +
        ", createTime=" + createTime +
        ", modifyTime=" + modifyTime +
        ", state='" + state + '\'' +
        '}';
  }
}
