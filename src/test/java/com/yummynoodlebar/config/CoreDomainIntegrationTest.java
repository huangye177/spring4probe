package com.yummynoodlebar.config;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yummynoodlebar.core.events.orders.AllOrdersEvent;
import com.yummynoodlebar.core.events.orders.CreateOrderEvent;
import com.yummynoodlebar.core.events.orders.OrderDetails;
import com.yummynoodlebar.core.events.orders.RequestAllOrdersEvent;
import com.yummynoodlebar.core.services.OrderService;

/*
 * constructs an ApplicationContext using JavaConfig as specified on the @ContextConfiguration annotation
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CoreConfig.class })
public class CoreDomainIntegrationTest
{

    @Autowired
    OrderService orderService;

    @Test
    public void addANewOrderToTheSystem()
    {

        CreateOrderEvent ev = new CreateOrderEvent(new OrderDetails());

        orderService.createOrder(ev);

        AllOrdersEvent allOrders = orderService.requestAllOrders(new RequestAllOrdersEvent());

        assertEquals(1, allOrders.getOrdersDetails().size());
    }
}