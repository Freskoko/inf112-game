package inf112.firegirlwaterboy.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import inf112.firegirlwaterboy.model.GameState;
public interface IViewModel {

    /**
   * Retrieves the current game state.
   *
   * @return The current state of the game, represented by a Gamestate enum value.
   */
  GameState getGameState();

  void update(float deltaTime);

  void draw(Batch batch);

  void dispose();

  TiledMap getMap();

  void init();

  
}
