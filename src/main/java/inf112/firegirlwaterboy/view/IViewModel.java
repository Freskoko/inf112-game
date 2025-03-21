package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

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
   * Updates the game logic
   */
  void update();

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

  /**
   * Returns the current physics world associated with this game.
   * 
   * @return the physics world used by this game instance
   */
  World getWorld();

}
