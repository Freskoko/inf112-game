package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Interface that represents an entity in the game.
 *
 * Defines the basic methods that an entity must have, including updating,
 * rendering and getting the texture of the entity.
 */
public interface IEntity<T> {

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

  /**
   * dispose the texture of entity
   */
  void dispose();

  /**
   * @return the type of the entity
   */
  T getType();
}
