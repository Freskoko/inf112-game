package inf112.firegirlwaterboy.controller;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;
import inf112.firegirlwaterboy.sound.ISoundManager;

/**
 * Interface for the controllable model.
 * This interface defines the methods that the controller can use to interact
 * with the model.
 */
public interface IControllableModel {

  /**
   * @return current Game State
   */
  GameState getGameState();

  /**
   * Set gamestate of model to given gamestate
   *
   * @param gameState
   *
   */
  void setGameState(GameState gameState);

  /**
   * @param player to move
   * @param dirX
   * @return return true if move was done, if not false
   */
  boolean changeDir(PlayerType player, MovementType dir);

  /**
   * Add player to the game
   *
   * @param playerType
   */
  void addPlayer(PlayerType playerType);

  /**
   * Restart the game
   */
  void restartGame();

  /**
   * Set the map to the given map name
   *
   * @param mapName the name of the map to set
   */
  void setMap(String mapName);

  /**
   * Checks whether the specified map is marked as completed.
   *
   * @param mapName the name of the map to check
   * @return {@code true} if the map has been completed; {@code false} otherwise
   */
  boolean isComplete(String string);

  /**
   * Sets the sound manager for the model.
   *
   * @param soundManager the sound manager to set
   */
  public void setSoundManager(ISoundManager soundManager);

}
