package rest.yummynoodlebar.core.repository;

import java.util.*;

import rest.yummynoodlebar.core.domain.Order;
import rest.yummynoodlebar.core.events.CreateEvent;
import rest.yummynoodlebar.core.events.DeleteEvent;
import rest.yummynoodlebar.core.events.RequestReadEvent;
import rest.yummynoodlebar.core.events.UpdatedEvent;
import rest.yummynoodlebar.core.events.orders.*;

public class OrdersMemoryRepository implements OrdersRepository {

  private Map<UUID, Order> orders;

  public OrdersMemoryRepository(final Map<UUID, Order> orders) {
    this.orders = Collections.unmodifiableMap(orders);
  }

  @Override
  public synchronized Order save(Order order) {

    Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
    modifiableOrders.put(order.getKey(), order);
    this.orders = Collections.unmodifiableMap(modifiableOrders);

    return order;
  }

  @Override
  public synchronized void delete(UUID key) {
    if (orders.containsKey(key)) {
      Map<UUID, Order> modifiableOrders = new HashMap<UUID, Order>(orders);
      modifiableOrders.remove(key);
      this.orders = Collections.unmodifiableMap(modifiableOrders);
    }
  }

  @Override
  public Order findById(UUID key) {
    return orders.get(key);
  }

  @Override
  public List<Order> findAll() {
    return Collections.unmodifiableList(new ArrayList<Order>(orders.values()));
  }
}
