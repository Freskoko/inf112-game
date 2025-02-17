package inf112.firegirlwaterboy.controller;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Entity.PlayerType;

/**
 * Interface for the controllable model.
 * This interface defines the methods that the controller can use to interact with the model.
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
     * @param dirY
     * @return return true if move was done, if not false
     */
    boolean changeVelocity(PlayerType player, int dirX, int dirY);


     /**
     * @return time between each tick
     */
    int getTimerDelay();

    
  
}
