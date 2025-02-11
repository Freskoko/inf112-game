package inf112.firegirlwaterboy.view;

import inf112.firegirlwaterboy.model.GameState;
public interface IViewModel {

    /**
   * Retrieves the current game state.
   *
   * @return The current state of the game, represented by a Gamestate enum value.
   */
  GameState getGameState();

  
}
