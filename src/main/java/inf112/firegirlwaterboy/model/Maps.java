package inf112.firegirlwaterboy.model;

import java.io.File;
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
    maps = loadAllMaps();
  }
  private HashMap<String, TiledMap> loadAllMaps() {
    HashMap<String, TiledMap> maps = new HashMap<>();
    File dir = new File("src/main/resources/");
    File[] tmxFiles = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".tmx"));

    if (tmxFiles != null) {
        for (File file : tmxFiles) {
            try {
                String mapName = file.getName().replace(".tmx", "");
                maps.put(mapName, new TmxMapLoader().load("src/main/resources/" + file.getName()));
            } catch (Exception e) {
                System.err.println("Error loading map: " + file.getName());
                e.printStackTrace();
            }
        }
    }
    return maps;
}


  public TiledMap getMap(String mapName) {
    return maps.get(mapName);
  }

  public TiledMapTileLayer getLayer(String mapName, String layerName) {
    return (TiledMapTileLayer) getMap(mapName).getLayers().get(layerName);
  }
}
