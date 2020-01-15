package com.github.flysium.io.springboot;

import static org.hamcrest.Matchers.containsString;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootApplicationTests {

  @ClassRule
  public static OutputCapture out = new OutputCapture();

  @Test
  public void contextLoads() {
    out.expect(containsString("1,Guangzhou,Guangdong,CN"));
  }
}
