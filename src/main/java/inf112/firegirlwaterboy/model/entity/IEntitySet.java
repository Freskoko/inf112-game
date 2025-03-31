package inf112.firegirlwaterboy.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;


public interface IEntitySet<E extends IEntity, T> {

  /**
   * Add antity to collection
   * 
   * @param entity The entity to be added
   * @throws IllegalArgumentException if player already exists
   */
  void addEntity(E entity);

  /**
   * Retrieve entity from the set based on playerType if entityList is a
   * collection of Players
   * 
   * @param playerType The key representing the entity type
   * @return player The entity associated with the given type
   */
  E getEntity(PlayerType playerType);

  /**
   * Checks if a entity is in the entityset
   *
   * @param entityType The key representing the entity type
   * @return true if the entity is in the set, false otherwise
   */
  boolean containsEntity(T entityType);

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

  boolean isEmpty();



  
}
