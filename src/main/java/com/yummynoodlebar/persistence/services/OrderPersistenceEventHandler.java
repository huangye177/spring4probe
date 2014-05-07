package com.yummynoodlebar.persistence.services;

import java.util.ArrayList;
import java.util.List;

import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.DeleteOrderEvent;
import com.yummynoodlebar.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.events.orders.OrderDeletedEvent;
import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.events.orders.OrderDetailsEvent;
import com.yummynoodlebar.events.orders.OrderStatusEvent;
import com.yummynoodlebar.events.orders.OrderUpdatedEvent;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.events.orders.RequestOrderDetailsEvent;
import com.yummynoodlebar.events.orders.RequestOrderStatusEvent;
import com.yummynoodlebar.events.orders.SetOrderPaymentEvent;
import com.yummynoodlebar.events.orders.SetOrderStatusEvent;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.repository.OrdersRepository;

public class OrderPersistenceEventHandler implements OrderPersistenceService
{

    private final OrdersRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public OrderPersistenceEventHandler(
            final OrdersRepository orderRepository,
            final OrderStatusRepository orderStatusRepository)
    {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderStatusEvent setOrderStatus(SetOrderStatusEvent event)
    {

        OrderStatus status = OrderStatus.fromStatusDetails(event.getOrderStatus());

        status = orderStatusRepository.save(status);

        return new OrderStatusEvent(status.getId(), status.toStatusDetails());
    }

    @Override
    public OrderCreatedEvent createOrder(CreateOrderEvent createOrderEvent)
    {
        Order order = Order.fromOrderDetails(createOrderEvent.getDetails());

        order = orderRepository.save(order);

        return new OrderCreatedEvent(order.getKey(), order.toOrderDetails());
    }

    @Override
    public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent)
    {
        List<OrderDetails> generatedDetails = new ArrayList<OrderDetails>();
        for (Order order : orderRepository.findAll())
        {
            generatedDetails.add(order.toOrderDetails());
        }
        return new AllOrdersEvent(generatedDetails);
    }

    @Override
    public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent)
    {

        Order order = orderRepository.findById(requestOrderDetailsEvent.getKey());

        if (order == null)
        {
            return OrderDetailsEvent.notFound(requestOrderDetailsEvent.getKey());
        }

        return new OrderDetailsEvent(
                requestOrderDetailsEvent.getKey(),
                order.toOrderDetails());
    }

    @Override
    public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent)
    {
        Order order = orderRepository.findById(setOrderPaymentEvent.getKey());

        if (order == null)
        {
            return OrderUpdatedEvent.notFound(setOrderPaymentEvent.getKey());
        }

        // TODO, handling payment details...

        return new OrderUpdatedEvent(order.getKey(), order.toOrderDetails());
    }

    @Override
    public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent)
    {

        Order order = orderRepository.findById(deleteOrderEvent.getKey());

        if (order == null)
        {
            return OrderDeletedEvent.notFound(deleteOrderEvent.getKey());
        }

        orderRepository.delete(deleteOrderEvent.getKey());

        return new OrderDeletedEvent(deleteOrderEvent.getKey(), order.toOrderDetails());
    }

    @Override
    public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderDetailsEvent)
    {

        OrderStatus orderStatus = orderStatusRepository.findLatestById(requestOrderDetailsEvent.getKey());

        if (orderStatus == null)
        {
            return OrderStatusEvent.notFound(requestOrderDetailsEvent.getKey());
        }

        return new OrderStatusEvent(requestOrderDetailsEvent.getKey(), orderStatus.toStatusDetails());
    }
}
