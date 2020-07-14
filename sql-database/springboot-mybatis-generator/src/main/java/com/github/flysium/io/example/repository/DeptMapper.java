package com.github.flysium.io.example.repository;

import com.github.flysium.io.example.entity.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DeptMapper {

  @Delete({
      "delete from dept",
      "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  int deleteByPrimaryKey(Integer deptno);

  @Insert({
      "insert into dept (DEPTNO, DNAME, ",
      "LOC)",
      "values (#{deptno,jdbcType=INTEGER}, #{dname,jdbcType=VARCHAR}, ",
      "#{loc,jdbcType=VARCHAR})"
  })
  int insert(Dept record);

  @InsertProvider(type = DeptSqlProvider.class, method = "insertSelective")
  int insertSelective(Dept record);

  @Select({
      "select",
      "DEPTNO, DNAME, LOC",
      "from dept",
      "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  @Results({
      @Result(column = "DEPTNO", property = "deptno", jdbcType = JdbcType.INTEGER, id = true),
      @Result(column = "DNAME", property = "dname", jdbcType = JdbcType.VARCHAR),
      @Result(column = "LOC", property = "loc", jdbcType = JdbcType.VARCHAR)
  })
  Dept selectByPrimaryKey(Integer deptno);

  @UpdateProvider(type = DeptSqlProvider.class, method = "updateByPrimaryKeySelective")
  int updateByPrimaryKeySelective(Dept record);

  @Update({
      "update dept",
      "set DNAME = #{dname,jdbcType=VARCHAR},",
      "LOC = #{loc,jdbcType=VARCHAR}",
      "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  int updateByPrimaryKey(Dept record);
}