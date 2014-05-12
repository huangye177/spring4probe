package io.spring.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/*
 * Spring AMQP’s RabbitTemplate provides everything you need to send and receive messages with RabbitMQ. Specifically, you need to configure:
 * 
 * A message listener container
 * Declare the queue, the exchange, and the binding between them
 */
@Configuration
public class RabbitMQConfig {
	
	final static String queueName = "spring-boot";

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	/*
	 *  JMS queues and AMQP queues have different semantics. For example, JMS sends queued messages to only one consumer. 
	 *  While AMQP queues do the same thing, AMQP producers don’t send messages directly to queues. 
	 *  Instead, a message is sent to an exchange, which can go to a single queue, or fanout to multiple queues, 
	 *  emulating the concept of JMS topics; 
	 *  
	 *  Spring AMQP requires that the Queue, the TopicExchange, 
	 *  and the Binding be declared as top level Spring beans in order to be set up properly.
	 */
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

    @Bean
    RabbitMQReceiver receiver() {
        return new RabbitMQReceiver();
    }

	@Bean
	MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	

	@Bean
	CachingConnectionFactory connectionFactory()
    {
        return new CachingConnectionFactory("127.0.0.1", 5672);
    }
	
	/* 
	 * NOTICE: RabbitAdmin is a missing bean from Spring-Guide, but needed in the latest Spring AMAQ version
	 */
	@Bean
	RabbitAdmin admin(CachingConnectionFactory connectionFactory, Queue queue, TopicExchange exchange, Binding binding) {
		
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		admin.declareQueue(queue);
		admin.declareExchange(exchange);
	    admin.declareBinding(binding);
	    
		return admin;
	}
	
	@Bean
	RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory)
    {
        return new RabbitTemplate(connectionFactory);
    }

}
