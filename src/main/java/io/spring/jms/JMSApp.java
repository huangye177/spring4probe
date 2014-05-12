package io.spring.jms;

import java.io.File;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.FileSystemUtils;

public class JMSApp {
	
	static String mailboxDestination = "mailbox-destination";
	
	public static void main(String[] args) {
        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

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
        System.out.println("* Sending a new message.");
        jmsTemplate.send(mailboxDestination, messageCreator);
    }
}
