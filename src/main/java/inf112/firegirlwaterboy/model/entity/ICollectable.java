package inf112.firegirlwaterboy.model.entity;

import java.util.Set;

import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * Interface for collectable items in the game.
 * Collectable items can be collected by players and have a set of required player types.
 */
public interface ICollectable {

  /**
   * Returns the PlayerTypes that can collect this item.
   * 
   * @return The set of PlayerTypes that can to collect this item.
   */
  Set<PlayerType> getRequiredPlayers();

  /**
   * Checks if the item has been collected.
   * 
   * @return True if the item has been collected, false otherwise.
   */
  boolean isCollected();

  /**
   * Sets the item as collected.
   */
  void collect();
}