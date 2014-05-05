package com.yummynoodlebar.persistence.integration.fakecore;

import java.util.concurrent.CountDownLatch;

import com.yummynoodlebar.core.services.OrderStatusUpdateService;
import com.yummynoodlebar.events.orders.OrderStatusEvent;
import com.yummynoodlebar.events.orders.SetOrderStatusEvent;

/**
 * A testing Stub. Stands in the place of the Core OrderStatusUpdateService.
 * Allows tests to count the number of update status events via a
 * CountDownLatch, which will be set manually in the test
 */
public class CountingOrderStatusService implements OrderStatusUpdateService
{
    /*
     * This stub will receive events and count them off against a CountDownLatch
     * to ensure that the correct number of events are received in the given
     * time. When all expected threads submit their countdown, the latch
     * proceeds to completion
     */

    private CountDownLatch latch;

    public void setLatch(CountDownLatch latch)
    {
        this.latch = latch;
    }

    /*
     * Continuous Querying allows you to register a GemFire Query with the
     * cluster and then for a simple POJO to receive events whenever a new piece
     * of data is added that matches your query
     * 
     * in this test, whenever an OrderStatus instance is saved into GemFire, the
     * method OrderStatusUpdateService.setOrderStatus() needs to be called with
     * an appropriate event.
     */
    @Override
    public OrderStatusEvent setOrderStatus(SetOrderStatusEvent orderStatusEvent)
    {
        latch.countDown();
        return OrderStatusEvent.notFound(orderStatusEvent.getKey());
    }
}
