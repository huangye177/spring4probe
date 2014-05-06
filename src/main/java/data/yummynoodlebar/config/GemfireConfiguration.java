package data.yummynoodlebar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import data.yummynoodlebar.persistence.repository.OrderStatusRepository;
import data.yummynoodlebar.persistence.services.StatusUpdateGemfireNotificationListener;

@Configuration
/*
 * mports a traditional XML based Spring configuration. Currently Spring Data
 * GemFire is significantly easier to configure using XML. For this reason, XML
 * configuration is still recommended for Spring Data GemFire.
 * 
 * This configuration uses the GemFire Spring configuration namespace and
 * creates a GemFire DataSource. This is a connection to a GemFire data grid and
 * is, in this case, connecting to the GemFire server running on
 * localhost:40404.
 * 
 * The bean yummyTemplate is the instance of GemFireTemplate that is used in the
 * test; It is set up to communicate with a specific GemFire Region,
 * YummyNoodleOrder, which must exist in the server
 */
@ImportResource({ "classpath:gemfire/client.xml" })
@EnableTransactionManagement
@EnableGemfireRepositories(basePackages = "data.yummynoodlebar.persistence.repository",
        includeFilters = @ComponentScan.Filter(value = { OrderStatusRepository.class }, type = FilterType.ASSIGNABLE_TYPE))
public class GemfireConfiguration
{

    @Bean
    public StatusUpdateGemfireNotificationListener statusUpdateListener()
    {
        return new StatusUpdateGemfireNotificationListener();
    }

}
