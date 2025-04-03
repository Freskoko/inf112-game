package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IEntitySet<E extends IEntity> {

  /**
   * Add antity to collection
   * 
   * @param entity The entity to be added
   * @throws IllegalArgumentException if player already exists
   */
  void add(E entity);

  /**
   * Draws all players contained in the list using the given batch
   * 
   * @param batch The batch used for rendering
   */
  void draw(Batch batch);

  /**
   * Disposes of all textures used by the players
   */
  void dispose();

  /**
   * @return true if this entutySet contains no entities
   */
  boolean isEmpty();

  /**
   * 
   * @param entity
   * @return true if entitySet contains enitity
   */
  boolean contains(E entity);
}
