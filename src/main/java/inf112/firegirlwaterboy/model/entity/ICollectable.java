package inf112.firegirlwaterboy.model.entity;

import java.util.Set;

import inf112.firegirlwaterboy.model.types.PlayerType;

public interface ICollectable {

  /**
   * @return The set of PlayerTypes that can to collect this item.
   */
  Set<PlayerType> getRequiredPlayers();

  /**
   * @return true if the item has been collected, false otherwise.
   */
  boolean isCollected();
}
