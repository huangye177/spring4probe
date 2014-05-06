package data.yummynoodlebar.core.services;


import data.yummynoodlebar.events.orders.OrderStatusEvent;
import data.yummynoodlebar.events.orders.SetOrderStatusEvent;

public interface OrderStatusUpdateService {

    OrderStatusEvent setOrderStatus(SetOrderStatusEvent orderStatusEvent);

}
