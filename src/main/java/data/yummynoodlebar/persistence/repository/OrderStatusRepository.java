package data.yummynoodlebar.persistence.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

import data.yummynoodlebar.persistence.domain.OrderStatus;

/*
 * The entity/persistence class, in this case OrderStatus, 
 * requires annotating to control how it is persisted into the data store
 */
public interface OrderStatusRepository extends GemfireRepository<OrderStatus, UUID>
{

    @Query("SELECT DISTINCT * FROM /YummyNoodleOrder WHERE orderId = $1 ORDER BY statusDate")
    public Collection<OrderStatus> getOrderHistory(UUID orderId);
}
