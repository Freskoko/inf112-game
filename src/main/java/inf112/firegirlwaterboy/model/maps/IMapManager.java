package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * Interface for managing tiled maps in the game.
 */
public interface IMapManager {

  /**
   * Retrieves a TiledMap by its name.
   *
   * @param mapName The name of the map (without file extension).
   * @return The corresponding TiledMap.
   * @throws IllegalArgumentException If the map is not found.
   */
  TiledMap getMap(String mapName);

  /**
   * Retrieves a specific layer from a given map.
   *
   * @param map       The map from which to retrieve the layer.
   * @param layerType The type of the layer to retrieve.
   * @return The MapLayer corresponding to the given type.
   * @throws IllegalArgumentException If the layer is not found in the map.
   */
  MapLayer getLayer(TiledMap map, LayerType layerType);

  /**
   * Checks whether the specified map has been completed.
   *
   * @param mapName The name of the map to check.
   * @return True if the map is completed, false otherwise.
   */
  boolean isComplete(String mapName);

  /**
   * Marks the specified map as completed.
   *
   * @param mapName The name of the map to mark as completed.
   */
  void complete(String mapName);

  /**
   * Gets the player's spawn position from the map.
   * Returns a default position if objects are missing.
   *
   * @param map The map to get the spawn position from.
   * @param playerType The type of player (Firegirl or Waterboy).
   * @return The spawn position as a Vector2, or DEFAULT_SPAWN_POS if missing.
   */
  Vector2 getSpawnPos(TiledMap map, PlayerType playerType);
}