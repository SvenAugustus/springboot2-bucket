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

package xyz.flysium.ws.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import xyz.flysium.api.HelloService;
import xyz.flysium.dto.entity.UserInfo;

/**
 * Client.
 *
 * @author Sven Augustus
 */
@SpringBootApplication
public class SampleWsClientApplication {

  private static final String wsdlAddress = "http://localhost:8296/services/HelloService?wsdl";

  public static void main(String[] args) {
    new SpringApplicationBuilder(SampleWsClientApplication.class)
        .web(WebApplicationType.NONE)
        .run(args);
  }

  /**
   * 方式1.代理类工厂方式,需要拿到对方的接口
   */
  @Bean
  CommandLineRunner initJaxWsProxyRunner() {

    return new CommandLineRunner() {

      @Override
      public void run(String... runArgs) throws Exception {
        try {
          // 接口地址
          // 代理工厂
          JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
          // 设置代理地址
          jaxWsProxyFactoryBean.setAddress(wsdlAddress);
          // 设置接口类型
          jaxWsProxyFactoryBean.setServiceClass(HelloService.class);
          // 创建一个代理接口实现
          HelloService cs = (HelloService) jaxWsProxyFactoryBean.create();
          // 数据准备
          String userName = "Sven Augustus";
          // 调用代理接口的方法调用并返回结果
          String result = cs.sayHello(userName);
          System.out.println("方式1.代理类工厂方式---返回结果: " + result);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
  }


  /**
   * 方式2.动态调用方式
   */
  @Bean
  CommandLineRunner initJaxWsDynamicRunner() {

    return new CommandLineRunner() {

      @Override
      public void run(String... runArgs) throws Exception {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects;
        try {
          // invoke("方法名",参数1,参数2,参数3....);
          objects = client.invoke("sayHello", "Sven Augustus");
          System.out.println("方式2.动态调用方式---返回类型: " + objects[0].getClass());
          System.out.println("方式2.动态调用方式---返回数据: " + objects[0]);
        } catch (java.lang.Exception e) {
          e.printStackTrace();
        }
      }
    };
  }

  /**
   * 方式3. 动态调用方式，返回对象User
   */
  @Bean
  CommandLineRunner initJaxWsDynamicRunner2() {

    return new CommandLineRunner() {

      @Override
      public void run(String... runArgs) throws Exception {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        Object[] objects;
        try {
          // invoke("方法名",参数1,参数2,参数3....);
          objects = client.invoke("getUser", "张三");
          System.out.println("方式3.动态调用方式---返回类型: " + objects[0].getClass());
          System.out.println("方式3.动态调用方式---返回数据: " + objects[0]);
          UserInfo user = (UserInfo) objects[0];
          System.out.println("方式3.动态调用方式---返回对象 User.name=" + user.getName());
        } catch (java.lang.Exception e) {
          e.printStackTrace();
        }
      }
    };
  }

  /**
   * 方式4. 客户端代码生成方式
   */

}
