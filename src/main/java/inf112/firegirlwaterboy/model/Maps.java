package inf112.firegirlwaterboy.model;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Maps{

  HashMap<String,TiledMap> maps;

  public Maps() {
    init();
  }

  public void init() {
    maps = new HashMap<String,TiledMap>();
    loadMap("map");
  }

  private void loadMap(String mapName) {
    if (maps.containsKey(mapName)) {
      throw new IllegalArgumentException(mapName +" already exists");
    }
    if (mapName == null) {
      throw new IllegalArgumentException( "Map name is null");
    }
    maps.put(mapName, new TmxMapLoader().load("src/main/resources/" + mapName + ".tmx"));
  }

  public TiledMap getMap(String mapName) {
    return maps.get(mapName);
  }

  public TiledMapTileLayer getLayer(String mapName, String layerName) {
    return (TiledMapTileLayer) getMap(mapName).getLayers().get(layerName);
  }

  

  


  
}
