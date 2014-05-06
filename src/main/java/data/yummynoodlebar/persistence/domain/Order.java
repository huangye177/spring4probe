package data.yummynoodlebar.persistence.domain;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Transient;

import data.yummynoodlebar.events.orders.OrderDetails;

@Entity(name = "NOODLE_ORDERS")
public class Order
{

    @Column(name = "SUBMISSION_DATETIME")
    private Date dateTimeOfSubmission;

    /*
     * This mapping is used to create a joined table, ORDER_ORDER_ITEMS, that
     * contains the data stored in the java.util.Map.
     */
    @ElementCollection(fetch = FetchType.EAGER, targetClass = java.lang.Integer.class)
    @JoinTable(name = "ORDER_ORDER_ITEMS", joinColumns = @JoinColumn(name = "ID"))
    @MapKeyColumn(name = "MENU_ID")
    @Column(name = "VALUE")
    private Map<String, Integer> orderItems;

    /*
     * informs JPA that the given field should not be stored in the database,
     * and is analogous to the Java transient keyword
     */
    @Transient
    private OrderStatus orderStatus;

    /*
     * indicates to both JPA and Spring Data that the given field(s) is the
     * Primary Key, and should be used to both index and provide the default
     * access method for the Entity
     */
    @Id
    @Column(name = "ORDER_ID")
    private String id;

    public void setId(String id)
    {
        this.id = id;
    }

    public void setDateTimeOfSubmission(Date dateTimeOfSubmission)
    {
        this.dateTimeOfSubmission = dateTimeOfSubmission;
    }

    public OrderStatus getStatus()
    {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Date getDateTimeOfSubmission()
    {
        return dateTimeOfSubmission;
    }

    public String getId()
    {
        return id;
    }

    public void setOrderItems(Map<String, Integer> orderItems)
    {
        if (orderItems == null)
        {
            this.orderItems = Collections.emptyMap();
        }
        else
        {
            this.orderItems = Collections.unmodifiableMap(orderItems);
        }
    }

    public Map<String, Integer> getOrderItems()
    {
        return orderItems;
    }

    public OrderDetails toOrderDetails()
    {
        OrderDetails details = new OrderDetails();

        details.setKey(UUID.fromString(this.id));
        details.setDateTimeOfSubmission(this.dateTimeOfSubmission);
        details.setOrderItems(this.getOrderItems());

        return details;
    }

    public static Order fromOrderDetails(OrderDetails orderDetails)
    {
        Order order = new Order();

        order.id = orderDetails.getKey().toString();
        order.dateTimeOfSubmission = orderDetails.getDateTimeOfSubmission();
        order.orderItems = orderDetails.getOrderItems();

        return order;
    }
}
