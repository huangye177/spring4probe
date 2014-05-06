package data.yummynoodlebar.events.orders;

import data.yummynoodlebar.events.CreateEvent;

public class CreateOrderEvent extends CreateEvent {
  private OrderDetails details;

  public CreateOrderEvent(OrderDetails details) {
    this.details = details;
  }

  public OrderDetails getDetails() {
    return details;
  }
}
