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

package xyz.flysium.circularreference;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class C {

//  public C(A a) {
//    this.a=a;
//    System.out.println("C init~");
//  }

  public C() {
    System.out.println("C init~");
  }

  private A a;

  public A getA() {
    return a;
  }

  public void setA(A a) {
    this.a = a;
  }

}
