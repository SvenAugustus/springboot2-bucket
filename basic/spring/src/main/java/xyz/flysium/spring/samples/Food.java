package xyz.flysium.spring.samples;

/**
 * @author Sven Augustus
 * @version 1.0
 */
public class Food {

  private int price;

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Food{" +
        "price=" + price +
        '}';
  }
}
