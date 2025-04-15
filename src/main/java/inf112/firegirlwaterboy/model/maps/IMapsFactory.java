package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.managers.CollectableSet;
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.entity.Element;
import inf112.firegirlwaterboy.model.entity.Platform;

public interface IMapsFactory {

  /**
   * Retrieves a {@link TiledMap} by its name.
   * If maps not initialized: initializes the all maps by loading all available
   * Tiled maps from recources
   * folder.
   *
   * @param mapName The name of the map to retrieve.
   * @return The corresponding {@link TiledMap}.
   * @throws IllegalArgumentException if the map does not exist.
   */
  TiledMap getMap(String mapName);

  /**
   * Retrieves a specific layer from the given map.
   *
   * @param mapName   The name of the map.
   * @param layerName The name of the layer.
   * @return The {@link MapLayer} if found.
   * @throws IllegalArgumentException if the layer does not exist.
   */
  MapLayer getLayer(String mapName, String layerName);

  /**
   * Creates physics objects in the given Box2D world from all object layers in
   * specified map, excluding the SPAWN layerType
   * 
   * @param world   The Box2D world to create objects in
   * @param mapName The name of the map with object layers
   */
  void createObjectsInWorld(World world, String mapName);

  /**
   * Retrieves the set of platforms associated with the specified map.
   *
   * @param mapName the name of the map for which platforms are requested
   * @return an {@code EntitySet} containing the platforms for the given map,
   *         or {@code null} if no platforms exist for the specified map
   */
  EntitySet<Platform> getPlatforms(String mapName);

  /**
   * Retrieves the set of collectables associated with the specified map.
   *
   * @param mapName the name of the map for which collectables are requested
   * @return an {@code CollectableSet} containing the collectables for the given
   *         map,
   *         or {@code null} if no collectables exist for the specified map
   */
  CollectableSet getCollectables(String mapName);

  /**
   * Retrieves the set of elements associated with the specified map.
   *
   * @param mapName the name of the map for which elements are requested
   * @return an {@code EntitySet} containing the elements for the given map,
   *         or {@code null} if no elements exist for the specified map
   */
  EntitySet<Element> getElements(String mapName);
}
