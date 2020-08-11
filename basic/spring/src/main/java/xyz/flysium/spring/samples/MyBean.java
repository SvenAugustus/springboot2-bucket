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

package xyz.flysium.spring.samples;

import javax.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class MyBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware,
    ApplicationContextAware, BeanPostProcessor {

  public MyBean() {
    System.out.println("构造器方法");
  }

  @PostConstruct
  public void postConstruct() {
    System.out.println("@PostConstruct");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("InitializingBean 的 afterPropertiesSet");
  }

  public void initMethod() throws Exception {
    System.out.println("Bean配置指定的 init-method ,调用 initMethod");
  }

  public void destroyMethod() throws Exception {
    System.out.println("Bean配置指定的 destroy-method ,调用 destroyMethod");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("DisposableBean 的 destroy");
  }

  @Override
  public void setBeanName(String name) {
    System.out.println("BeanNameAware 的 setBeanName");
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("BeanFactoryAware 的 setBeanFactory");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("ApplicationContextAware 的 setApplicationContext");
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    // 注意，这个是全局的，所有的bean 初始化完成 一阶段都会在这里进行
    System.out.println("BeanPostProcessor 的 postProcessBeforeInitialization " + beanName);
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    // 注意，这个是全局的，所有的bean 初始化完成 二阶段都会在这里进行
    System.out.println("BeanPostProcessor 的 postProcessAfterInitialization " + beanName);
    return bean;
  }

}
