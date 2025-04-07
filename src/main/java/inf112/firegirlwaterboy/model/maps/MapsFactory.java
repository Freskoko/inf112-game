package inf112.firegirlwaterboy.model.maps;

import java.io.File;
import java.util.HashMap;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
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
import inf112.firegirlwaterboy.model.managers.EntitySet;
import inf112.firegirlwaterboy.model.entity.Platform;
import inf112.firegirlwaterboy.model.entity.Collectable;

/**
 * Manages loading and interacting with tiled maps in the game.
 * Handles map retrieval, layer access, and object creation in a Box2D world.
 */
public class MapsFactory implements IMapsFactory {

  private HashMap<String, TiledMap> maps;
  public static final float PPM = 32;
  private final Vector2 DEFAULT_SPAWN_POS = new Vector2(100, 100);

  private HashMap<String, EntitySet<Platform>> platformsMap = new HashMap<>(); 


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
        case "Platform" -> createPlatform(world, layer, mapName);
        case "Finish" -> createFinishFromLayer(world, layer);
        default ->  createObjectsFromLayer(world, layer);
      }
    }
  }


    /**
   * Creates platfroms in the world from a given map layer.
   * Saves platforms in hashmap with the map name as key.
   * 
   * @param world The Box2D world where platforms should be created.
   * @param layer The map layer containing platforms.
   * @param mapName The name of the map to save the platforms with.
   */
  private void createPlatform(World world, MapLayer layer, String mapName) {
    EntitySet<Platform> platforms = new EntitySet<>();
    for (MapObject object : layer.getObjects()) {
      platforms.add(new Platform(world, object));
    }
    platformsMap.put(mapName, platforms);
  }

  /**
   * Creates finish zone objects in the world from a given map layer.
   * 
   * @param world The Box2D world where objects should be created.
   * @param layer The map layer containing finish zone objects.
   */
  private void createFinishFromLayer(World world, MapLayer layer) {
    for (MapObject object : layer.getObjects()) {
      BodyDef bdef = new BodyDef();
      bdef.position.set(getCX(object), getCY(object));
      bdef.type = BodyDef.BodyType.StaticBody;
      Body body = world.createBody(bdef);

      PolygonShape shape = new PolygonShape();
      shape.setAsBox(getWidth(object) / 2, getHeight(object) / 2);

      FixtureDef fdef = new FixtureDef();
      fdef.shape = shape;
      Fixture fixture = body.createFixture(fdef);
      fixture.setSensor(true);
      fixture.setUserData(layer.getName());
      shape.dispose();
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
      if (object instanceof PolygonMapObject) {
        createPolygonObject(world, (PolygonMapObject) object, layer.getName());
      } else {
        createRectangleObject(world, object, layer.getName());
      }
    }
  }

  /**
   * Creates a polygon object in the world from a given map object.
   * 
   * @param world The Box2D world where the object should be created.
   * @param polygon The map object to create a polygon from.
   * @param layerName The name of the layer the object belongs to.
   */
  private void createPolygonObject(World world, PolygonMapObject polygon, String layerName) {
    BodyDef bdef = new BodyDef();
    bdef.type = BodyDef.BodyType.StaticBody;

    Body body = world.createBody(bdef);

    PolygonShape shape = new PolygonShape();
    float[] vertices = polygon.getPolygon().getTransformedVertices();
    Vector2[] worldVertices = new Vector2[vertices.length / 2];

    for (int i = 0; i < worldVertices.length; i++) {
      worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
    }

    shape.set(worldVertices);

    FixtureDef fdef = new FixtureDef();
    fdef.shape = shape;
    fdef.friction = 1f;
    //fdef.restitution = 0f;
    Fixture fixture = body.createFixture(fdef);

    fixture.setUserData(layerName);
    shape.dispose();
  }

  /**
   * Creates a rectangle object in the world from a given map object.
   * 
   * @param world The Box2D world where the object should be created.
   * @param object The map object to create a rectangle from.
   * @param layerName The name of the layer the object belongs to.
   */
  private void createRectangleObject(World world, MapObject object, String layerName) {
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

    fixture.setUserData(layerName);
    shape.dispose();
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

  public static String getProperty(MapObject object, String property) {
    return object.getProperties().get(property, String.class).toUpperCase();
  }

  @Override
  public EntitySet<Platform> getPlatforms(String mapName) {
    return platformsMap.getOrDefault(mapName, new EntitySet<>());
  }

  

}
