package inf112.firegirlwaterboy.model.entity;

import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * EntitySet is a class that holds a collection of entities.
 * It is used to draw and dispose of entities.
 * 
 * @param <E> Entity
 */
public class EntitySet<E extends IEntity> implements Iterable<E>, IEntitySet<E> {

  public static final int maxPlayers = 2;
  private HashSet<E> entities;

  public EntitySet() {
    entities = new HashSet<>();
  }

  @Override
  public void addEntity(E entity) {
    if (entity instanceof Player) {
      if (entities.size() >= EntitySet.maxPlayers) {
        System.out.println("Max players reached!");
        return;
      } else {
        if (entities.contains(entity)) {
          throw new IllegalArgumentException("Player of type:" + entity + "already exists");
        }
      }

    }
    entities.add(entity);
  }

  
  public E getEntity(PlayerType playerType) {
    for (E entity : entities) {
      if (entity instanceof Player player && player.getPlayerType() == playerType) {
        return entity;
      }
    }
    throw new IllegalArgumentException("EntitySet does not contain a Player of type: " + playerType);
  }

  



  @Override
  public void draw(Batch batch) {
    for (E entity : entities) {
      entity.draw(batch);
    }
  }

  /**
   * Disposes of all textures used by the players
   */
  public void dispose() {
    for (E entity : entities) {
      entity.getTexture().dispose();
    }
  }

  @Override
  public Iterator<E> iterator() {
    return entities.iterator();
  }

  @Override
  public boolean isEmpty() {
    return entities.isEmpty();
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (E entities : entities) {
      sb.append(entities.toString()).append(", ");
    }

    if (sb.length() > 0) {
      sb.setLength(sb.length() - 2);
    }

    return sb.toString();
  }
}
