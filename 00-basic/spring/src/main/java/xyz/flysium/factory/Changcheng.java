package xyz.flysium.factory;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Changcheng implements Car {

  @Override
  public String getName() {
    return "Changcheng Car";
  }

  @Override
  public String getPrice() {
    return "50000 RMB";
  }
//
//  @Override
//  public String toString() {
//    return "Changcheng{"
//        + "name=" + getName()
//        + ",price=" + getPrice()
//        + "}";
//  }

}
