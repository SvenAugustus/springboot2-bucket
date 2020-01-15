package xyz.flysium.spring.samples.circularreference;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class B {

  public B() {
    System.out.println("B init~");
  }

  private C c;

  public C getC() {
    return c;
  }

  public void setC(C c) {
    this.c = c;
  }

}
