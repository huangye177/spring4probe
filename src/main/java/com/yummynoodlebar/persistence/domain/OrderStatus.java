package com.yummynoodlebar.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import com.yummynoodlebar.events.orders.OrderStatusDetails;

/*
 * configuring the default Region and specifying the property to use as the Region key/ID
 */
@Region("YummyNoodleOrder")
public class OrderStatus implements Serializable
{

    private UUID orderId;

    @Id
    private UUID id;

    private Date statusDate;
    private String status;

    public OrderStatus(UUID orderId, UUID id, final Date date, final String status)
    {
        this.orderId = orderId;
        this.id = id;
        this.status = status;
        this.statusDate = date;
    }

    public Date getStatusDate()
    {
        return statusDate;
    }

    public String getStatus()
    {
        return status;
    }

    public UUID getOrderId()
    {
        return orderId;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public OrderStatusDetails toStatusDetails()
    {
        return new OrderStatusDetails(orderId, id, statusDate, status);
    }

    public static OrderStatus fromStatusDetails(OrderStatusDetails orderStatusDetails)
    {
        return new OrderStatus(
                orderStatusDetails.getOrderId(), orderStatusDetails.getId(),
                orderStatusDetails.getStatusDate(), orderStatusDetails.getStatus());
    }
}
