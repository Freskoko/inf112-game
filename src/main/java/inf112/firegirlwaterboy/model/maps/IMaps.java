package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public interface IMaps {

  /**
   * Initializes the all maps by loading all available Tiled maps from recources
   * folder.
   * Set currentMapName to "map"
   */
  void init();

  /**
   * Retrieves a {@link TiledMap} by its name.
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
   * Gets the player's spawn position from the "Spawn" object layer in the current
   * Tiled map.
   * Returns a default position if the layer or objects are missing.
   *
   * @return The spawn position as a {@link Vector2}, or {@code DEFAULT_SPAWN_POS}
   *         if missing.
   * @throws NullPointerException If the "Spawn" object lacks valid X/Y
   *                              properties.
   */
  Vector2 getPlayerSpawn();

  /**
   * Creates physics objects in the given Box2D world from all object layers in
   * specified map, excluding the "Spawn" layer.
   * 
   * @param world   The Box2D world to create objects in
   * @param mapName The name of the map with object layers
   */
  void createObjectsInWorld(World world, String mapName);

}
