package io.spring.datamongodb;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "io.spring.datamongodb")
public class DataMongoDBConfig {

	public @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) throws UnknownHostException
    {
        return new MongoTemplate(mongoClient, "mongotestdb");
    }

    public @Bean
    MongoClient mongoClient() throws UnknownHostException
    {
        return new MongoClient("localhost");
    }
}
