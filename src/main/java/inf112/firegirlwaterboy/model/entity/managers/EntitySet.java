package inf112.firegirlwaterboy.model.entity.managers;

import java.util.HashSet;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;

import inf112.firegirlwaterboy.model.entity.IEntity;

/**
 * EntitySet is a class that holds a collection of entities.
 * It is used to draw and dispose of entities.
 *
 * @param <E> Entity
 */
public class EntitySet<E extends IEntity<?>> implements Iterable<E>, IEntitySet<E> {

  protected HashSet<E> entities = new HashSet<>();

  @Override
  public void add(E entity) {
    if (!entities.add(entity)) {
      throw new IllegalArgumentException("Entity of type: " + entity.getType() + " already exists");
    }
  }

  @Override
  public void draw(Batch batch) {
    entities.forEach(entity -> entity.draw(batch));
  }

  @Override
  public void dispose() {
    entities.forEach(entity -> entity.dispose());
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
    return entities.toString().substring(1, entities.toString().length() - 1);
  }

  @Override
  public boolean contains(E entity) {
    return entities.contains(entity);
  }

  @Override
  public void update() {
    entities.forEach(entity -> entity.update());
  }
}