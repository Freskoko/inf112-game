package inf112.firegirlwaterboy.model.managers;

import java.util.Iterator;

import inf112.firegirlwaterboy.model.entity.Collectable;

public class CollectableSet extends EntitySet<Collectable> {

  @Override
  public void update() {
    Iterator<Collectable> itr = entities.iterator();
    while (itr.hasNext()) {
      Collectable collectable = itr.next();
      if (collectable.isCollected()) {
        itr.remove();
      }
      collectable.update();
    }

  }

}
