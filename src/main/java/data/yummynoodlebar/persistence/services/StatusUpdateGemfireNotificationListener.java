package data.yummynoodlebar.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.gemstone.gemfire.cache.query.CqEvent;

import data.yummynoodlebar.core.services.OrderStatusUpdateService;
import data.yummynoodlebar.events.orders.SetOrderStatusEvent;
import data.yummynoodlebar.persistence.domain.OrderStatus;

/*
 * A new Continuous Query with the given query being continuously evaluated is defined in client.xml;
 * 
 * Matching data is passed to this bean named statusUpdateListener that declared in the GemfireConfiguration, namely this implementation class
 */
public class StatusUpdateGemfireNotificationListener
{

    @Autowired
    private OrderStatusUpdateService orderStatusUpdateService;

    public void setOrderStatusUpdateService(OrderStatusUpdateService orderStatusUpdateService)
    {
        this.orderStatusUpdateService = orderStatusUpdateService;
    }

    public void handleEvent(CqEvent event)
    {

        if (!event.getBaseOperation().isCreate())
        {
            return;
        }

        /*
         * ransforms the GemFire CqEvent into a SetOrderStatusEvent to be
         * consumed by the Core domain, and gains a reference to
         * OrderStatusUpdateService
         */
        OrderStatus status = (OrderStatus) event.getNewValue();

        orderStatusUpdateService.setOrderStatus(
                new SetOrderStatusEvent(status.getOrderId(),
                        status.toStatusDetails()));

    }
}
