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

package xyz.flysium;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.flysium.dao.entity.User;
import xyz.flysium.dao.repository.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectList() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        System.out.println(("----- selectPage method test ------"));
        Page<User> page = new Page<>(1, 10);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //        String d = simpleDateFormat.format(date);
        //        wrapper.eq("".equals(d), "created_at", d).orderByAsc("id");
        wrapper.like("email", "test").orderByAsc("id");

        IPage<User> userList = userMapper.selectPage(page, wrapper);
        Assert.assertEquals(1, userList.getCurrent());//当前页数
        Assert.assertEquals(10, userList.getSize());//当前页的数据量
        Assert.assertEquals(5, userList.getTotal());//总查询结果量
        Assert.assertEquals(1, userList.getPages());//总页数
        // 具体查询的结果，返回值是List<T>
        userList.getRecords().forEach(System.out::println);
    }

}
