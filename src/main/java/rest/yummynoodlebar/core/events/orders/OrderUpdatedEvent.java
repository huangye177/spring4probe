package rest.yummynoodlebar.core.events.orders;

import java.util.UUID;

import rest.yummynoodlebar.core.events.UpdatedEvent;

public class OrderUpdatedEvent extends UpdatedEvent {

  private UUID key;
  private OrderDetails orderDetails;

  public OrderUpdatedEvent(UUID key, OrderDetails orderDetails) {
    this.key = key;
    this.orderDetails = orderDetails;
  }

  public UUID getKey() {
    return key;
  }

  public OrderDetails getOrderDetails() {
    return orderDetails;
  }
}
