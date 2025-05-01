package inf112.firegirlwaterboy.model.maps;

import com.badlogic.gdx.maps.MapObject;

/**
 * Utility class that provides methods to retrieve properties of map objects.
 */
public final class MapUtils {

  public static final float PPM = 32;

  private MapUtils() {
  }

  /**
   * Retrieves the Y-coordinate of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The Y-coordinate in world units.
   */
  public static float getY(MapObject object) {
    return object.getProperties().get("y", Float.class) / MapUtils.PPM;
  }

  /**
   * Retrieves the X-coordinate of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The X-coordinate in world units.
   */
  public static float getX(MapObject object) {
    return object.getProperties().get("x", Float.class) / MapUtils.PPM;
  }

  /**
   * Retrieves a string property from a map object and converts it to uppercase.
   *
   * @param object   The map object.
   * @param property The name of the property to retrieve.
   * @return The property value in uppercase.
   */
  public static String getProperty(MapObject object, String property) {
    return object.getProperties().get(property, String.class).toUpperCase();
  }

  /**
   * Retrieves the center Y-coordinate of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The center Y-coordinate in world units.
   */
  public static float getCY(MapObject object) {
    float y = object.getProperties().get("y", Float.class) / MapUtils.PPM;
    float height = MapUtils.getHeight(object);
    return y + height / 2;
  }

  /**
   * Retrieves the center X-coordinate of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The center X-coordinate in world units.
   */
  public static float getCX(MapObject object) {
    float x = object.getProperties().get("x", Float.class) / MapUtils.PPM;
    float width = MapUtils.getWidth(object);
    return x + width / 2;
  }

  /**
   * Retrieves the height of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The height in world units.
   */
  public static float getHeight(MapObject object) {
    return object.getProperties().get("height", Float.class) / MapUtils.PPM;
  }

  /**
   * Retrieves the width of a map object, scaled by PPM to match to world units.
   *
   * @param object The map object.
   * @return The width in world units.
   */
  public static float getWidth(MapObject object) {
    return object.getProperties().get("width", Float.class) / MapUtils.PPM;
  }
}