package inf112.firegirlwaterboy.model.entity.managers;

import com.badlogic.gdx.graphics.g2d.Batch;

import inf112.firegirlwaterboy.model.entity.IEntity;

/**
 * Interface that defines a set of entities.
 * 
 * @param <E> Entity
 */
public interface IEntitySet<E extends IEntity<?>> {

  /**
   * Add entity to collection
   *
   * @param entity The entity to be added
   * @throws IllegalArgumentException if player already exists
   */
  void add(E entity);

  /**
   * Draws all entities contained in the set using the given batch
   *
   * @param batch The batch used for rendering
   */
  void draw(Batch batch);

  /**
   * Disposes of all textures used by the entities in the set.
   */
  void dispose();

  /**
   * Checks if the entitySet is empty
   * 
   * @return True if this entitySet is empty, false otherwise
   */
  boolean isEmpty();

  /**
   * Checks if the entitySet contains the given entity
   * 
   * @param entity The entity to be checked
   * @return True if entitySet contains enitity, false otherwise
   */
  boolean contains(E entity);

  /**
   * Updates all entities in the set. Used for updating entity state.
   */
  void update();
}