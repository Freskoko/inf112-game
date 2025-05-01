package inf112.firegirlwaterboy.model.entity.managers;

import com.badlogic.gdx.graphics.g2d.Batch;

import inf112.firegirlwaterboy.model.entity.IEntity;

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
   * @return true if this entitySet is empty, false otherwise
   */
  boolean isEmpty();

  /**
   *
   * @param entity The entity to be checked
   * @return true if entitySet contains enitity, false otherwise
   */
  boolean contains(E entity);

  /**
   * Updates all entities in the set. Used for updating entity state.
   */
  void update();
}
