package inf112.firegirlwaterboy.model.maps;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import inf112.firegirlwaterboy.model.entity.types.PlayerType;

/**
 * Manages loading and interacting with tiled maps in the game.
 * Handles map retrieval, layer access and overview over completed maps.
 */
public class MapManager implements IMapManager {

  private HashMap<String, TiledMap> maps;
  private HashMap<String, Boolean> completedMap = new HashMap<>();
  static final Vector2 DEFAULT_SPAWN_POS = new Vector2(2, 2);

  /**
   * Loads all Tiled maps (*.tmx) from the resources folder into a HashMap.
   *
   * @return A {@link HashMap} mapping map names (file names without ".tmx") to
   *         {@link TiledMap} objects.
   */
  private HashMap<String, TiledMap> loadAllMaps() {
    HashMap<String, TiledMap> maps = new HashMap<>();
    File dir = new File("src/main/resources/assets/maps/");
    File[] tmxFiles = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tmx"));

    if (tmxFiles != null) {
      for (File file : tmxFiles) {
        try {
          String mapName = file.getName().replace(".tmx", "");
          maps.put(mapName, new TmxMapLoader().load("src/main/resources/assets/maps/" + file.getName()));
        } catch (Exception e) {
          System.err.println("Error loading map: " + file.getName());
          e.printStackTrace();
        }
      }
    }
    return maps;
  }

  @Override
  public TiledMap getMap(String mapName) {
    if (maps == null) {
      maps = loadAllMaps();
    }
    if (!maps.containsKey(mapName)) {
      throw new IllegalArgumentException("Map not found: " + mapName);
    }
    return maps.get(mapName);
  }

  @Override
  public MapLayer getLayer(TiledMap map, LayerType layerType) {
    MapLayer layer = map.getLayers().get(layerType.toString());
    if (layer == null) {
      throw new IllegalArgumentException("Layer not found: " + layerType + " in map:" + map);
    }
    return layer;
  }

  @Override
  public boolean isComplete(String mapName) {
    return completedMap.getOrDefault(mapName, false);
  }

  @Override
  public void complete(String mapName) {
    completedMap.put(mapName, true);
  }

  @Override
  public Vector2 getSpawnPos(TiledMap map, PlayerType playerType) {
    MapLayer layer = getLayer(map, LayerType.SPAWN);

    if (layer == null || layer.getObjects().getCount() == 0 || playerType == null) {
      System.err.println("Warning returning default spawn position");
      return DEFAULT_SPAWN_POS;
    }

    for (MapObject object : layer.getObjects()) {
      if (object.getProperties().getValues() == null) {
        break;
      }

      String type = MapUtils.getProperty(object, "type");

      if (playerType.name().equals(type)) {
        Float x = MapUtils.getX(object);
        Float y = MapUtils.getY(object);

        if (x != null && y != null) {
          return new Vector2(x, y);
        }
      }
    }

    return DEFAULT_SPAWN_POS;
  }
}