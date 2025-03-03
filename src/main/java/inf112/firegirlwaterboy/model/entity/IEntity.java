package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Interface that represents an entity in the game.
 * 
 * Defines the basic methods that an entity must have, including updating,
 * rendering and getting the texture of the entity.
 */
public interface IEntity {

  /**
   * Updates the entity´s state based on the given deltaTime.
   * 
   * @param deltaTime The time elapsed since the last update (in seconds)
   */
  void update(float deltaTime);

  /**
   * Renders the entity using the given batch.
   * 
   * @param batch The batch used for rendering the entity
   */
  void draw(Batch batch);

  /** @return The texture of the entity */
  Texture getTexture();

}
