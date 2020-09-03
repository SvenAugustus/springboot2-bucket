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

import java.io.Serializable;

public class Dept implements Serializable {

    private Integer deptno;

    private String dname;

    private String loc;

  private static final long serialVersionUID = 1L;

  public Integer getDeptno() {
    return deptno;
  }

  public void setDeptno(Integer deptno) {
    this.deptno = deptno;
  }

  public String getDname() {
    return dname;
  }

  public void setDname(String dname) {
    this.dname = dname;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", deptno=").append(deptno);
    sb.append(", dname=").append(dname);
    sb.append(", loc=").append(loc);
    sb.append(", serialVersionUID=").append(serialVersionUID);
    sb.append("]");
    return sb.toString();
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    Dept other = (Dept) that;
    return (this.getDeptno() == null ? other.getDeptno() == null
        : this.getDeptno().equals(other.getDeptno()))
        && (this.getDname() == null ? other.getDname() == null
        : this.getDname().equals(other.getDname()))
        && (this.getLoc() == null ? other.getLoc() == null : this.getLoc().equals(other.getLoc()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
    result = prime * result + ((getDname() == null) ? 0 : getDname().hashCode());
    result = prime * result + ((getLoc() == null) ? 0 : getLoc().hashCode());
    return result;
  }
}