package rest.yummynoodlebar.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static rest.yummynoodlebar.rest.controller.fixture.RestDataFixture.YUMMY_ITEM;
import static rest.yummynoodlebar.rest.controller.fixture.RestDataFixture.standardOrderJSON;
import static rest.yummynoodlebar.rest.controller.fixture.RestEventFixtures.orderCreated;

import java.nio.charset.Charset;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import rest.yummynoodlebar.core.events.orders.CreateOrderEvent;
import rest.yummynoodlebar.core.services.OrderService;
import rest.yummynoodlebar.rest.controller.OrderCommandsController;

public class CreateNewOrderIntegrationTest
{

    MockMvc mockMvc;

    @InjectMocks
    OrderCommandsController controller;

    @Mock
    OrderService orderService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

        when(orderService.createOrder(any(CreateOrderEvent.class))).thenReturn(
                orderCreated(UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13")));
    }

    // createOrder - validation?

    @Test
    public void thatCreateOrderUsesHttpCreated() throws Exception
    {

        this.mockMvc.perform(
                post("/aggregators/orders")
                        .content(standardOrderJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /*
     * Create with HTTP basic authentication, used to replace test class:
     * OrderTests
     */
    @Test
    public void thatCreateOrderUsesHttpBasicCreated() throws Exception
    {

        byte[] encodedAuthorisation = org.springframework.security.crypto.codec.Base64.encode("http:http".getBytes(Charset.forName("UTF-8")));

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:8080/aggregators/orders").header("Authentication", new String(encodedAuthorisation))
                        .content(standardOrderJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void thatCreateOrderRendersAsJson() throws Exception
    {

        this.mockMvc.perform(
                post("/aggregators/orders")
                        .content(standardOrderJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items['" + YUMMY_ITEM + "']").value(12))
                .andExpect(jsonPath("$.key").value("f3512d26-72f6-4290-9265-63ad69eccc13"));
    }

    @Test
    public void thatCreateOrderPassesLocationHeader() throws Exception
    {

        this.mockMvc.perform(
                post("/aggregators/orders")
                        .content(standardOrderJSON())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", Matchers.endsWith("/aggregators/orders/f3512d26-72f6-4290-9265-63ad69eccc13")));
    }
}
