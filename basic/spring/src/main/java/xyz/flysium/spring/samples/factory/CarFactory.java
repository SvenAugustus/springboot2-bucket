package xyz.flysium.spring.samples.factory;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class CarFactory {

  public CarFactory() {
    System.out.println("carFactory init~");
  }

  public Car getCar(String name) throws Exception {
    if ("changcheng".equals(name)) {
      return new Changcheng();
    } else {
      throw new Exception("car not fond");
    }
  }

}
