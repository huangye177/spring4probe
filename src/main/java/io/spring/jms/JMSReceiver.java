package io.spring.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

public class JMSReceiver {

	/**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     */
    public void receiveMessage(String message) {
        System.out.println("* Received <" + message + ">");
        context.close();
    }
}
