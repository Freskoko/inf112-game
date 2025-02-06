package inf112.firegirlwaterboy.view;

import java.util.ArrayList;

import inf112.firegirlwaterboy.model.GameState;
import inf112.grid.GridCell;
import inf112.grid.GridDimension;

public interface IViewModel {

    /**
   * Retrieves the current game state.
   *
   * @return The current state of the game, represented by a Gamestate enum value.
   */
  GameState getGameState();

   /**
   * Retrieves an iterable containing all cells of the game board.
   *
   * @return An iterable containing all cells of the game board.
   */
  Iterable<GridCell<Character>> getBoardTiles();

  /**
   * Retrieves the grid dimension of the game board.
   *
   * @return The grid dimension of the game board.
   */
  GridDimension getDimension();

  
}
