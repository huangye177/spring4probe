package io.spring.rabbitmq;

import io.spring.redis.RedisApp;
import io.spring.redis.RedisConfig;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RabbitMQApp {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQApp.class);

	final static String queueName = "spring-boot";
	
    public static void main(String[] args) throws Exception
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // invoke the CONFIG
        /*
         * application context then starts the message listener container, and
         * the message listener container bean starts listening for messages
         */
        ctx.register(RabbitMQConfig.class);
        ctx.refresh();
        
        RabbitTemplate rabbitTemplate = ctx.getBean(RabbitTemplate.class);

        System.out.println("* Waiting 3 seconds...");
        Thread.sleep(3000);
        System.out.println("* Sending message...");
        
        rabbitTemplate.convertAndSend(queueName, "* Hello from RabbitMQ!");

        System.exit(0);
    }
}
