package com.github.flysium.io.example.mapper;

import com.github.flysium.io.example.model.Dept;
import org.apache.ibatis.jdbc.SQL;

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