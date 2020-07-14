package com.github.flysium.io.example.service;

import com.github.flysium.io.example.entity.Emp;
import com.github.flysium.io.example.entity.EmpExample;
import com.github.flysium.io.example.repository.EmpMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.function.Supplier;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Service
public class EmpService {

  private final EmpMapper empMapper;

  public EmpService(EmpMapper empMapper) {
    this.empMapper = empMapper;
  }

  public List<Emp> query() {
    EmpExample example = new EmpExample();
//      example.createCriteria().andDeptnoEqualTo(10).andEnameEqualTo("CLARK");
    example.or().andDeptnoEqualTo(10).andEnameIsNotNull();
    example.or().andDeptnoEqualTo(20);

    return this.empMapper.selectByExample(example);
  }

  public List<Emp> findByPage0(Integer pageNum, Integer pageSize) {
    RowBounds rowBounds = ofPage(pageNum, pageSize);

    EmpExample example = new EmpExample();
    return empMapper.selectByExampleWithRowbounds(example, rowBounds);
  }

  public PageInfo<Emp> findByPage(Integer pageNum, Integer pageSize) {
//    Page<Emp> page = PageHelper.startPage(pageNum, pageSize);
//
//    // AOP
//    EmpExample example = new EmpExample();
//    empMapper.selectByExample(example);
//
//    return page.toPageInfo();
    return findByPage(pageNum, pageSize, () -> {
      EmpExample example = new EmpExample();
      return empMapper.selectByExample(example);
    });
  }

  // FIXME 可以考虑封装为工具类
  public static <T> PageInfo<T> findByPage(Integer pageNum, Integer pageSize,
      Supplier<List<T>> supplier) {
    Page<T> page = PageHelper.startPage(pageNum, pageSize);
    supplier.get();
    return page.toPageInfo();
  }

  // FIXME 可以考虑封装为工具类
  public static RowBounds ofPage(Integer pageNum, Integer pageSize) {
    int num = pageNum == null ? 1 : pageNum;
    int size = pageSize == null ? 20 : pageSize;
    return new RowBounds((num - 1) * size, size);
  }

}
