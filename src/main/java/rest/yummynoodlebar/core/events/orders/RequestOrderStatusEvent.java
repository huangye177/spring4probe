package rest.yummynoodlebar.core.events.orders;

import java.util.UUID;

import rest.yummynoodlebar.core.events.RequestReadEvent;

public class RequestOrderStatusEvent extends RequestReadEvent {
  private UUID key;

  public RequestOrderStatusEvent(UUID key) {
    this.key = key;
  }

  public UUID getKey() {
    return key;
  }
}
