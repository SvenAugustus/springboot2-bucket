package com.github.flysium.io.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Emp implements Serializable {

  private Long empno;

  private String ename;

  private String job;

  private Integer mgr;

  private Date hiredate;

  private BigDecimal sal;

  private BigDecimal comm;

  private Integer deptno;

  private static final long serialVersionUID = 1L;

  public Long getEmpno() {
    return empno;
  }

  public void setEmpno(Long empno) {
    this.empno = empno;
  }

  public String getEname() {
    return ename;
  }

  public void setEname(String ename) {
    this.ename = ename;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public Integer getMgr() {
    return mgr;
  }

  public void setMgr(Integer mgr) {
    this.mgr = mgr;
  }

  public Date getHiredate() {
    return hiredate;
  }

  public void setHiredate(Date hiredate) {
    this.hiredate = hiredate;
  }

  public BigDecimal getSal() {
    return sal;
  }

  public void setSal(BigDecimal sal) {
    this.sal = sal;
  }

  public BigDecimal getComm() {
    return comm;
  }

  public void setComm(BigDecimal comm) {
    this.comm = comm;
  }

  public Integer getDeptno() {
    return deptno;
  }

  public void setDeptno(Integer deptno) {
    this.deptno = deptno;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName());
    sb.append(" [");
    sb.append("Hash = ").append(hashCode());
    sb.append(", empno=").append(empno);
    sb.append(", ename=").append(ename);
    sb.append(", job=").append(job);
    sb.append(", mgr=").append(mgr);
    sb.append(", hiredate=").append(hiredate);
    sb.append(", sal=").append(sal);
    sb.append(", comm=").append(comm);
    sb.append(", deptno=").append(deptno);
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
    Emp other = (Emp) that;
    return (this.getEmpno() == null ? other.getEmpno() == null
        : this.getEmpno().equals(other.getEmpno()))
        && (this.getEname() == null ? other.getEname() == null
        : this.getEname().equals(other.getEname()))
        && (this.getJob() == null ? other.getJob() == null
        : this.getJob().equals(other.getJob()))
        && (this.getMgr() == null ? other.getMgr() == null
        : this.getMgr().equals(other.getMgr()))
        && (this.getHiredate() == null ? other.getHiredate() == null
        : this.getHiredate().equals(other.getHiredate()))
        && (this.getSal() == null ? other.getSal() == null
        : this.getSal().equals(other.getSal()))
        && (this.getComm() == null ? other.getComm() == null
        : this.getComm().equals(other.getComm()))
        && (this.getDeptno() == null ? other.getDeptno() == null
        : this.getDeptno().equals(other.getDeptno()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getEmpno() == null) ? 0 : getEmpno().hashCode());
    result = prime * result + ((getEname() == null) ? 0 : getEname().hashCode());
    result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
    result = prime * result + ((getMgr() == null) ? 0 : getMgr().hashCode());
    result = prime * result + ((getHiredate() == null) ? 0 : getHiredate().hashCode());
    result = prime * result + ((getSal() == null) ? 0 : getSal().hashCode());
    result = prime * result + ((getComm() == null) ? 0 : getComm().hashCode());
    result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
    return result;
  }
}