package inf112.firegirlwaterboy.controller;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.Entity.PlayerType;

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
     * @param deltaRow
     * @param deltaCol
     * @return return true if move was done, if not false
     */
    boolean move(PlayerType player, int deltaRow, int deltaCol);


     /**
     * @return time between each tick
     */
    int getTimerDelay();

    
  
}
