package io.spring.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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
	
	static String queueName = "myQueue";

	@Autowired
	JmsTemplate jmsTemplate;
	
    @Bean
    JMSReceiver receiver() {
        return new JMSReceiver();
    }

    @Bean
    ActiveMQQueue queue() {
    	return new ActiveMQQueue(queueName);
    }
    
    /*
     * configure which method to invoke when a message comes in. 
     * Thus you avoid implementing any JMS or broker-specific interfaces
     */
    @Bean
    MessageListenerAdapter adapter(JMSReceiver receiver) {
    	
        MessageListenerAdapter messageListener = new MessageListenerAdapter(receiver);
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
    SimpleMessageListenerContainer container(MessageListenerAdapter messageListener, ConnectionFactory connectionFactory, ActiveMQQueue queue) {
    	
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(messageListener);
        container.setConnectionFactory(connectionFactory);
        container.setDestination(queue);
        container.setPubSubDomain(true);
        
        return container;
    }
    
    @Bean
    ActiveMQConnectionFactory amqConnectionFactory()
    {
    	return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }
    
    /*
     * JmsTemplate makes it very simple to send messages to a JMS message queue
     */
    @Bean
    JmsTemplate template(ConnectionFactory connectionFactory, ActiveMQQueue queue) {
    	JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    	jmsTemplate.setDefaultDestination(queue);
    	return jmsTemplate;
    }
}
