package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;

public final class MapUtils {

  private static final Vector2 DEFAULT_SPAWN_POS = new Vector2(100, 100);
  public static final float PPM = 32;

  private MapUtils() {} // Prevent instantiation 

  public static float getY(MapObject object) {
    return object.getProperties().get("y", Float.class) / MapUtils.PPM;
  }

  public static float getX(MapObject object) {
    return object.getProperties().get("x", Float.class) / MapUtils.PPM;
  }

  public static String getProperty(MapObject object, String property) {
    return object.getProperties().get(property, String.class).toUpperCase();
  }

  public static float getCY(MapObject object) {
    float y = object.getProperties().get("y", Float.class) / MapUtils.PPM;
    float height = MapUtils.getHeight(object);
    return y + height / 2;
  }

  public static float getCX(MapObject object) {
    float x = object.getProperties().get("x", Float.class) / MapUtils.PPM;
    float width = MapUtils.getWidth(object);
    return x + width / 2;
  }

  public static float getHeight(MapObject object) {
    return object.getProperties().get("height", Float.class) / MapUtils.PPM;
  }

  public static float getWidth(MapObject object) {
    return object.getProperties().get("width", Float.class) / MapUtils.PPM;
  }


   /**
   * Gets the player's spawn position from the "Spawn" map layer.
   * Returns a default position if objects are missing.
   * 
   * @param spawnLayer The map layer to get the spawn position from.
   *
   * @return The spawn position as a {@link Vector2}, or {@code DEFAULT_SPAWN_POS}
   *         if missing.
   * @throws NullPointerException If the "Spawn" object lacks valid X/Y
   *                              properties.
   */
  public static Vector2 getSpawnPos(MapLayer spawnLayer) {
  
    if (spawnLayer.getObjects().getCount() == 0) {
      System.err.println("Warning: no objects in map layer");
      return DEFAULT_SPAWN_POS;
    }
  
    MapObject object = spawnLayer.getObjects().get(0);
    Float x = getX(object);
    Float y = getY(object);
    
    
    if (x != null && y != null) {
      return new Vector2(x, y); // Mulig de må deles på PPM
    }
    throw new NullPointerException("Unknown error with object layer");
  }
  
}
