package io.spring.redis;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/*
 * Spring Data Redis provides all the components to send and receive messages with Redis. Specifically, need to configure: 
 * A connection factory
 * A message listener container
 * A Redis template
 * 
 * You’ll use the Redis template to send messages and you will register the Receiver with the message listener container 
 * so that it will receive messages;
 * 
 * The connection factory drives both the template and the message listener container, 
 * enabling them to connect to the Redis server
 */
@Configuration
public class RedisConfig
{
    /*
     * a JedisConnectionFactory is a Redis connection factory based on the Jedis
     * Redis library. That connection factory is injected into both the message
     * listener container and the Redis template
     */
    @Bean
    JedisConnectionFactory connectionFactory()
    {
        return new JedisConnectionFactory();
    }

    /*
     * registered a a message listener in the message listener container defined
     * in container and will listen for messages on the "chat" topic
     */
    @Bean
    RedisMessageListenerContainer container(JedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter)
    {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
        return container;
    }

    /*
     * Because the Receiver class is a POJO, it needs to be wrapped in a message
     * listener adapter that implements the MessageListener interface required
     * by addMessageListener()
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver)
    {
        /*
         * The message listener adapter is also configured to call the
         * receiveMessage() method on Receiver when a message arrives
         */
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /*
     * prepare the POJO class based message receiver
     */
    @Bean
    Receiver receiver(CountDownLatch latch)
    {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch()
    {
        return new CountDownLatch(1);
    }

    /*
     * To send a message you also need a Redis template. Here, it is a bean
     * configured as a StringRedisTemplate, an implementation of RedisTemplate
     * that is focused on the common use of Redis where both keys and values are
     * `String`s
     */
    @Bean
    StringRedisTemplate template(JedisConnectionFactory connectionFactory)
    {
        return new StringRedisTemplate(connectionFactory);
    }
}
