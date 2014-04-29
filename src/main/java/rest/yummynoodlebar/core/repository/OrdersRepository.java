package rest.yummynoodlebar.core.repository;

import java.util.List;
import java.util.UUID;

import rest.yummynoodlebar.core.domain.Order;
import rest.yummynoodlebar.core.events.CreateEvent;
import rest.yummynoodlebar.core.events.DeleteEvent;
import rest.yummynoodlebar.core.events.RequestReadEvent;
import rest.yummynoodlebar.core.events.UpdatedEvent;
import rest.yummynoodlebar.core.events.orders.*;

//TODO, make this event based again, with persistence integration events.
public interface OrdersRepository {

  Order save(Order order);

  void delete(UUID key);

  Order findById(UUID key);

  List<Order> findAll();
}
