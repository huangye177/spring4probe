package rest.yummynoodlebar.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import rest.yummynoodlebar.core.events.orders.OrderDetails;
import rest.yummynoodlebar.core.events.orders.OrderDetailsEvent;
import rest.yummynoodlebar.core.events.orders.RequestAllOrdersEvent;
import rest.yummynoodlebar.core.events.orders.RequestOrderDetailsEvent;
import rest.yummynoodlebar.core.services.OrderService;
import rest.yummynoodlebar.rest.domain.Order;

@Controller
@RequestMapping("/aggregators/orders")
public class OrderQueriesController
{
    private static Logger LOG = LoggerFactory.getLogger(OrderQueriesController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Order> getAllOrders()
    {
        List<Order> orders = new ArrayList<Order>();
        for (OrderDetails detail : orderService.requestAllOrders(new RequestAllOrdersEvent()).getOrdersDetails())
        {
            orders.add(Order.fromOrderDetails(detail));
        }
        return orders;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Order> viewOrder(@PathVariable String id)
    {

        OrderDetailsEvent details = orderService.requestOrderDetails(new RequestOrderDetailsEvent(UUID.fromString(id)));

        if (!details.isEntityFound())
        {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }

        Order order = Order.fromOrderDetails(details.getOrderDetails());

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
}
