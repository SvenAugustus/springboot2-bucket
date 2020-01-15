package xyz.flysium.spring.samples;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.flysium.spring.samples.annotation.controller.MainController;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class TestSpring05_annotation {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
        "t5_annotation.xml");

    MainController controller = context.getBean(MainController.class);
    System.out.println(controller.login("zhangsan", "123"));
    System.out.println(controller.login("zhangsan", "456"));
  }

}
