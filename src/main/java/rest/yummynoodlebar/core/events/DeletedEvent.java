package rest.yummynoodlebar.core.events;

public class DeletedEvent {
  protected boolean entityFound = true;

  public boolean isEntityFound() {
    return entityFound;
  }
}
