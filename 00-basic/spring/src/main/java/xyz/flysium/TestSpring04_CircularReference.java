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
import xyz.flysium.circularreference.A;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class TestSpring04_CircularReference {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "t4_CircularReference.xml");

    // 循环依赖
    //    循环依赖的bean都是singleton 成功
    //    循环依赖的bean都是prototype 失败
    //    同时有singleton和prototype ，也是成功的。
    A a = context.getBean("a", A.class);
    System.out.println(ToStringBuilder.reflectionToString(a, ToStringStyle.MULTI_LINE_STYLE));

    A a2 = context.getBean("a", A.class);
    System.out.println(ToStringBuilder.reflectionToString(a2, ToStringStyle.MULTI_LINE_STYLE));

    System.out.println(a == a2);
    System.out.println(a.getB() == a2.getB());
    System.out.println(a.getB().getC() == a2.getB().getC());
    System.out.println(a.getB().getC().getA() == a2.getB().getC().getA());
    System.out.println(a.getB().getC().getA() == a);
  }

}
