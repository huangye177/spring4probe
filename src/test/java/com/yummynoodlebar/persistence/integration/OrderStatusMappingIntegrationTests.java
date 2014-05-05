package com.yummynoodlebar.persistence.integration;

import static junit.framework.TestCase.assertEquals;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.gemfire.GemfireCallback;
import org.springframework.data.gemfire.GemfireOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gemstone.gemfire.GemFireCheckedException;
import com.gemstone.gemfire.GemFireException;
import com.gemstone.gemfire.cache.Region;
import com.yummynoodlebar.config.GemfireConfiguration;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GemfireConfiguration.class })
public class OrderStatusMappingIntegrationTests
{
    /*
     * GemFireTemplate implements interface GemFireOperations, and exposes a
     * Map-oriented method to interact with its configured region
     */
    @Resource(name = "yummyTemplate")
    GemfireOperations yummyTemplate;

    @Before
    public void setup()
    {
        clear();
    }

    @After
    public void teardown()
    {
        clear();
    }

    public void clear()
    {
        yummyTemplate.execute(new GemfireCallback<Object>()
        {
            @Override
            public Object doInGemfire(Region<?, ?> region) throws GemFireCheckedException, GemFireException
            {
                /*
                 * accesses the Region instance and clears it of all data.
                 * Region implements the Map interface as it is also
                 * conceptually a Map
                 */
                region.clear();
                return "completed";
            }
        });
    }

    @Test
    public void thatItemCustomMappingWorks() throws Exception
    {
        OrderStatus status = PersistenceFixture.startedCooking(UUID.randomUUID());

        yummyTemplate.put(4L, status);

        /*
         * performs a query, using the GemFire Object Query Language (OQL)
         * 
         * GemFire OQL is a declarative language similar to the JPA Query
         * Language and Hibernate Query Languages and provides a syntax to query
         * against a set of Objects and their properties and perform selections,
         * ordering, grouping and projections against the results
         */
        OrderStatus results = yummyTemplate.findUnique("SELECT * from /YummyNoodleOrder");

        System.out.println("Found " + results.getId());

        assertEquals(status.getId(), results.getId());
        assertEquals(status.getOrderId(), results.getOrderId());
        assertEquals(status.getStatus(), results.getStatus());
    }
}
