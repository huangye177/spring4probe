package rest.yummynoodlebar.config;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rest.yummynoodlebar.core.domain.Order;
import rest.yummynoodlebar.core.repository.OrdersMemoryRepository;
import rest.yummynoodlebar.core.repository.OrdersRepository;
import rest.yummynoodlebar.core.services.OrderEventHandler;
import rest.yummynoodlebar.core.services.OrderService;

@Configuration
public class CoreConfig
{

    @Bean
    public OrderService createService(OrdersRepository repo)
    {
        return new OrderEventHandler(repo);
    }

    @Bean
    public OrdersRepository createRepo()
    {
        return new OrdersMemoryRepository(new HashMap<UUID, Order>());
    }
}