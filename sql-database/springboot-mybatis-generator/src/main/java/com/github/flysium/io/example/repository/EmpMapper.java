package com.github.flysium.io.example.repository;

import com.github.flysium.io.example.entity.Emp;
import com.github.flysium.io.example.entity.EmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;

public interface EmpMapper {

  @Delete({
      "delete from emp",
      "where EMPNO = #{empno,jdbcType=BIGINT}"
  })
  int deleteByPrimaryKey(Long empno);

  @Insert({
      "insert into emp (ENAME, JOB, ",
      "MGR, HIREDATE, SAL, ",
      "COMM, DEPTNO)",
      "values (#{ename,jdbcType=VARCHAR}, #{job,jdbcType=VARCHAR}, ",
      "#{mgr,jdbcType=INTEGER}, #{hiredate,jdbcType=DATE}, #{sal,jdbcType=DECIMAL}, ",
      "#{comm,jdbcType=DECIMAL}, #{deptno,jdbcType=INTEGER})"
  })
  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "empno", before = false, resultType = Long.class)
  int insert(Emp record);

  @InsertProvider(type = EmpSqlProvider.class, method = "insertSelective")
  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "empno", before = false, resultType = Long.class)
  int insertSelective(Emp record);

  @SelectProvider(type = EmpSqlProvider.class, method = "selectByExample")
  @Results({
      @Result(column = "EMPNO", property = "empno", jdbcType = JdbcType.BIGINT, id = true),
      @Result(column = "ENAME", property = "ename", jdbcType = JdbcType.VARCHAR),
      @Result(column = "JOB", property = "job", jdbcType = JdbcType.VARCHAR),
      @Result(column = "MGR", property = "mgr", jdbcType = JdbcType.INTEGER),
      @Result(column = "HIREDATE", property = "hiredate", jdbcType = JdbcType.DATE),
      @Result(column = "SAL", property = "sal", jdbcType = JdbcType.DECIMAL),
      @Result(column = "COMM", property = "comm", jdbcType = JdbcType.DECIMAL),
      @Result(column = "DEPTNO", property = "deptno", jdbcType = JdbcType.INTEGER)
  })
  List<Emp> selectByExampleWithRowbounds(EmpExample example, RowBounds rowBounds);

  @SelectProvider(type = EmpSqlProvider.class, method = "selectByExample")
  @Results({
      @Result(column = "EMPNO", property = "empno", jdbcType = JdbcType.BIGINT, id = true),
      @Result(column = "ENAME", property = "ename", jdbcType = JdbcType.VARCHAR),
      @Result(column = "JOB", property = "job", jdbcType = JdbcType.VARCHAR),
      @Result(column = "MGR", property = "mgr", jdbcType = JdbcType.INTEGER),
      @Result(column = "HIREDATE", property = "hiredate", jdbcType = JdbcType.DATE),
      @Result(column = "SAL", property = "sal", jdbcType = JdbcType.DECIMAL),
      @Result(column = "COMM", property = "comm", jdbcType = JdbcType.DECIMAL),
      @Result(column = "DEPTNO", property = "deptno", jdbcType = JdbcType.INTEGER)
  })
  List<Emp> selectByExample(EmpExample example);

  @Select({
      "select",
      "EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO",
      "from emp",
      "where EMPNO = #{empno,jdbcType=BIGINT}"
  })
  @Results({
      @Result(column = "EMPNO", property = "empno", jdbcType = JdbcType.BIGINT, id = true),
      @Result(column = "ENAME", property = "ename", jdbcType = JdbcType.VARCHAR),
      @Result(column = "JOB", property = "job", jdbcType = JdbcType.VARCHAR),
      @Result(column = "MGR", property = "mgr", jdbcType = JdbcType.INTEGER),
      @Result(column = "HIREDATE", property = "hiredate", jdbcType = JdbcType.DATE),
      @Result(column = "SAL", property = "sal", jdbcType = JdbcType.DECIMAL),
      @Result(column = "COMM", property = "comm", jdbcType = JdbcType.DECIMAL),
      @Result(column = "DEPTNO", property = "deptno", jdbcType = JdbcType.INTEGER)
  })
  Emp selectByPrimaryKey(Long empno);

  @UpdateProvider(type = EmpSqlProvider.class, method = "updateByPrimaryKeySelective")
  int updateByPrimaryKeySelective(Emp record);

  @Update({
      "update emp",
      "set ENAME = #{ename,jdbcType=VARCHAR},",
      "JOB = #{job,jdbcType=VARCHAR},",
      "MGR = #{mgr,jdbcType=INTEGER},",
      "HIREDATE = #{hiredate,jdbcType=DATE},",
      "SAL = #{sal,jdbcType=DECIMAL},",
      "COMM = #{comm,jdbcType=DECIMAL},",
      "DEPTNO = #{deptno,jdbcType=INTEGER}",
      "where EMPNO = #{empno,jdbcType=BIGINT}"
  })
  int updateByPrimaryKey(Emp record);
}