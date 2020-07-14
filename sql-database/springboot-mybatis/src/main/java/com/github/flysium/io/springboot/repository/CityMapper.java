package com.github.flysium.io.springboot.repository;

import com.github.flysium.io.springboot.entity.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * City Mapper
 *
 * @author Sven Augustus
 * @version 1.0
 */
@Mapper
public interface CityMapper {

  @Insert("INSERT INTO city (name, state, country) VALUES(#{name}, #{state}, #{country})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(City city);

  @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
  City findById(long id);

}
