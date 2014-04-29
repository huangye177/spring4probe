package rest.yummynoodlebar.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static rest.yummynoodlebar.rest.controller.fixture.RestEventFixtures.orderDetailsEvent;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import rest.yummynoodlebar.core.events.orders.RequestOrderDetailsEvent;
import rest.yummynoodlebar.core.services.OrderService;
import rest.yummynoodlebar.rest.controller.OrderQueriesController;

public class ViewOrderXmlIntegrationTest
{

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
         * mockMvc object is being set up to support both XML and JSON
         * 
         * runtime 'com.fasterxml.jackson.core:jackson-core:2.2.2'
         * 
         * runtime 'com.fasterxml.jackson.core:jackson-databind:2.2.2'
         * 
         * runtime 'javax.xml.bind:jaxb-api:2.2.9'
         */
        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(),
                        new Jaxb2RootElementHttpMessageConverter()).build();
    }

    @Test
    public void thatViewOrderRendersXMLCorrectly() throws Exception
    {

        when(orderService.requestOrderDetails(any(RequestOrderDetailsEvent.class))).thenReturn(
                orderDetailsEvent(key));

        this.mockMvc.perform(
                get("/aggregators/orders/{id}", key.toString())
                        .accept(MediaType.TEXT_XML))
                .andDo(print())
                .andExpect(content().contentType(MediaType.TEXT_XML))
                .andExpect(xpath("/order/key").string(key.toString()));
    }

    @Test
    public void thatViewOrderRendersJsonCorrectly() throws Exception
    {

        when(orderService.requestOrderDetails(any(RequestOrderDetailsEvent.class))).thenReturn(
                orderDetailsEvent(key));

        // TODOCUMENT JSON Path in use here (really like this)

        this.mockMvc.perform(
                get("/aggregators/orders/{id}", key.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.key").value(key.toString()));
    }
}