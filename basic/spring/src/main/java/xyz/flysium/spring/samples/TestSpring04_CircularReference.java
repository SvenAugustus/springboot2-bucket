/*
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.spring.samples;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.flysium.spring.samples.circularreference.A;

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
