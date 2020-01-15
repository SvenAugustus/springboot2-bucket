package xyz.flysium.spring.samples.circularreference;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class C {

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
