package inf112.firegirlwaterboy.controller;

import inf112.firegirlwaterboy.model.GameState;
import inf112.firegirlwaterboy.model.entity.PlayerType;

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
     * @return return true if move was done, if not false
     */
    boolean changeDir(PlayerType player, MovementType dir);

    void removePlayer(PlayerType player);

    void addPlayer(PlayerType player);

    boolean containsPlayer(PlayerType playerType);

    String getPlayersAsString();
}
