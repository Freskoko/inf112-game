package inf112.firegirlwaterboy.model.maps;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.firegirlwaterboy.model.entity.Element;

import inf112.firegirlwaterboy.model.entity.Collectable;

/**
 * Manages loading and interacting with tiled maps in the game.
 * Handles map retrieval, layer access, and object creation in a Box2D world.
 */
public class Maps implements IMaps {

  private HashMap<String, TiledMap> maps;
  public static final float PPM = 32;
  private final Vector2 DEFAULT_SPAWN_POS = new Vector2(100, 100);



  /**
   * Loads all Tiled maps (*.tmx) from the resources folder into a HashMap.
   *
   * @return A {@link HashMap} mapping map names (file names without ".tmx") to
   *         {@link TiledMap} objects.
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
  public MapLayer getLayer(String mapName, String layerName) {
    MapLayer layer = getMap(mapName).getLayers().get(layerName);
    if (layer == null) {
      throw new IllegalArgumentException("Layer not found: " + layerName + " in map:" + mapName);
    }
    return layer;
  }

  @Override
  public Vector2 getSpawnPos(String mapName) {
    MapLayer objectLayer = getLayer(mapName, "Spawn");

    if (objectLayer == null) {
      System.out.println("Warning: 'Spawn' layer missing in map " + mapName);
      return DEFAULT_SPAWN_POS;
    }
    if (objectLayer.getObjects().getCount() == 0) {
      System.err.println("Warning: 'Spawn' layer exists but contains no objects in map " + mapName);
      return DEFAULT_SPAWN_POS;
    }

    MapObject object = objectLayer.getObjects().get(0);
    Float x = getX(object);
    Float y = getY(object);
    
    
    if (x != null && y != null) {
      return new Vector2(x, y); // Mulig de må deles på PPM
    }
    throw new NullPointerException("Unknown error with 'Spawn' layer");
  }

  @Override
  public void createObjectsInWorld(World world, String mapName) {
    for (MapLayer layer : getMap(mapName).getLayers()) {
      String layerName = layer.getName();
      switch (layerName) {
        case "Collectable" -> createCollectablesFromLayer(world, layer);
        case "Elements" -> createElementsFromLayer(world, layer);
        case "Spawn" -> {} // Ignore spawn layer;
        default ->  createObjectsFromLayer(world, layer);
      }
    }
  }

  /**
   * Creates elements in the world from a given map layer.
   * 
   * @param world The Box2D world where elements should be created.
   * @param layer The map layer containing elements.
   */
  private void createElementsFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      new Element(world, object);
    }
  }

   /**
   * Creates collectable objects in the world from a given map layer.
   * 
   * @param world The Box2D world where elements should be created.
   * @param layer The map layer containing collectable object.
   */
  private void createCollectablesFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      new Collectable(world, object);
    
    }
  }

  

 
  /**
   * Creates physics objects in the world from a given map layer.
   *
   * @param world The Box2D world where objects should be created.
   * @param layer The map layer containing objects.
   */
  private void createObjectsFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      BodyDef bdef = new BodyDef();
      bdef.type = BodyDef.BodyType.StaticBody;

      float width = getWidth(object);
      float height = getHeight(object);
      float x = getCX(object);
      float y = getCY(object);

      bdef.position.set(x, y);
      Body body = world.createBody(bdef);

      PolygonShape shape = new PolygonShape();
      shape.setAsBox(width / 2, height / 2);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      Fixture fixture = body.createFixture(fdef);

      fixture.setUserData(layer.getName());
      shape.dispose();
    }
  }

 

  public static float getWidth(MapObject object) {
    return object.getProperties().get("width", Float.class) / PPM;
  }

  public static float getHeight(MapObject object) {
    return object.getProperties().get("height", Float.class) / PPM;
  }

  public static float getCX(MapObject object) {
    float x = object.getProperties().get("x", Float.class) / PPM;
    float width = getWidth(object);
    return x + width / 2;
  }

  public static float getCY(MapObject object) {
    float y = object.getProperties().get("y", Float.class) / PPM;
    float height = getHeight(object);
    return y + height / 2;
  }

  public static float getX(MapObject object) {
    return object.getProperties().get("x", Float.class) / PPM;
  }

  public static float getY(MapObject object) {
    return object.getProperties().get("y", Float.class) / PPM;
  }
}
