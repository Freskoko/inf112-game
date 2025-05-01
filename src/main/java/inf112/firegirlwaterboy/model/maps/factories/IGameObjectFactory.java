package inf112.firegirlwaterboy.model.maps.factories;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;

/**
 * Interface for creating game objects such as platforms, elements, and collectables.
 * Create game objects based on the map representation.
 */
public interface IGameObjectFactory {

  /**
   * Creates a new plaform based on object in the world
   *
   * @param world  The game world
   * @param object The representation of the platform
   * @return A plaform object
   */
  Platform createPlatform(World world, MapObject object);

  /**
   * Creates a new element based on object in the world
   *
   * @param world  The game world
   * @param object The representation of the element
   * @return An element object
   */
  Element createElement(World world, MapObject object);

  /**
   * Creates a new collectable based on object in the world
   *
   * @param world  The game world
   * @param object The representation of the collectable
   * @return A collectable object
   */
  Collectable createCollectable(World world, MapObject object);
}