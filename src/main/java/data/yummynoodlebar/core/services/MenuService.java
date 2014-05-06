package data.yummynoodlebar.core.services;

import data.yummynoodlebar.events.menu.*;

public interface MenuService {
  AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent);
  MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent);
  MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent);
}
