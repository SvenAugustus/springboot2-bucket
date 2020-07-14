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

package com.github.flysium.io.bucket.springboot.repository;

import com.github.flysium.io.bucket.springboot.entity.UserInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User Repository.
 *
 * @author Sven Augustus
 */
public interface UserRepository extends MongoRepository<UserInfo, String> {

  /**
   * find by name.
   *
   * @param name name
   * @return user
   */
  Optional<UserInfo> findByName(String name);

  /**
   * find by sex.
   *
   * @param sex sex
   * @return user
   */
  List<UserInfo> findBySex(String sex);

}
