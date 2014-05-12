package io.spring.jms;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/*
 * JmsTemplate can receive messages directly through its receive method, 
 * but that only works synchronously, meaning it will block. 
 * 
 * That’s why Spring recommends that you use a listener container 
 * such as SimpleMessageListenerContainer with a cache-based connection factory, 
 * so you can consume messages asynchronously and with maximum connection efficiency
 */
@Configuration
public class JMSConfig {
	static String mailboxDestination = "mailbox-destination";

    @Bean
    JMSReceiver receiver() {
        return new JMSReceiver();
    }

    /*
     * configure which method to invoke when a message comes in. Thus you avoid implementing any JMS or broker-specific interfaces
     */
    @Bean
    MessageListenerAdapter adapter(JMSReceiver receiver) {
        MessageListenerAdapter messageListener
                = new MessageListenerAdapter(receiver);
        messageListener.setDefaultListenerMethod("receiveMessage");
        return messageListener;
    }

    /*
     * SimpleMessageListenerContainer class is an asynchronous message receiver. 
     * 
     * It uses the MessageListenerAdapter and the ConnectionFactory and is fired up when the application context starts. 
     * 
     * Another parameter is the queue name set in mailboxDestination. It is also set up to receive messages in a publish/subscribe fashion
     */
    @Bean
    SimpleMessageListenerContainer container(MessageListenerAdapter messageListener,
                                             ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(mailboxDestination);
        container.setPubSubDomain(true);
        return container;
    }
    
    @Bean
    ActiveMQConnectionFactory amqConnectionFactory()
    {
        return new ActiveMQConnectionFactory("failover:(tcp://localhost:61616)?timeout=1000");
    }
    
//    @Bean
//    CachingConnectionFactory connectionFactory()
//    {
//        return new CachingConnectionFactory();
//    }
    
    /*
     * JmsTemplate makes it very simple to send messages to a JMS message queue
     */
    @Bean
    JmsTemplate template(ConnectionFactory connectionFactory) {
    	return new JmsTemplate(connectionFactory);
    }
}
