package rest.yummynoodlebar.core.events.orders;

import java.util.UUID;

import rest.yummynoodlebar.core.events.RequestReadEvent;

public class RequestOrderDetailsEvent extends RequestReadEvent {
  private UUID key;

  public RequestOrderDetailsEvent(UUID key) {
    this.key = key;
  }

  public UUID getKey() {
    return key;
  }
}
