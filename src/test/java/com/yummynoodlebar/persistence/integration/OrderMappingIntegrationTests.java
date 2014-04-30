package com.yummynoodlebar.persistence.integration;

import static com.yummynoodlebar.persistence.domain.fixture.JPAAssertions.assertTableExists;
import static com.yummynoodlebar.persistence.domain.fixture.JPAAssertions.assertTableHasColumn;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yummynoodlebar.config.JPAConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JPAConfiguration.class })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrderMappingIntegrationTests
{

    /*
     * IMPORTANT
     * 
     * Argument and potential error (fixed by using @PersistenceContext) from
     * the tutorial
     * (http://stackoverflow.com/questions/22301426/no-qualifying-bean
     * -of-type-javax-persistence-entitymanager):
     * 
     * The EntityManager interface belongs to JPA and is implemented by JPA
     * providers, not Spring, and it has its own injection annotation:
     * 
     * @PersistenceContext.
     * 
     * EntityManager objects are transaction-scoped and should not be exposed as
     * beans
     */

    // @Autowired
    @PersistenceContext
    EntityManager manager;

    @Test
    public void thatItemCustomMappingWorks() throws Exception
    {
        assertTableExists(manager, "NOODLE_ORDERS");
        assertTableExists(manager, "ORDER_ORDER_ITEMS");

        assertTableHasColumn(manager, "NOODLE_ORDERS", "ORDER_ID");
        assertTableHasColumn(manager, "NOODLE_ORDERS", "SUBMISSION_DATETIME");
    }

}
