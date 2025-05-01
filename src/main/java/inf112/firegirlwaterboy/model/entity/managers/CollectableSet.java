package inf112.firegirlwaterboy.model.entity.managers;

import inf112.firegirlwaterboy.model.entity.Collectable;

/**
 * A specialized set for managing Collectable entities in the game.
 **/
public class CollectableSet extends EntitySet<Collectable> {

  @Override
  public void update() {
    super.update();
    entities.removeIf(Collectable::isCollected);
  }
}
