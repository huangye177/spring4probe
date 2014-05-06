package data.yummynoodlebar.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

import data.yummynoodlebar.persistence.repository.MenuItemRepository;

/*
 * The guts of the configuration deal with connecting to MongoDB. 
 * It creates a Mongo driver connection and a MongoTemplate to wrap it
 */
@Configuration
@EnableMongoRepositories(basePackages = "data.yummynoodlebar.persistence.repository",
        includeFilters = @ComponentScan.Filter(value = { MenuItemRepository.class },
                type = FilterType.ASSIGNABLE_TYPE))
public class MongoConfiguration
{

    public @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) throws UnknownHostException
    {
        return new MongoTemplate(mongoClient, "yummynoodle");
    }

    public @Bean
    MongoClient mongoClient() throws UnknownHostException
    {
        return new MongoClient("localhost");
    }
}
