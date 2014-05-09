package io.spring.redis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisApp
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisApp.class);

    public static void main(String[] args) throws Exception
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // invoke the CONFIG
        /*
         * application context then starts the message listener container, and
         * the message listener container bean starts listening for messages
         */
        ctx.register(RedisConfig.class);
        ctx.refresh();

        /*
         * retrieves the StringRedisTemplate bean from the application context
         * and uses it to send a "Hello from Redis!" message on the "chat" topic
         */
        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        CountDownLatch latch = ctx.getBean(CountDownLatch.class);

        LOGGER.info("* Sending message...");
        template.convertAndSend("chat", "Hello from Redis!");

        latch.await();
        System.exit(0);

    }
}
