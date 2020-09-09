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

import java.util.List;
import org.apache.ibatis.jdbc.SQL;
import xyz.flysium.dao.entity.Emp;
import xyz.flysium.dao.entity.EmpExample;
import xyz.flysium.dao.entity.EmpExample.Criteria;
import xyz.flysium.dao.entity.EmpExample.Criterion;

public class EmpSqlProvider {

  public String insertSelective(Emp record) {
    SQL sql = new SQL();
    sql.INSERT_INTO("emp");

    if (record.getEname() != null) {
      sql.VALUES("ENAME", "#{ename,jdbcType=VARCHAR}");
    }

    if (record.getJob() != null) {
      sql.VALUES("JOB", "#{job,jdbcType=VARCHAR}");
    }

    if (record.getMgr() != null) {
      sql.VALUES("MGR", "#{mgr,jdbcType=INTEGER}");
    }

    if (record.getHiredate() != null) {
      sql.VALUES("HIREDATE", "#{hiredate,jdbcType=DATE}");
    }

    if (record.getSal() != null) {
      sql.VALUES("SAL", "#{sal,jdbcType=DECIMAL}");
    }

    if (record.getComm() != null) {
      sql.VALUES("COMM", "#{comm,jdbcType=DECIMAL}");
    }

    if (record.getDeptno() != null) {
      sql.VALUES("DEPTNO", "#{deptno,jdbcType=INTEGER}");
    }

    return sql.toString();
  }

  public String selectByExample(EmpExample example) {
    SQL sql = new SQL();
    if (example != null && example.isDistinct()) {
      sql.SELECT_DISTINCT("EMPNO");
    } else {
      sql.SELECT("EMPNO");
    }
    sql.SELECT("ENAME");
    sql.SELECT("JOB");
    sql.SELECT("MGR");
    sql.SELECT("HIREDATE");
    sql.SELECT("SAL");
    sql.SELECT("COMM");
    sql.SELECT("DEPTNO");
    sql.FROM("emp");
    applyWhere(sql, example, false);

    if (example != null && example.getOrderByClause() != null) {
      sql.ORDER_BY(example.getOrderByClause());
    }

    return sql.toString();
  }

  public String updateByPrimaryKeySelective(Emp record) {
    SQL sql = new SQL();
    sql.UPDATE("emp");

    if (record.getEname() != null) {
      sql.SET("ENAME = #{ename,jdbcType=VARCHAR}");
    }

    if (record.getJob() != null) {
      sql.SET("JOB = #{job,jdbcType=VARCHAR}");
    }

    if (record.getMgr() != null) {
      sql.SET("MGR = #{mgr,jdbcType=INTEGER}");
    }

    if (record.getHiredate() != null) {
      sql.SET("HIREDATE = #{hiredate,jdbcType=DATE}");
    }

    if (record.getSal() != null) {
      sql.SET("SAL = #{sal,jdbcType=DECIMAL}");
    }

    if (record.getComm() != null) {
      sql.SET("COMM = #{comm,jdbcType=DECIMAL}");
    }

    if (record.getDeptno() != null) {
      sql.SET("DEPTNO = #{deptno,jdbcType=INTEGER}");
    }

    sql.WHERE("EMPNO = #{empno,jdbcType=BIGINT}");

    return sql.toString();
  }

  protected void applyWhere(SQL sql, EmpExample example, boolean includeExamplePhrase) {
    if (example == null) {
      return;
    }

    String parmPhrase1;
    String parmPhrase1_th;
    String parmPhrase2;
    String parmPhrase2_th;
    String parmPhrase3;
    String parmPhrase3_th;
    if (includeExamplePhrase) {
      parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
      parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
      parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
      parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
      parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
      parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
    } else {
      parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
      parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
      parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
      parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
      parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
      parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
    }

    StringBuilder sb = new StringBuilder();
    List<Criteria> oredCriteria = example.getOredCriteria();
    boolean firstCriteria = true;
    for (int i = 0; i < oredCriteria.size(); i++) {
      Criteria criteria = oredCriteria.get(i);
      if (criteria.isValid()) {
        if (firstCriteria) {
          firstCriteria = false;
        } else {
          sb.append(" or ");
        }

        sb.append('(');
        List<Criterion> criterions = criteria.getAllCriteria();
        boolean firstCriterion = true;
        for (int j = 0; j < criterions.size(); j++) {
          Criterion criterion = criterions.get(j);
          if (firstCriterion) {
            firstCriterion = false;
          } else {
            sb.append(" and ");
          }

          if (criterion.isNoValue()) {
            sb.append(criterion.getCondition());
          } else if (criterion.isSingleValue()) {
            if (criterion.getTypeHandler() == null) {
              sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
            } else {
              sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,
                  criterion.getTypeHandler()));
            }
          } else if (criterion.isBetweenValue()) {
            if (criterion.getTypeHandler() == null) {
              sb.append(
                  String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
            } else {
              sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j,
                  criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
            }
          } else if (criterion.isListValue()) {
            sb.append(criterion.getCondition());
            sb.append(" (");
            List<?> listItems = (List<?>) criterion.getValue();
            boolean comma = false;
            for (int k = 0; k < listItems.size(); k++) {
              if (comma) {
                sb.append(", ");
              } else {
                comma = true;
              }
              if (criterion.getTypeHandler() == null) {
                sb.append(String.format(parmPhrase3, i, j, k));
              } else {
                sb.append(String
                    .format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
              }
            }
            sb.append(')');
          }
        }
        sb.append(')');
      }
    }

    if (sb.length() > 0) {
      sql.WHERE(sb.toString());
    }
  }
}