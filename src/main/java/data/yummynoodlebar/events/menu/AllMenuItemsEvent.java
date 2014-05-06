package data.yummynoodlebar.events.menu;


import data.yummynoodlebar.events.ReadEvent;

import java.util.List;

public class AllMenuItemsEvent extends ReadEvent {
  private List<MenuItemDetails> menuItemDetails;

  public AllMenuItemsEvent(List<MenuItemDetails> menuItemDetails) {
    this.menuItemDetails = menuItemDetails;
  }

  public List<MenuItemDetails> getMenuItemDetails() {
    return menuItemDetails;
  }
}
