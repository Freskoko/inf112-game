package inf112.firegirlwaterboy.model;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

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

  public Vector2 getPlayerSpawn() {
    MapLayer objectLayer = getLayer("map", "PlayerLayer");
    if (objectLayer != null) {
      for (MapObject object : objectLayer.getObjects()) {
        if (object.getName().equals("playerSpawn") && object instanceof RectangleMapObject) {
          Rectangle rect = ((RectangleMapObject) object).getRectangle();
          return new Vector2(rect.x, rect.y);
        }
      }
    }
    return new Vector2(100, 100);
  }
  
  public void createObjectsInWorld(World world, String mapName) {
    for (MapObject object : getLayer(mapName, "InteractiveObjects").getObjects()) {
      BodyDef bdef = new BodyDef();
      FixtureDef fdef = new FixtureDef();

      Body body = world.createBody(bdef);
      Fixture fixture = body.createFixture(fdef);
      // alt blir satt til wall, burde ha egne layers til hver type 
      fixture.setUserData("WALL");
    }
  }
    

}
