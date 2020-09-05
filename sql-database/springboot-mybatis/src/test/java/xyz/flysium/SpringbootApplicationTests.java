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

import static org.hamcrest.Matchers.containsString;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.flysium.dao.entity.Account;
import xyz.flysium.dao.repository.AccountMapper;
import xyz.flysium.dao.repository.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootApplicationTests {

    @ClassRule
    public static OutputCaptureRule out = new OutputCaptureRule();

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        out.expect(containsString("1,Guangzhou,Guangdong,CN"));
    }

    @Test
    public void findAll() {
        Account account = new Account();
        account.setLoginName("SvenAugustus");
        account.setPassword("root123");
        account.setNickName("zeno");
        account.setAge(18);
        account.setLocation("Guangzhou");
        accountMapper.add(account);
        final List<Account> all = this.accountMapper.findAll();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals("SvenAugustus", all.get(0).getLoginName());
    }

    /**
     * Page 分页查询
     */
    @Test
    public void findAllAsPage() {
        Account account = new Account();
        account.setLoginName("SvenAugustus");
        account.setPassword("root123");
        account.setNickName("zeno");
        account.setAge(28);
        account.setLocation("Guangzhou");
        accountMapper.add(account);
        PageInfo<Account> pageInfo = PageHelper.startPage(1, 10).doSelectPageInfo(() -> {
            accountMapper.findAll();
        });
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getPageSize());
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        Assert.assertEquals(1, pageInfo.getPageNum());//当前页数
        Assert.assertEquals(10, pageInfo.getPageSize());//当前页的数据量
        Assert.assertEquals(1, pageInfo.getTotal());//总查询结果量
        Assert.assertEquals(1, pageInfo.getPages());//总页数
        // 具体查询的结果，返回值是List<T>
        pageInfo.getList().forEach(System.out::println);
    }

}
