package inf112.firegirlwaterboy.model;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Class that represents the maps in the game.
 * This class handles loading, storing and retrieving tile-based maps.
 */
public class Maps {

  /** HashMap that stores the maps. */
  HashMap<String, TiledMap> maps;

  /**
   * Constructs a new Maps object and initializes the map storage.
   */
  public Maps() {
    init();
  }

  /**
   * Initializes the map storage and loads the deafult map.
   */
  public void init() {
    maps = new HashMap<String, TiledMap>();
    loadMap("map");
  }

  /**
   * Loads a map from a file and stores it in the map storage.
   * 
   * @param mapName The name of the map to load
   */
  private void loadMap(String mapName) {
    if (maps.containsKey(mapName)) {
      throw new IllegalArgumentException(mapName + " already exists");
    }
    if (mapName == null) {
      throw new IllegalArgumentException("Map name is null");
    }
    maps.put(mapName, new TmxMapLoader().load("src/main/resources/" + mapName + ".tmx"));
  }

  /**
   * Retrieves a map from the map storage.
   * 
   * @param mapName The name of the map to retrieve
   * @return The map associated with the given name
   */
  public TiledMap getMap(String mapName) {
    return maps.get(mapName);
  }

  /**
   * Retrieves a layer from a map.
   * 
   * @param mapName   The name of the map
   * @param layerName The name of the layer to retrieve
   * @return The layer associated with the given name
   */
  public TiledMapTileLayer getLayer(String mapName, String layerName) {
    return (TiledMapTileLayer) getMap(mapName).getLayers().get(layerName);
  }

}
