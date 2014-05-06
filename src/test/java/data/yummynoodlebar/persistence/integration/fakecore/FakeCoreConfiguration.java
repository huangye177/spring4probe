package data.yummynoodlebar.persistence.integration.fakecore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import data.yummynoodlebar.core.services.OrderStatusUpdateService;

/*
 * a new test-only Spring Configuration. This will stand in the place of any Core domain Spring configuration
 * 
 * a standard @Configuration, simply creating a new bean instance of the type OrderStatusUpdateService
 */
@Configuration
public class FakeCoreConfiguration
{
    @Bean
    OrderStatusUpdateService orderStatusUpdateService()
    {
        return new CountingOrderStatusService();
    }
}
