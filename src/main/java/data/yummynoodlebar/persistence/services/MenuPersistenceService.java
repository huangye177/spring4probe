package data.yummynoodlebar.persistence.services;

import data.yummynoodlebar.events.menu.*;

public interface MenuPersistenceService {

  AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent);
  MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent);
  MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent);

}
