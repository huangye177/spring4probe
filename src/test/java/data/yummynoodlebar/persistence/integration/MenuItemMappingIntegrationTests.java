package data.yummynoodlebar.persistence.integration;

import static data.yummynoodlebar.persistence.domain.fixture.MongoAssertions.usingMongo;
import static data.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.standardItem;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {CoreConfig.class, MVCConfig.class})
public class MenuItemMappingIntegrationTests
{

    MongoOperations mongo;

    @Before
    public void setup() throws Exception
    {
        mongo = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "yummynoodle"));

        mongo.dropCollection("menu");
    }

    @After
    public void teardown()
    {
        mongo.dropCollection("menu");
    }

    @Test
    public void thatItemIsInsertedIntoCollectionHasCorrectIndexes() throws Exception
    {

        mongo.insert(standardItem());

        assertEquals(1, mongo.getCollection("menu").count());

        assertTrue(usingMongo(mongo).collection("menu").hasIndexOn("_id"));
        assertTrue(usingMongo(mongo).collection("menu").hasIndexOn("itemName"));
    }

    @Test
    public void thatItemCustomMappingWorks() throws Exception
    {
        mongo.insert(standardItem());

        assertTrue(usingMongo(mongo).collection("menu").first().hasField("itemName"));
    }
}
