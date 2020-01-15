package xyz.flysium.spring.samples;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Pet {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Pet{" +
        "name='" + name + '\'' +
        '}';
  }
}
