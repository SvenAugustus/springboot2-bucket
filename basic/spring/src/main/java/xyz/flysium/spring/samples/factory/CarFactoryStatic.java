package xyz.flysium.spring.samples.factory;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public final class CarFactoryStatic {

  private CarFactoryStatic() {
  }

  public static Car getCar(String name) throws Exception {
    if ("changcheng".equals(name)) {
      return new Changcheng();
    } else {
      throw new Exception("car not fond");
    }
  }

}
