package io.spring.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RabbitMQReceiver {

	@Autowired
	AnnotationConfigApplicationContext context;

	public void receiveMessage(String message) {
        System.out.println("* Received <" + message + ">");
        this.context.close();
   }
}
