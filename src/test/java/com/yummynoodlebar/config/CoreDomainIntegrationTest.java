package com.yummynoodlebar.config;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yummynoodlebar.core.services.MenuService;
import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.RequestAllMenuItemsEvent;
import com.yummynoodlebar.events.orders.AllOrdersEvent;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.events.orders.RequestAllOrdersEvent;

@RunWith(SpringJUnit4ClassRunner.class)
/*
 * The Core domain's configuration will be created using Spring JavaConfig in a
 * class called CoreConfig. The persistence domain's configuration will be
 * created in a class call PersistenceConfig
 */
@ContextConfiguration(classes = { PersistenceConfig.class, CoreConfig.class })
public class CoreDomainIntegrationTest
{

    @Autowired
    MenuService menuService;

    @Autowired
    OrderService orderService;

    @Test
    public void thatAllMenuItemsReturned()
    {

        AllMenuItemsEvent allMenuItems = menuService.requestAllMenuItems(new RequestAllMenuItemsEvent());

        assertEquals(3, allMenuItems.getMenuItemDetails().size());

    }

    @Test
    public void addANewOrderToTheSystem()
    {

        CreateOrderEvent ev = new CreateOrderEvent(new OrderDetails());

        orderService.createOrder(ev);

        AllOrdersEvent allOrders = orderService.requestAllOrders(new RequestAllOrdersEvent());

        assertEquals(1, allOrders.getOrdersDetails().size());
    }

}
