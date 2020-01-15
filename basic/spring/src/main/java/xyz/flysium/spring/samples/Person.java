package xyz.flysium.spring.samples;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Person {

  private String name;
  private int age;
  private String sex;
  private Food food;

  public Person() {
    System.out.println("Person init~");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Food getFood() {
    return food;
  }

  public void setFood(Food food) {
    this.food = food;
  }
}
