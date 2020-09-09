/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.hello;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.flysium.api.HelloService;

/**
 * Hello Service
 *
 * @author Sven Augustus
 */
@Service
public class HelloServiceImpl implements HelloService {

  private final JdbcTemplate jdbcTemplate;

  public HelloServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public String sayHello(String name) {
    List<Map<String, Object>> userInfoList = jdbcTemplate
        .queryForList("select * from USER where USER_NAME = ? ", name);
    if (CollectionUtils.isEmpty(userInfoList)) {
      return "You are not in the database!!!";
    }
    return "Hello " + name + ", userInfo = " + userInfoList.get(0);
  }

}
