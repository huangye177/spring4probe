package rest.yummynoodlebar.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static rest.yummynoodlebar.rest.controller.fixture.RestDataFixture.YUMMY_ITEM;
import static rest.yummynoodlebar.rest.controller.fixture.RestEventFixtures.orderDetailsEvent;
import static rest.yummynoodlebar.rest.controller.fixture.RestEventFixtures.orderDetailsNotFound;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import rest.yummynoodlebar.core.events.orders.RequestOrderDetailsEvent;
import rest.yummynoodlebar.core.services.OrderService;
import rest.yummynoodlebar.rest.controller.OrderQueriesController;

// {!begin intro}
// {!begin opening}
public class ViewOrderIntegrationTest
{
    // {!end opening}

    /*
     * MockMvc is a relatively new part of Spring MVC. It provides a test method
     * with a Spring MVC Controller, including all of its annotations, routing,
     * and URI templates. It does this by initializing the MVC Controller
     * classes in a full MVC environment, including the DispatcherServlet and
     * then running assertions against that
     */
    MockMvc mockMvc;

    @InjectMocks
    OrderQueriesController controller;

    @Mock
    OrderService orderService;

    UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        /*
         * adding JSON message conversion, because you expect JSON when you ask
         * for the current state of an Order.
         */
        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    // {!end intro}

    @Test
    public void thatViewOrderUsesHttpNotFound() throws Exception
    {

        when(orderService.requestOrderDetails(any(RequestOrderDetailsEvent.class))).thenReturn(
                orderDetailsNotFound(key));

        this.mockMvc.perform(
                get("/aggregators/orders/{id}", key.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void thatViewOrderUsesHttpOK() throws Exception
    {

        when(orderService.requestOrderDetails(any(RequestOrderDetailsEvent.class))).thenReturn(
                orderDetailsEvent(key));

        this.mockMvc.perform(
                get("/aggregators/orders/{id}", key.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // {!begin thatViewOrderRendersCorrectly}
    @Test
    public void thatViewOrderRendersCorrectly() throws Exception
    {

        when(orderService.requestOrderDetails(any(RequestOrderDetailsEvent.class))).thenReturn(
                orderDetailsEvent(key));

        this.mockMvc.perform(
                get("/aggregators/orders/{id}", key.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items['" + YUMMY_ITEM + "']").value(12))
                .andExpect(jsonPath("$.key").value(key.toString()));
    }
    // {!end thatViewOrderRendersCorrectly}
}
