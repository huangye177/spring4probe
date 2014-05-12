package io.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSApp {
	
	public static void main(String[] args) {

        // Launch the application
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // invoke the CONFIG
        /*
         * application context then starts the message listener container, and
         * the message listener container bean starts listening for messages
         */
        ctx.register(JMSConfig.class);
        ctx.refresh();

        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("* ping!");
            }
        };
        
        
        JmsTemplate jmsTemplate = ctx.getBean(JmsTemplate.class);
        ActiveMQQueue queue = ctx.getBean(ActiveMQQueue.class);
        
        System.out.println("* Sending a new message.");
        jmsTemplate.send(queue, messageCreator);
        System.out.println("* Message sent.");
        
    }
}
