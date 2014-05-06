package data.yummynoodlebar.persistence.integration;


import data.yummynoodlebar.config.MongoConfiguration;
import data.yummynoodlebar.persistence.domain.MenuItem;
import data.yummynoodlebar.persistence.repository.MenuItemRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static data.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.eggFriedRice;
import static data.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.standardItem;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfiguration.class})
public class MenuItemRepositoryFindByIngredientsIntegrationTests {

  @Autowired
  MenuItemRepository menuItemRepository;

  @Autowired
  MongoOperations mongo;

  @Before
  public void setup() throws Exception {
    mongo.dropCollection("menu");
  }

  @After
  public void teardown() {
    mongo.dropCollection("menu");
  }

  @Test
  public void thatItemIsInsertedIntoRepoWorks() throws Exception {

    menuItemRepository.save(standardItem());
    menuItemRepository.save(standardItem());
    menuItemRepository.save(eggFriedRice());

    List<MenuItem> peanutItems = menuItemRepository.findByIngredientsNameIn("Peanuts");

    assertEquals(2, peanutItems.size());
  }
}
