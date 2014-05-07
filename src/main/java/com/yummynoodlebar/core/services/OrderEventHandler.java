package com.yummynoodlebar.core.services;

import java.util.Date;
import java.util.UUID;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.DeleteOrderEvent;
import com.yummynoodlebar.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.events.orders.OrderStatusDetails;
import com.yummynoodlebar.events.orders.OrderStatusEvent;
import com.yummynoodlebar.events.orders.OrderUpdatedEvent;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.events.orders.RequestOrderDetailsEvent;
import com.yummynoodlebar.events.orders.RequestOrderStatusEvent;
import com.yummynoodlebar.events.orders.SetOrderPaymentEvent;
import com.yummynoodlebar.events.orders.SetOrderStatusEvent;
import com.yummynoodlebar.persistence.services.OrderPersistenceService;

public class OrderEventHandler implements OrderService
{

    private final OrderPersistenceService ordersPersistenceService;

    public OrderEventHandler(final OrderPersistenceService ordersPersistenceService)
    {
        this.ordersPersistenceService = ordersPersistenceService;
    }

    @Override
    public OrderCreatedEvent createOrder(CreateOrderEvent createOrderEvent)
    {

        // TODO, add validation of menu items
        // TODO, add order total calculation
        // TODO, add order time estimate calculation
        // TODO Think transaction boundary. Order and OrderStatus should be
        // atomic
        OrderCreatedEvent event = ordersPersistenceService.createOrder(createOrderEvent);

        // TODO, where should this go?
        OrderStatusEvent orderStatusEvent = ordersPersistenceService.setOrderStatus(
                new SetOrderStatusEvent(event.getNewOrderKey(), new OrderStatusDetails(event.getNewOrderKey(),
                        UUID.randomUUID(), new Date(), "Order Created")));

        return event;
    }

    @Override
    public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent)
    {
        return ordersPersistenceService.requestAllOrders(requestAllCurrentOrdersEvent);
    }

    @Override
    public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent)
    {
        return ordersPersistenceService.requestOrderDetails(requestOrderDetailsEvent);
    }

    @Override
    public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent)
    {
        return ordersPersistenceService.setOrderPayment(setOrderPaymentEvent);
    }

    @Override
    public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent)
    {

        OrderDetailsEvent orderDetailsEvent = ordersPersistenceService.requestOrderDetails(new RequestOrderDetailsEvent(deleteOrderEvent.getKey()));

        if (!orderDetailsEvent.isEntityFound())
        {
            return OrderDeletedEvent.notFound(deleteOrderEvent.getKey());
        }

        Order order = Order.fromOrderDetails(orderDetailsEvent.getOrderDetails());

        if (!order.canBeDeleted())
        {
            return OrderDeletedEvent.deletionForbidden(deleteOrderEvent.getKey(), order.toOrderDetails());
        }

        ordersPersistenceService.deleteOrder(deleteOrderEvent);

        return new OrderDeletedEvent(deleteOrderEvent.getKey(), order.toOrderDetails());
    }

    @Override
    public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderDetailsEvent)
    {
        return ordersPersistenceService.requestOrderStatus(requestOrderDetailsEvent);
    }
}
