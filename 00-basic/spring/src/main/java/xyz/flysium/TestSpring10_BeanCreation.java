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
public class TestSpring10_BeanCreation {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "tt10_BeanCreation.xml");
    System.out.println("----------------------准备获取-------------------");
    //  MyBean mybean = context.getBean("myBean", MyBean.class);
    MyBean mybean = context.getBean("myFactoryBean", MyBean.class);
    System.out.println(ToStringBuilder.reflectionToString(mybean, ToStringStyle.MULTI_LINE_STYLE));
//    MyBean mybean2 = context.getBean("myFactoryBean", MyBean.class);
//    System.out.println(ToStringBuilder.reflectionToString(mybean2, ToStringStyle.MULTI_LINE_STYLE));
    System.out.println("----------------------准备销毁-------------------");
    context.close();
  }

}
