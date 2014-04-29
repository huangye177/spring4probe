package rest.yummynoodlebar.core.events.orders;

import rest.yummynoodlebar.core.events.CreateEvent;

public class CreateOrderEvent extends CreateEvent {
  private OrderDetails details;

  public CreateOrderEvent(OrderDetails details) {
    this.details = details;
  }

  public OrderDetails getDetails() {
    return details;
  }
}
