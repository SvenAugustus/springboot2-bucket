package xyz.flysium.spring.samples.circularreference;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class A {

  public A() {
    System.out.println("A init~");
  }

  private B b;

  public B getB() {
    return b;
  }

  public void setB(B b) {
    this.b = b;
  }

}
