package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface that represents an entity in the game.
 * 
 * Defines the basic methods that an entity must have, including updating,
 * rendering and getting the texture of the entity.
 */
public interface IEntity {

  /**
   * Updates the entity´s state
   */
  void update();

  /**
   * Renders the entity using the given batch.
   * 
   * @param batch The batch used for rendering the entity
   */
  void draw(Batch batch);

  /** @return The texture of the entity */
  Texture getTexture();

  Body getBody();

}
