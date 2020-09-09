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

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import xyz.flysium.dao.entity.Dept;

@Mapper
@Component
public interface DeptMapper {

  @Delete({ "delete from dept", "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  int deleteByPrimaryKey(Integer deptno);

  @Insert({ "insert into dept (DEPTNO, DNAME, ", "LOC)",
      "values (#{deptno,jdbcType=INTEGER}, #{dname,jdbcType=VARCHAR}, ", "#{loc,jdbcType=VARCHAR})"
  })
  int insert(Dept record);

  @InsertProvider(type = DeptSqlProvider.class, method = "insertSelective")
  int insertSelective(Dept record);

  @Select({ "select", "DEPTNO, DNAME, LOC", "from dept", "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  @Results({ @Result(column = "DEPTNO", property = "deptno", jdbcType = JdbcType.INTEGER, id = true), @Result(column = "DNAME", property = "dname", jdbcType = JdbcType.VARCHAR),
      @Result(column = "LOC", property = "loc", jdbcType = JdbcType.VARCHAR)
  })
  Dept selectByPrimaryKey(Integer deptno);

  @UpdateProvider(type = DeptSqlProvider.class, method = "updateByPrimaryKeySelective")
  int updateByPrimaryKeySelective(Dept record);

  @Update({ "update dept", "set DNAME = #{dname,jdbcType=VARCHAR},", "LOC = #{loc,jdbcType=VARCHAR}",
      "where DEPTNO = #{deptno,jdbcType=INTEGER}"
  })
  int updateByPrimaryKey(Dept record);
}