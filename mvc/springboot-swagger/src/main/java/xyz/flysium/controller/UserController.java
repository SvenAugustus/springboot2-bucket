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

package xyz.flysium.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.flysium.dao.entity.User;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/users")     // 通过这里配置使下面的映射都在/users下，可去除
public class UserController {

  static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

  @ApiOperation(value = "获取用户列表", notes = "")
  @RequestMapping(value = {""}, method = RequestMethod.GET)
  public List<User> getUserList() {
    List<User> r = new ArrayList<User>(users.values());
    return r;
  }

  @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
  @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
  @RequestMapping(value = "", method = RequestMethod.POST)
  public String postUser(@RequestBody User user) {
    users.put(user.getId(), user);
    return "success";
  }

  @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
  @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public User getUser(@PathVariable Long id) {
    return users.get(id);
  }

  @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
      @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
  })
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public String putUser(@PathVariable Long id, @RequestBody User user) {
    User u = users.get(id);
    u.setName(user.getName());
    u.setAge(user.getAge());
    users.put(id, u);
    return "success";
  }

  @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
  @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public String deleteUser(@PathVariable Long id) {
    users.remove(id);
    return "success";
  }
}