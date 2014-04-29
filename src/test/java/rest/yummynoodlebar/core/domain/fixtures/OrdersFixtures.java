package rest.yummynoodlebar.core.domain.fixtures;

import java.util.Collections;
import java.util.Date;

import rest.yummynoodlebar.core.domain.Order;
import rest.yummynoodlebar.core.events.orders.OrderDetails;

public class OrdersFixtures {

  public static final String YUMMY_ITEM = "yummy_core";

  public static Order standardOrder() {
    Order order = new Order(new Date());

    order.setOrderItems(Collections.singletonMap(YUMMY_ITEM, 12));

    return order;
  }

  /*
   * Twin of the above, to improve readability
   */
  public static OrderDetails standardOrderDetails() {
    return standardOrder().toOrderDetails();
  }

}
