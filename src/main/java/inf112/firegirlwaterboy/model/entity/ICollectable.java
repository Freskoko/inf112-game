package inf112.firegirlwaterboy.model.entity;

import inf112.firegirlwaterboy.model.types.PlayerType;

public interface ICollectable {

  /**
   * @return The PlayerType that is required to collect this item.
   */
  PlayerType getRequiredPlayer();

  /**
   * Collects the item by destroying its physics body and removing its user data.
   */
  void collect();
}
