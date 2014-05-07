package com.yummynoodlebar.web.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yummynoodlebar.core.services.OrderService;
import com.yummynoodlebar.events.orders.CreateOrderEvent;
import com.yummynoodlebar.events.orders.OrderCreatedEvent;
import com.yummynoodlebar.events.orders.OrderDetails;
import com.yummynoodlebar.web.domain.Basket;
import com.yummynoodlebar.web.domain.CustomerInfo;

@Controller
@RequestMapping("/checkout")
public class CheckoutController
{

    private static final Logger LOG = LoggerFactory
            .getLogger(BasketCommandController.class);

    @Autowired
    private Basket basket;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String checkout()
    {
        return "/checkout";
    }

    /*
     * The Command Object, in this case CustomerInfo, is a class that Spring
     * will map the POST variables onto, parsing them into the given types on
     * the class -- This process of automatic parsing and conversion is known as
     * Binding
     * 
     * the Command Object is ready to represent the incoming POST request,
     * namely to use the Command Object customerInfo, of type CustomerInfo
     * 
     * Using CustomerInfo as a parameter means that Spring will perform Binding
     * of the request parameters against it. If the binding did not complete
     * successfully, then the result is stored in the BindingResult parameter
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doCheckout(@Valid @ModelAttribute("customerInfo") CustomerInfo customer, BindingResult result, RedirectAttributes redirectAttrs)
    {
        if (result.hasErrors())
        {
            // errors in the form
            // show the checkout form again
            System.out.println("*** found error in Binding");
            return "/checkout";
        }

        LOG.debug("No errors, continue with processing for Customer {}:",
                customer.getName());

        OrderDetails order = basket
                .createOrderDetailsWithCustomerInfo(customer);

        OrderCreatedEvent event = orderService
                .createOrder(new CreateOrderEvent(order));

        UUID key = event.getNewOrderKey();

        redirectAttrs.addFlashAttribute("message",
                "Your order has been accepted!");
        // redirectAttrs.addFlashAttribute("customer.getName() message",
        // customer.getName());

        basket.clear();
        LOG.debug("Basket now has {} items", basket.getSize());

        return "redirect:/order/" + key.toString();
    }

    /*
     * When the page is rendered for the first time on a GET /checkout, the
     * method getCustomerInfo is called to generate the 'customerInfo' property
     * in the model
     */
    @ModelAttribute("customerInfo")
    private CustomerInfo getCustomerInfo()
    {
        return new CustomerInfo();
    }

    @ModelAttribute("basket")
    public Basket getBasket()
    {
        return basket;
    }

    public void setBasket(Basket basket)
    {
        this.basket = basket;
    }
}
