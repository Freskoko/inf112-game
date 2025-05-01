package inf112.firegirlwaterboy.model.entity.managers;

import inf112.firegirlwaterboy.model.entity.Player;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * Interface for a set of players.
 * This interface defines the methods that can be used to interact with a set of players.
 */
public interface IPlayerSet extends IEntitySet<Player> {

  /**
   * Returns the total score collected by all players.
   *
   * @return The total score
   */
  public int getScore();

  /**
   * Checks if all players have finished the level.
   *
   * @return True if all players are finished, false otherwise
   */
  boolean areFinished();

  /**
   * Checks if the playerSet contains a player of the given type.
   *
   * @param playerType The type of player to check
   * @return True playerSer contains playerType, false otherwise
   */
  boolean contains(PlayerType playerType);

  /**
   * Returns the player of the given type.
   *
   * @param playerType The type of player to get
   * @return The player of the given type
   * @throws IllegalArgumentException if the playerSet does not contain a player
   *                                  of the given type
   */
  Player getPlayer(PlayerType playerType);

  /**
   * Checks if all players are alive.
   *
   * @return True if all players are alive, false otherwise
   */
  boolean areAlive();
}