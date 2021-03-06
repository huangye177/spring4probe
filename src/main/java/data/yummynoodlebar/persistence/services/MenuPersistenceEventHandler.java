package data.yummynoodlebar.persistence.services;

import data.yummynoodlebar.events.menu.*;
import data.yummynoodlebar.persistence.domain.MenuItem;
import data.yummynoodlebar.persistence.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

public class MenuPersistenceEventHandler implements MenuPersistenceService {

  private MenuItemRepository menuItemRepository;

  public MenuPersistenceEventHandler(MenuItemRepository menuItemRepository) {
    this.menuItemRepository = menuItemRepository;
  }

  @Override
  public AllMenuItemsEvent requestAllMenuItems(RequestAllMenuItemsEvent requestAllMenuItemsEvent) {
    Iterable<MenuItem> menuItems = menuItemRepository.findAll();

    List<MenuItemDetails> details = new ArrayList<MenuItemDetails>();

    for(MenuItem item: menuItems) {
      details.add(item.toStatusDetails());
    }

    return new AllMenuItemsEvent(details);
  }

  @Override
  public MenuItemDetailsEvent requestMenuItemDetails(RequestMenuItemDetailsEvent requestMenuItemDetailsEvent) {
    MenuItem item = menuItemRepository.findOne(requestMenuItemDetailsEvent.getId());

    if (item == null) {
      return MenuItemDetailsEvent.notFound(requestMenuItemDetailsEvent.getId());
    }

    return new MenuItemDetailsEvent(
        requestMenuItemDetailsEvent.getId(),
        item.toStatusDetails());
  }

  @Override
  public MenuItemDetailsEvent createMenuItem(CreateMenuItemEvent createMenuItemEvent) {
    MenuItem item = menuItemRepository.save(
        MenuItem.fromStatusDetails(createMenuItemEvent.getDetails()));

    return new MenuItemDetailsEvent(
        item.getId(),
        item.toStatusDetails());
  }
}
