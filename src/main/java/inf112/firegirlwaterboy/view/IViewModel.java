package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.firegirlwaterboy.model.GameState;

/**
 * Interface that represents the view model of the game.
 * It includes methods for retrieving the current game state, updating the game
 * state, drawing the game and disposing of the game.
 */
public interface IViewModel {

  /**
   * Retrieves the current game state.
   *
   * @return The current state of the game, represented by a Gamestate enum value.
   */
  GameState getGameState();

  /**
   * Updates the game logic based on the given time elapsed since the last update.
   *
   * @param deltaTime The time elapsed since the last update (in seconds)
   */
  void update(float deltaTime);

  /**
   * Renders the game using the given batch.
   * 
   * @param batch The batch used for rendering the game
   */
  void draw(Batch batch);

  /**
   * Disposes of the game.
   */
  void dispose();

  /**
   * Retrieves the current map of the game.
   * 
   * @return The active map of the game
   */
  TiledMap getMap();

  /**
   * Initializes the game state, maps, and entities.
   */
  void init();

}
