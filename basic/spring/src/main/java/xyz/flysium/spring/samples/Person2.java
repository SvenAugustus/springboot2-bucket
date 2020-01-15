package xyz.flysium.spring.samples;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Person2 {

  private String name;
  private Pet pet;

  public Person2() {
    System.out.println("Person2 init~");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }
}
