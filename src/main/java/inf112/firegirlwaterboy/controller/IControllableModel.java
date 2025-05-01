package inf112.firegirlwaterboy.controller;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.types.PlayerType;
import inf112.firegirlwaterboy.model.sound.ISoundManager;

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
   * @param gameState The gamestate to set
   */
  void setGameState(GameState gameState);

  /**
   * Change the direction of the player
   * 
   * @param player to move
   * @param dirX the x direction to move
   * @return return true if move was done, if not false
   */
  boolean changeDir(PlayerType player, MovementType dir);

  /**
   * Add player to the game
   * 
   * @param playerType The type of player to add
   */
  void addPlayer(PlayerType playerType);

  /**
   * Restart the game
   */
  void restartGame();

  /**
   * Set the map to the given map name
   * 
   * @param mapName The name of the map to set
   */
  void setMap(String mapName);

  /**
   * Checks whether the specified map is marked as completed.
   *
   * @param mapName The name of the map to check
   * @return true if the map has been completed, false otherwise
   */
  boolean isComplete(String string);

  /**
   * Sets the sound manager for the model.
   *
   * @param soundManager The sound manager to set
   */
  public void setSoundManager(ISoundManager soundManager);
}