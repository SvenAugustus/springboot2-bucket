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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class TestSpring03_Autowire {

    public static void main(String[] args) {
        //    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        //        "c001/t3_autowire.xml");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("t3_autowireGlobal.xml");

        // autowire byName 注入
        Person2 personAutowireByName = context.getBean("personAutowireByName", Person2.class);
        System.out.println(ToStringBuilder.reflectionToString(personAutowireByName, ToStringStyle.MULTI_LINE_STYLE));
    }

}
