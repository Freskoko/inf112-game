package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

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

  /**
   * Creates the body of the entity in the given world.
   * 
   * @param world The world in which the entity will be created
   * @param pos The position of the entity in the world
   */
  void createBody(World world, Vector2 pos);

}
