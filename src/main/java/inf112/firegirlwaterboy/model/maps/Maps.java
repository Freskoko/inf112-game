package inf112.firegirlwaterboy.model.maps;

import java.io.File;
import java.util.HashMap;

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

import inf112.firegirlwaterboy.model.entity.Collectable;
import inf112.firegirlwaterboy.model.entity.PlayerType;

/**
 * Manages loading and interacting with tiled maps in the game.
 * Handles map retrieval, layer access, and object creation in a Box2D world.
 */
public class Maps implements IMaps {

  private HashMap<String, TiledMap> maps;
  private String currentMapName;
  public static final float PPM = 32;
  private final Vector2 DEFAULT_SPAWN_POS = new Vector2(100, 100);

  @Override
  public void init() {
    this.maps = loadAllMaps();
    this.currentMapName = "map";
  }

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
  public Vector2 getPlayerSpawn() {
    MapLayer objectLayer = getLayer(currentMapName, "Spawn");

    if (objectLayer == null) {
      System.out.println("Warning: 'Spawn' layer missing in map " + currentMapName);
      return DEFAULT_SPAWN_POS;
    }
    if (objectLayer.getObjects().getCount() == 0) {
      System.err.println("Warning: 'Spawn' layer exists but contains no objects in map " + currentMapName);
      return DEFAULT_SPAWN_POS;
    }

    MapObject object = objectLayer.getObjects().get(0);
    Float x = (Float) object.getProperties().get("x");
    Float y = (Float) object.getProperties().get("y");

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
        case "Collectable":
          createCollectableObjectsInWorldFromLayer(world, layer);
          break;
        case "Spawn":
          break;
        default:
          createObjectsInWorldFromLayer(world, layer);
          break;
      }
    }
  }

  private void createCollectableObjectsInWorldFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      new Collectable(world, object);
    }
  }

  public static float getX(MapObject object) {
    float x = object.getProperties().get("x", Float.class) / PPM;
    return x;
  }
  public static float getY(MapObject object) {
    float y = object.getProperties().get("y", Float.class) / PPM;
    return y;
  }

  public static float getWidth(MapObject object) {
    float width = object.getProperties().get("width", Float.class) / PPM;
    return width;
  }

  public static float getHeight(MapObject object) {
    float height = object.getProperties().get("height", Float.class) / PPM;
    return height;
  }

 
  /**
   * Creates physics objects in the world from a given map layer.
   *
   * @param world The Box2D world where objects should be created.
   * @param layer The map layer containing objects.
   */
  private void createObjectsInWorldFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      BodyDef bdef = new BodyDef();
      bdef.type = BodyDef.BodyType.StaticBody;

      float px = object.getProperties().get("x", Float.class);
      float py = object.getProperties().get("y", Float.class);
      float width = object.getProperties().get("width", Float.class);
      float height = object.getProperties().get("height", Float.class);

      float x = (px + width / 2) / PPM;
      float y = (py + height / 2) / PPM;

      bdef.position.set(x, y);

      Body body = world.createBody(bdef);

      PolygonShape shape = new PolygonShape();
      shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      // fdef.friction = 2f; // Optional: Adds friction
      // fdef.restitution = 0f; // Optional: No bouncing

      Fixture fixture = body.createFixture(fdef);
      fixture.setUserData(layer.getName());
      shape.dispose();
    }
  }
}
