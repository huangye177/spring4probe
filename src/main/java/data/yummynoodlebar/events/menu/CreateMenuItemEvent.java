package data.yummynoodlebar.events.menu;


import data.yummynoodlebar.events.CreateEvent;

public class CreateMenuItemEvent extends CreateEvent {

  private MenuItemDetails details;

  public CreateMenuItemEvent(MenuItemDetails details) {
    this.details = details;
  }

  public MenuItemDetails getDetails() {
    return details;
  }
}
