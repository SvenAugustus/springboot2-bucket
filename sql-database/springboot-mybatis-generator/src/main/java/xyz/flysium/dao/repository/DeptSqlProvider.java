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

package xyz.flysium.dao.repository;

import org.apache.ibatis.jdbc.SQL;
import xyz.flysium.dao.entity.Dept;

public class DeptSqlProvider {

    public String insertSelective(Dept record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("dept");

    if (record.getDeptno() != null) {
      sql.VALUES("DEPTNO", "#{deptno,jdbcType=INTEGER}");
    }

    if (record.getDname() != null) {
      sql.VALUES("DNAME", "#{dname,jdbcType=VARCHAR}");
    }

    if (record.getLoc() != null) {
      sql.VALUES("LOC", "#{loc,jdbcType=VARCHAR}");
    }

    return sql.toString();
  }

  public String updateByPrimaryKeySelective(Dept record) {
    SQL sql = new SQL();
    sql.UPDATE("dept");

    if (record.getDname() != null) {
      sql.SET("DNAME = #{dname,jdbcType=VARCHAR}");
    }

    if (record.getLoc() != null) {
      sql.SET("LOC = #{loc,jdbcType=VARCHAR}");
    }

    sql.WHERE("DEPTNO = #{deptno,jdbcType=INTEGER}");

    return sql.toString();
  }
}