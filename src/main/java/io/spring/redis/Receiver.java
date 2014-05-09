package io.spring.redis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Redis not only provides a NoSQL data store, but a messaging system as well
 */
public class Receiver
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    /*
     * a simple POJO that defines a method for receiving messages. The Receiver
     * will be registered as a message listener
     * 
     * it is autowired by its constructor with a countdown latch. That way, it
     * can signal when it has received a message.
     */
    @Autowired
    public Receiver(CountDownLatch latch)
    {
        this.latch = latch;
    }

    public void receiveMessage(String message)
    {
        LOGGER.info("* Redis message on given topic(chat) received <" + message + ">");
        latch.countDown();
    }
}