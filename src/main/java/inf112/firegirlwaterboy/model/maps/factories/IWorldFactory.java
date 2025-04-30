package inf112.firegirlwaterboy.model.maps.factories;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.managers.CollectableSet;
import inf112.firegirlwaterboy.model.managers.EntitySet;

/**
 * Interface for creating and populating a Box2D world from a Tiled map.
 */
public interface IWorldFactory {

  /**
   * Creates a new Box2D {@link World} and populates it with objects based on the
   * given Tiled map.
   *
   * @param map The {@link TiledMap} used to generate world objects.
   * @return A fully initialized {@link World} with objects added.
   */
  World createWorld(TiledMap map);

  /**
   * Creates all platform objects from the PLATFORM layer in the map.
   *
   * @param world The Box2D world where platforms will be added.
   * @param map   The Tiled map containing platform data.
   * @return An {@link EntitySet} containing all created platforms.
   */
  EntitySet<Platform> createPlatforms(World world, TiledMap map);

  /**
   * Creates all element objects from the ELEMENT layer in the map.
   *
   * @param world The Box2D world where elements will be added.
   * @param map   The Tiled map containing element data.
   * @return An {@link EntitySet} containing all created elements.
   */
  EntitySet<Element> createElements(World world, TiledMap map);

  /**
   * Creates all collectable objects from the COLLECTABLE layer in the map.
   *
   * @param world The Box2D world where collectables will be added.
   * @param map   The Tiled map containing collectable data.
   * @return A {@link CollectableSet} containing all created collectables.
   */
  CollectableSet createCollectables(World world, TiledMap map);
}