package inf112.firegirlwaterboy.model;

import java.io.File;
import java.util.HashMap;
import java.util.MissingResourceException;

import javax.print.DocFlavor.STRING;

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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that represents the maps in the game.
 * This class handles loading, storing and retrieving tile-based maps.
 */
public class Maps {

  /** HashMap that stores the maps. */
  private HashMap<String, TiledMap> maps;
  private String currentMapName;
  public static final float PPM = 32f;

  /**
   * Constructs a new Maps object and initializes the map storage.
   */
  public Maps() {
  }

  /**
   * Initializes the map storage and loads the deafult map.
   */
  public void init() {
    maps = loadAllMaps();
    this.currentMapName = "map";
  }

  /**
   * Loads all maps from resources 
   * 
   * @return A HashMap of all maps 
   */
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
   * @param mapName   The name of the map which contains the layer
   * @param layerName The name of the layer to retrieve
   * @return The layer associated with the given name
   */
  public MapLayer getLayer(String mapName, String layerName) {
    return getMap(mapName).getLayers().get(layerName);
  }

  /**
   * Retrieves the position of players spawn
   * 
   * @return The position of players spawn
   */
  public Vector2 getPlayerSpawn() {
    MapLayer objectLayer = getLayer(currentMapName, "Spawn");
    
    if (objectLayer != null) {
      MapObject obj = objectLayer.getObjects().get(0);
      Rectangle rect = ((RectangleMapObject) obj).getRectangle();
      return new Vector2(rect.x, rect.y);
    } else {
      throw new NullPointerException("Missing spawn-layer in map " + currentMapName);
    }
  }
  
  /**
   * Creates objects from all layers in map in given world
   * 
   * @param world The world to create objects in
   * @param mapName The name of the map with layers
   */
  public void createObjectsInWorld(World world, String mapName) {
    for (MapLayer layer : getMap(mapName).getLayers()) {
      if (!(layer instanceof TiledMapTileLayer)) {
        System.out.println(layer.getName() + ": " + layer.getClass());
        createObjectsFromLayer(world, layer);
      }
    }
  }
    
  /**
   * Create objects from given layer in given world
   * 
   * @param world The world to create objects in
   * @param layer The layer objects are loaded from
   */
  private void createObjectsFromLayer(World world , MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      BodyDef bdef = new BodyDef();
      bdef.type = BodyDef.BodyType.StaticBody; // Walls are static

      // Get position from Tiled map
      float x = object.getProperties().get("x", Float.class);
      float y = object.getProperties().get("y", Float.class);
      bdef.position.set(x / PPM, y / PPM); // Convert to Box2D units

      Body body = world.createBody(bdef);

      // Define shape (assuming rectangular objects)
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(
          object.getProperties().get("width", Float.class) / 2 / PPM,
          object.getProperties().get("height", Float.class) / 2 / PPM);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      fdef.friction = 2f; // Optional: Adds friction
      fdef.restitution = 0f; // Optional: No bouncing

      Fixture fixture = body.createFixture(fdef);
      fixture.setUserData(layer.getName());
      System.out.println("Added " + layer.getName());

      shape.dispose(); // Clean up memory

    }
  }
}
